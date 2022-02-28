//  g++ -o sin sin.cpp Jims_I2CDevice.cpp Qwiic_LED_Stick.cpp -I/usr/local/frc/include/ -std=c++17 -lntcore -lwpiutil -L/usr/local/frc/lib/

#include <cstdio>
#include <string>
#include <thread>
#include <vector>
#include <math.h>
#include <iostream>

#include <networktables/NetworkTableInstance.h>
#include <wpi/StringRef.h>
#include <wpi/json.h>
#include <wpi/raw_istream.h>
#include <wpi/raw_ostream.h>
#include "Qwiic_LED_Stick.h"


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

unsigned int team;
bool server = false;

bool ReadConfig() {
  // open config file
  std::error_code ec;
  wpi::raw_fd_istream is(configFile, ec);
  if (ec) {
    std::cout << "could not open '" << configFile << "': " << ec.message()
                << '\n';
    return false;
  }

  // parse file
  wpi::json j;
  try {
    j = wpi::json::parse(is);
  } catch (const wpi::json::parse_error& e) {
    std::cout << "byte " << e.byte << ": " << e.what() << '\n';
    return false;
  }

  // top level must be an object
  if (!j.is_object()) {
    std::cout << "must be JSON object\n";
    return false;
  }

  // team number
  try {
    team = j.at("team").get<unsigned int>();
  } catch (const wpi::json::exception& e) {
    std::cout << "could not read team number: " << e.what() << '\n';
    return false;
  }
  std::cout << "team:"<<team<< '\n';


  // ntmode (optional)
  if (j.count("ntmode") != 0) {
    try {
      auto str = j.at("ntmode").get<std::string>();
      wpi::StringRef s(str);
      if (s.equals_lower("client")) {
        server = false;
      } else if (s.equals_lower("server")) {
        server = true;
      } else {
        std::cout << "could not understand ntmode value '" << str << "'\n";
      }
    } catch (const wpi::json::exception& e) {
      std::cout << "could not read ntmode: " << e.what() << '\n';
    }
  }

  return true;
}

int main(int argc, char* argv[]) 
{
  nt::NetworkTableEntry mSin;
  double count = 0.0;
  uint8_t red = 0;

  LED ledStrip;
  ledStrip.begin();

  if (argc >= 2) configFile = argv[1];

  // read configuration
  if (!ReadConfig()) return EXIT_FAILURE;

  // start NetworkTables
  auto ntinst = nt::NetworkTableInstance::GetDefault();
  ntinst.StartClientTeam(team);
  ntinst.StartDSClient();
  mSin = ntinst.GetEntry("/rp/sin2");

  // loop forever
  for (;;)
  {
    double sinValue = sin(count);
    mSin.SetValue(nt::Value::MakeDouble(sinValue));
    count += 0.01;
    red++;
    ledStrip.setLEDColor(red,0x80,0x80);
    std::this_thread::sleep_for(std::chrono::milliseconds(20));
  } 
}
