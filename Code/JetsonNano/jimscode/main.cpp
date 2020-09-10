//----------------------------------------------------------------------------
//
//  $Workfile: main.cpp$
//
//  $Revision: X$
//
//  Project:    Ball Locator
//
//                            Copyright (c) 2020
//                               James A Wright
//                            All Rights Reserved
//
//  Modification History:
//  $Log:
//  $
//
//  Notes:
//     This is the main entry poiint
//
//----------------------------------------------------------------------------

//----------------------------------------------------------------------------
//  Includes
//----------------------------------------------------------------------------
#include <iostream>
#include <string>
#include <opencv2/opencv.hpp>
#include <librealsense2/rs.hpp>
#include "config.h"
#include "stopWatch.h"
#include "utils.h"
using namespace std;
using namespace cv;

//#define WEBCAM
#define BAL_DIST


Mat gSourceImage;
#ifndef WEBCAM
Mat gSourceImageIR;
#endif

//----------------------------------------------------------------------------
//  Purpose:
//   The main entry point
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
int main()
{
  Config* config = Config::getInstance();
  int frameCount = 0;
  int lastFrameCount = 0;
  string originalPath = config->getOutputOriginalPath();
  string distancePath = config->getOutputDistancePath();
  StopWatch waitASecond;
  StopWatch waitForSaving;
  
  //------------------------------------------
  //  List the config
  //------------------------------------------
  cout << config->toString();
  
  //------------------------------------------
  //  Make the save directory
  //------------------------------------------
  if(true == config->getSaveOutput())
  {
    string outputPath = findNewPath(config->getOutputPath(), config->getOutputPrefix());
    cout << outputPath << "\n";
    config->setOutputPath(outputPath);
    originalPath = config->getOutputOriginalPath();
    distancePath = config->getOutputDistancePath();
    waitForSaving.setTime(config->getSaveOutputTime());
  }


#ifdef WEBCAM
  //------------------------------------------
  //  Setup the comera
  //------------------------------------------
  VideoCapture cap(1); //capture the video from web cam
  // Can't open the camera
  if (!cap.isOpened())  // if not success, exit program
  {
    cout << "Cannot open the web cam" << endl;
    return -1;
  }

  //------------------------------------------
  //  Camera settings
  //------------------------------------------
  cout << "CV_CAP_PROP_FRAME_WIDTH  :" << cap.get(CAP_PROP_FRAME_WIDTH)  << "\n";
  cout << "CV_CAP_PROP_FRAME_HEIGHT :" << cap.get(CAP_PROP_FRAME_HEIGHT) << "\n";
  cout << "CV_CAP_PROP_BRIGHTNESS   :" << cap.get(CAP_PROP_BRIGHTNESS) << "\n";
  cout << "CV_CAP_PROP_CONTRAST     :" << cap.get(CAP_PROP_CONTRAST) << "\n";
  cout << "CV_CAP_PROP_SATURATION   :" << cap.get(CAP_PROP_SATURATION) << "\n";
  cout << "CV_CAP_PROP_HUE          :" << cap.get(CAP_PROP_HUE) << "\n";
  cout << "CV_CAP_PROP_GAIN         :" << cap.get(CAP_PROP_GAIN) << "\n";
  cout << "CV_CAP_PROP_EXPOSURE     :" << cap.get(CAP_PROP_EXPOSURE) << "\n";
  cout << "CV_CAP_PROP_EXPOSURE     :" << cap.get(0x009a0902) << "\n";
  double dbl = cap.get(CAP_PROP_EXPOSURE);
  cout << "CV_CAP_PROP_EXPOSUREX     :" << dbl << "\n";
  cout << "CV_CAP_PROP_AUTOFOCUS    :" << cap.get(CAP_PROP_AUTOFOCUS) << "\n";
  cout << "CV_CAP_PROP_AUTO_EXPOSURE:" << cap.get(CAP_PROP_AUTO_EXPOSURE) << "\n";
  cout << "CV_CAP_PROP_WHITE_BALANCE_RED_V :" << cap.get(CAP_PROP_WHITE_BALANCE_RED_V) << "\n"; 
  cout << "CV_CAP_PROP_WHITE_BALANCE_BLUE_U:" << cap.get(CAP_PROP_WHITE_BALANCE_BLUE_U) << "\n";
#else
  //Contruct a pipeline which abstracts the device
  rs2::pipeline pipe;

  //Create a configuration for configuring the pipeline with a non default profile
  rs2::config cfg;

  //Add desired streams to configuration
  cfg.enable_stream(RS2_STREAM_COLOR, 640, 480, RS2_FORMAT_BGR8, 30);
  cfg.enable_stream(RS2_STREAM_INFRARED, 640, 480, RS2_FORMAT_Y8, 30);
  cfg.enable_stream(RS2_STREAM_DEPTH, 640, 480, RS2_FORMAT_Z16, 30);

  //Instruct pipeline to start streaming with the requested configuration
  pipe.start(cfg);

  // Camera warmup - dropping several first frames to let auto-exposure stabilize
  rs2::frameset frames;
  for(int i = 0; i < 30; i++)
  {
      //Wait for all configured streams to produce a frame
      frames = pipe.wait_for_frames();
  }
#endif


  waitASecond.reset();
  waitForSaving.reset();
  //------------------------------------------
  //  Run forever
  //------------------------------------------
  while (true)
  {
#ifdef WEBCAM  
    bool bSuccess = cap.read(gSourceImage); // read a new frame from video
    if (!bSuccess) //if not success, break loop
    {
      cout << "Cannot read a frame from video stream" << endl;
      break;
    }
#else
      frames = pipe.wait_for_frames();

      //Get each frame
      rs2::frame color_frame = frames.get_color_frame();
      // Creating OpenCV Matrix from a color image
      Mat tempColor(Size(640, 480), CV_8UC3, (void*)color_frame.get_data(), Mat::AUTO_STEP);
      gSourceImage = tempColor;

      //Get each frame
      rs2::frame ir_frame = frames.first(RS2_STREAM_INFRARED);
      rs2::frame depth_frame = frames.get_depth_frame();

      // Creating OpenCV matrix from IR image
      Mat tempIR(Size(640, 480), CV_8UC1, (void*)ir_frame.get_data(), Mat::AUTO_STEP);
      gSourceImageIR = tempIR;

#ifdef BAL_DIST      
      // Apply Histogram Equalization
      equalizeHist( gSourceImageIR, gSourceImageIR );
      applyColorMap(gSourceImageIR, gSourceImageIR, COLORMAP_JET);
#endif      
#endif

    frameCount++;

    imshow("Original", gSourceImage); //show the original image
#ifndef WEBCAM
    imshow("IR", gSourceImageIR); //show the original image
#endif

    if(true == waitASecond.isExpired())
    {
      cout << "Frame count:" << (frameCount-lastFrameCount) << "\n";
      waitASecond.reset();
      lastFrameCount = frameCount;
    }

    if(true == config->getSaveOutput())
    {
      if(true == waitForSaving.isExpired())
      {
        string fileName = originalPath + "/frame-"+to_string(frameCount)+".jpg";
        imwrite( fileName, gSourceImage );

#ifndef WEBCAM
        string fileNameIR = distancePath + "/frame-"+to_string(frameCount)+".jpg";
        imwrite( fileNameIR, gSourceImageIR );
        
        cout << fileName << " " << fileNameIR << "\n";
        
#endif
        waitForSaving.reset();
      }
    }

    int key = waitKey(1);
    if (key == 32)
    {
      break;
    }
  }
    
  return 0;
}

