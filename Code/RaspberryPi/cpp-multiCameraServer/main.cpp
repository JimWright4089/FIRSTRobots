// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

#include <cstdio>
#include <string>
#include <string_view>
#include <thread>
#include <vector>

#include <fmt/format.h>
#include <networktables/NetworkTableInstance.h>
#include <vision/VisionPipeline.h>
#include <vision/VisionRunner.h>
#include <wpi/StringExtras.h>
#include <wpi/json.h>
#include <wpi/raw_istream.h>
#include <wpi/raw_ostream.h>
#include <opencv2/core.hpp>
#include <opencv2/imgproc.hpp>
#include <opencv2/highgui.hpp>

using namespace cv;

#include "cameraserver/CameraServer.h"

/*
   JSON format:
   {
       "team": <team number>,
       "ntmode": <"client" or "server", "client" if unspecified>
       "cameras": [
           {
               "name": <camera name>
               "path": <path, e.g. "/dev/video0">
               "pixel format": <"MJPEG", "YUYV", etc>   // optional
               "width": <video mode width>              // optional
               "height": <video mode height>            // optional
               "fps": <video mode fps>                  // optional
               "brightness": <percentage brightness>    // optional
               "white balance": <"auto", "hold", value> // optional
               "exposure": <"auto", "hold", value>      // optional
               "properties": [                          // optional
                   {
                       "name": <property name>
                       "value": <property value>
                   }
               ],
               "stream": {                              // optional
                   "properties": [
                       {
                           "name": <stream property name>
                           "value": <stream property value>
                       }
                   ]
               }
           }
       ]
       "switched cameras": [
           {
               "name": <virtual camera name>
               "key": <network table key used for selection>
               // if NT value is a string, it's treated as a name
               // if NT value is a double, it's treated as an integer index
           }
       ]
   }
 */

static const char* configFile = "/boot/frc.json";

namespace {

unsigned int team;
bool server = false;

struct CameraConfig {
  std::string name;
  std::string path;
  wpi::json config;
  wpi::json streamConfig;
};

struct SwitchedCameraConfig {
  std::string name;
  std::string key;
};

std::vector<CameraConfig> cameraConfigs;
std::vector<SwitchedCameraConfig> switchedCameraConfigs;
std::vector<cs::VideoSource> cameras;

void ParseError(std::string_view msg) {
  fmt::print(stderr, "config error in '{}': {}\n", configFile, msg);
}

bool ReadCameraConfig(const wpi::json& config) {
  CameraConfig c;

  // name
  try {
    c.name = config.at("name").get<std::string>();
  } catch (const wpi::json::exception& e) {
    ParseError(fmt::format("could not read camera name: {}", e.what()));
    return false;
  }

  // path
  try {
    c.path = config.at("path").get<std::string>();
  } catch (const wpi::json::exception& e) {
    ParseError(fmt::format("camera '{}': could not read path: {}", c.name, e.what()));
    return false;
  }

  // stream properties
  if (config.count("stream") != 0) c.streamConfig = config.at("stream");

  c.config = config;

  cameraConfigs.emplace_back(std::move(c));
  return true;
}

bool ReadSwitchedCameraConfig(const wpi::json& config) {
  SwitchedCameraConfig c;

  // name
  try {
    c.name = config.at("name").get<std::string>();
  } catch (const wpi::json::exception& e) {
    ParseError(fmt::format("could not read switched camera name: {}",
                           e.what()));
    return false;
  }

  // key
  try {
    c.key = config.at("key").get<std::string>();
  } catch (const wpi::json::exception& e) {
    ParseError(fmt::format("switched camera '{}': could not read key: {}",
                           c.name, e.what()));
    return false;
  }

  switchedCameraConfigs.emplace_back(std::move(c));
  return true;
}

bool ReadConfig() {
  // open config file
  std::error_code ec;
  wpi::raw_fd_istream is(configFile, ec);
  if (ec) {
    wpi::errs() << "could not open '" << configFile << "': " << ec.message()
                << '\n';
    return false;
  }

  // parse file
  wpi::json j;
  try {
    j = wpi::json::parse(is);
  } catch (const wpi::json::parse_error& e) {
    ParseError(fmt::format("byte {}: {}", e.byte, e.what()));
    return false;
  }

  // top level must be an object
  if (!j.is_object()) {
    ParseError("must be JSON object");
    return false;
  }

  // team number
  try {
    team = j.at("team").get<unsigned int>();
  } catch (const wpi::json::exception& e) {
    ParseError(fmt::format("could not read team number: {}", e.what()));
    return false;
  }

  // ntmode (optional)
  if (j.count("ntmode") != 0) {
    try {
      auto str = j.at("ntmode").get<std::string>();
      if (wpi::equals_lower(str, "client")) {
        server = false;
      } else if (wpi::equals_lower(str, "server")) {
        server = true;
      } else {
        ParseError(fmt::format("could not understand ntmode value '{}'", str));
      }
    } catch (const wpi::json::exception& e) {
      ParseError(fmt::format("could not read ntmode: {}", e.what()));
    }
  }

  // cameras
  try {
    for (auto&& camera : j.at("cameras")) {
      if (!ReadCameraConfig(camera)) return false;
    }
  } catch (const wpi::json::exception& e) {
    ParseError(fmt::format("could not read cameras: {}", e.what()));
    return false;
  }

  // switched cameras (optional)
  if (j.count("switched cameras") != 0) {
    try {
      for (auto&& camera : j.at("switched cameras")) {
        if (!ReadSwitchedCameraConfig(camera)) return false;
      }
    } catch (const wpi::json::exception& e) {
      ParseError(fmt::format("could not read switched cameras: {}", e.what()));
      return false;
    }
  }

  return true;
}

cs::UsbCamera StartCamera(const CameraConfig& config) {
  fmt::print("Starting camera '{}' on {}\n", config.name, config.path);
  cs::UsbCamera camera{config.name, config.path};
  auto server = frc::CameraServer::StartAutomaticCapture(camera);

  camera.SetConfigJson(config.config);
  camera.SetConnectionStrategy(cs::VideoSource::kConnectionKeepOpen);

  if (config.streamConfig.is_object())
    server.SetConfigJson(config.streamConfig);

  return camera;
}

cs::MjpegServer StartSwitchedCamera(const SwitchedCameraConfig& config) {
  fmt::print("Starting switched camera '{}' on {}\n", config.name, config.key);
  auto server = frc::CameraServer::AddSwitchedCamera(config.name);

  nt::NetworkTableInstance::GetDefault()
      .GetEntry(config.key)
      .AddListener(
          [server](const auto& event) mutable {
            if (event.value->IsDouble()) {
              int i = event.value->GetDouble();
              if (i >= 0 && i < cameras.size()) server.SetSource(cameras[i]);
            } else if (event.value->IsString()) {
              auto str = event.value->GetString();
              for (int i = 0; i < cameraConfigs.size(); ++i) {
                if (str == cameraConfigs[i].name) {
                  server.SetSource(cameras[i]);
                  break;
                }
              }
            }
          },
          NT_NOTIFY_IMMEDIATE | NT_NOTIFY_NEW | NT_NOTIFY_UPDATE);

  return server;
}

class MyPipeline : public frc::VisionPipeline {
public:
  double mVal = 0.0;
  nt::NetworkTableEntry mX;
  nt::NetworkTableEntry mY;
  nt::NetworkTableEntry mArea;
  nt::NetworkTableEntry mHigh;
  nt::NetworkTableEntry mLow;
  cs::CvSource mThreshold;
  cs::CvSource mOrigSource;
  int mLow1Value = 0;
  int mHigh1Value = 255;
  int mLow2Value = 90;
  int mHigh2Value = 226;
  int mLow3Value = 214;
  int mHigh3Value = 255;
  int mMorphSize = 7;
  std::vector<std::vector<Point> > mContours;
  std::vector<Vec4i> mHierarchy;
  cv::Rect mRectangle;

  MyPipeline()
  {
    wpi::outs() << "Building pipline\n";
    auto inst = nt::NetworkTableInstance::GetDefault();
    mX = inst.GetEntry("/rp/x");
    mY = inst.GetEntry("/rp/y");
    mArea = inst.GetEntry("/rp/area");
    mHigh = inst.GetEntry("/rp/high");
    mLow = inst.GetEntry("/rp/low");
    mThreshold = frc::CameraServer::GetInstance()->PutVideo("Threshold", 320, 240);
    mOrigSource = frc::CameraServer::GetInstance()->PutVideo("Processed", 320, 240);
  }

  void Process(cv::Mat& mat) override {
    cv::Mat imgThresholded;
    cv::Mat imgThresholdedCopy;
    cv::Mat imageInHueSatVal;

    //    mHigh1Value = mHigh.GetDouble(255);
    //    mLow1Value = mLow.GetDouble(0);

        // Convert the captured frame from BGR to HSV
        // I like working in Hue Sat Value
    cv::cvtColor(mat, imageInHueSatVal, cv::COLOR_BGR2HSV);

    cv::inRange(imageInHueSatVal, Scalar(mLow1Value, mLow2Value, mLow3Value),
      Scalar(mHigh1Value, mHigh2Value, mHigh3Value), imgThresholded); //Threshold the image

    //morphological opening (remove small objects from the foreground)
    cv::erode(imgThresholded, imgThresholded,
      cv::getStructuringElement(cv::MORPH_ELLIPSE, cv::Size(mMorphSize, mMorphSize)));
    cv::dilate(imgThresholded, imgThresholded,
      cv::getStructuringElement(cv::MORPH_ELLIPSE, cv::Size(mMorphSize, mMorphSize)));

    //morphological closing (fill small holes in the foreground)
    cv::dilate(imgThresholded, imgThresholded,
      cv::getStructuringElement(cv::MORPH_ELLIPSE, cv::Size(mMorphSize, mMorphSize)));
    cv::erode(imgThresholded, imgThresholded,
      cv::getStructuringElement(cv::MORPH_ELLIPSE, cv::Size(mMorphSize, mMorphSize)));

    // finding the contours corupts the image passed in
    imgThresholded.copyTo(imgThresholdedCopy);

    cv::findContours(imgThresholdedCopy, mContours, mHierarchy,
      cv::RETR_TREE, cv::CHAIN_APPROX_SIMPLE, Point(0, 0));

    double largestArea = -1;
    int largestCont = -1;

    for (int i = 0; i < mContours.size(); i++)
    {
      double area = contourArea(mContours.at(i));

      if (area > largestArea)
      {
        largestArea = area;
        largestCont = i;
      }
    }

    // Are there contours
    if (-1 != largestCont)
    {
      mRectangle = boundingRect(mContours.at(largestCont));
      approxPolyDP(Mat(mContours.at(largestCont)), mContours.at(largestCont), 3, true);

      Scalar color = Scalar(255, 255, 255);
      drawContours(mat, mContours, largestCont, color, 2, 8, mHierarchy, 0, Point());
      Scalar color2 = Scalar(255, 128, 255);
      rectangle(mat, mRectangle, color2, 2);
    }

    // Did we find the biggest contour, display it
    if (-1 != largestCont)
    {
      mRectangle = boundingRect(mContours.at(largestCont));
      approxPolyDP(Mat(mContours.at(largestCont)), mContours.at(largestCont), 3, true);
      mX.SetValue(nt::Value::MakeDouble(mRectangle.x + (mRectangle.width / 2)));
      mY.SetValue(nt::Value::MakeDouble(mRectangle.y + (mRectangle.height / 2)));
      mArea.SetValue(nt::Value::MakeDouble(largestArea));

      //printf("X:%4d Y:%4d W:%4d H:%4d A:%6.1f\n",
      //          mRectangle.x, mRectangle.y, mRectangle.width, mRectangle.height, largestArea);
    }


    mThreshold.PutFrame(imgThresholded);
    mOrigSource.PutFrame(mat);
  }
};



#ifdef JUNK
// example pipeline
class MyPipeline : public frc::VisionPipeline {
 public:
  int val = 0;

  void Process(cv::Mat& mat) override {
    ++val;
  }
};
#endif
}  // namespace

int main(int argc, char* argv[]) {
  if (argc >= 2) configFile = argv[1];

  // read configuration
  if (!ReadConfig()) return EXIT_FAILURE;

  // start NetworkTables
  auto ntinst = nt::NetworkTableInstance::GetDefault();
  if (server) {
    fmt::print("Setting up NetworkTables server\n");
    ntinst.StartServer();
  } else {
    fmt::print("Setting up NetworkTables client for team {}\n", team);
    ntinst.StartClientTeam(team);
    ntinst.StartDSClient();
  }

  // start cameras
  for (const auto& config : cameraConfigs)
    cameras.emplace_back(StartCamera(config));

  // start switched cameras
  for (const auto& config : switchedCameraConfigs) StartSwitchedCamera(config);

  // start image processing on camera 0 if present
  if (cameras.size() >= 1) {
    std::thread([&] {
      frc::VisionRunner<MyPipeline> runner(cameras[0], new MyPipeline(),
                                           [&](MyPipeline &pipeline) {
        // do something with pipeline results
      });
      /* something like this for GRIP:
      frc::VisionRunner<MyPipeline> runner(cameras[0], new grip::GripPipeline(),
                                           [&](grip::GripPipeline& pipeline) {
        ...
      });
       */
      runner.RunForever();
    }).detach();
  }

  // loop forever
  for (;;) std::this_thread::sleep_for(std::chrono::seconds(10));
}