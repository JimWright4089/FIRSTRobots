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
#include "opencv2/core.hpp"
#include "opencv2/imgproc.hpp"
#include "opencv2/highgui.hpp"
#include "config.h"
#include "stopWatch.h"
#include "utils.h"
using namespace std;
using namespace cv;

Mat gSourceImage;

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
    waitForSaving.setTime(config->getSaveOutputTime());
  }

  //------------------------------------------
  //  Setup the comera
  //------------------------------------------
  VideoCapture cap(0); //capture the video from web cam
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

  waitASecond.reset();
  waitForSaving.reset();
  //------------------------------------------
  //  Run forever
  //------------------------------------------
  while (true)
  {
    bool bSuccess = cap.read(gSourceImage); // read a new frame from video

    frameCount++;
    
    if(true == config->getSaveOutput())
    {
      if(true == waitForSaving.isExpired())
      {
        string fileName = originalPath + "/frame-"+to_string(frameCount)+".jpg";
        imwrite( fileName, gSourceImage );
        cout << fileName << "\n";
        waitForSaving.reset();
      }
    }
        
    if (!bSuccess) //if not success, break loop
    {
      cout << "Cannot read a frame from video stream" << endl;
      break;
    }
    imshow("Original", gSourceImage); //show the original image

    if(true == waitASecond.isExpired())
    {
      cout << "Frame count:" << (frameCount-lastFrameCount) << "\n";
      waitASecond.reset();
      lastFrameCount = frameCount;
    }

    int key = waitKey(1);
    if (key == 32)
    {
      break;
    }
  }
    
  return 0;
}

