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
//     This is the main entry point
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
#define MASK


Mat gSourceImage;
Mat gSourceImageOutline;
#ifndef WEBCAM
Mat gSourceImageIR;
Mat gSourceImageIROutline;
#endif
int gMorphSize = 7;
vector<vector<Point> > gContours;
vector<Vec4i> gHierarchy;
Rect gRectangle;
Mat gMaskImage;


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
  string thresholdPath = config->getOutputThresholdPath();
  StopWatch waitASecond;
  StopWatch waitForSaving;
  rs2::colorizer theColorizer;
  Mat imageInHueSatVal;
  Mat imgThresholded;
  Mat imgThresholdedCopy;
  
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
    thresholdPath = config->getOutputThresholdPath();
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
  cfg.enable_stream(RS2_STREAM_COLOR);
  cfg.enable_stream(RS2_STREAM_DEPTH);

  //Instruct pipeline to start streaming with the requested configuration
  pipe.start(cfg);

  // that should not be performed in the main loop
  rs2::align align_to_depth(RS2_STREAM_DEPTH);
  rs2::align align_to_color(RS2_STREAM_COLOR);

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
      // Using the align object, we block the application until a frameset is available
      rs2::frameset frameset = pipe.wait_for_frames();

      // Align all frames to color viewport
      frameset = align_to_color.process(frameset);

      // With the aligned frameset we proceed as usual
      rs2::frame depth_frame = frameset.get_depth_frame();
      rs2::frame color_frame = frameset.get_color_frame();
      rs2::frame colorized_depth = theColorizer.colorize(depth_frame);

      // Creating OpenCV Matrix from a color image
      Mat tempColor(Size(640, 480), CV_8UC3, (void*)color_frame.get_data(), Mat::AUTO_STEP);
      gSourceImage = tempColor;

      // Creating OpenCV matrix from IR image
      Mat tempIR(Size(640, 480), CV_8UC3, (void*)colorized_depth.get_data(), Mat::AUTO_STEP);
      gSourceImageIR = tempIR;
#endif

    frameCount++;
    
    cvtColor(gSourceImage, gSourceImage, COLOR_RGB2BGR);
    // Convert the captured frame from BGR to HSV
    // I like working in Hue Sat Value
    cvtColor(gSourceImage, imageInHueSatVal, COLOR_BGR2HSV);

    inRange(imageInHueSatVal, 
        Scalar( config->getHueLow(),  config->getSatLow(),  config->getValLow()), 
        Scalar( config->getHueHigh(), config->getSatHigh(), config->getValHigh()), 
        
        imgThresholded); //Threshold the image

    //morphological opening (remove small objects from the foreground)
    erode(imgThresholded, imgThresholded, 
        getStructuringElement(MORPH_ELLIPSE, Size(gMorphSize, gMorphSize)));
    dilate(imgThresholded, imgThresholded, 
        getStructuringElement(MORPH_ELLIPSE, Size(gMorphSize, gMorphSize)));

    //morphological closing (fill small holes in the foreground)
    dilate(imgThresholded, imgThresholded, 
        getStructuringElement(MORPH_ELLIPSE, Size(gMorphSize, gMorphSize)));
    erode(imgThresholded, imgThresholded, 
        getStructuringElement(MORPH_ELLIPSE, Size(gMorphSize, gMorphSize)));

#ifdef MASK
    gSourceImage.copyTo(gSourceImageOutline,imgThresholded);
#ifndef WEBCAM  
    gSourceImageIR.copyTo(gSourceImageIROutline,imgThresholded);
#endif
#else
    // finding the contours corupts the image passed in
    imgThresholded.copyTo(imgThresholdedCopy);
    gSourceImage.copyTo(gSourceImageOutline);
    gSourceImageIR.copyTo(gSourceImageIROutline);
    
    findContours(imgThresholdedCopy, gContours, gHierarchy, 
        RETR_TREE, CHAIN_APPROX_SIMPLE, Point(0, 0));
    
    for (int i = 0; i< (int)gContours.size(); i++)
    {
      vector<Point> countour = gContours.at(i);
      double area = contourArea(countour);
      
      if(area > config->getMinArea())
      {
        gRectangle = boundingRect(countour);
        approxPolyDP(Mat(countour), countour, 3, true);

        Scalar color = Scalar(255, 255, 255);
        drawContours(gSourceImageOutline, gContours, i, color, 2, 8, gHierarchy, 0, Point());
        Scalar color2 = Scalar( config->getRGBR(),config->getRGBG(),config->getRGBB());
        rectangle(gSourceImageOutline, gRectangle, color2, 2);
      }
    }
#endif    
    imshow("Thresholded Image", imgThresholded); //show the thresholded image
    imshow("Original Outline", gSourceImageOutline); //show the thresholded image

    imshow("Original", gSourceImage); //show the original image
#ifndef WEBCAM
    imshow("IR", gSourceImageIR); //show the original image
    imshow("IR Outline", gSourceImageIROutline); //show the original image
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

        fileName = originalPath + "/frameOut-"+to_string(frameCount)+".jpg";
        imwrite( fileName, gSourceImageOutline );

        fileName = thresholdPath + "/frame-"+to_string(frameCount)+".jpg";
        imwrite( fileName, imgThresholded );

#ifndef WEBCAM
        string fileNameIR = distancePath + "/frame-"+to_string(frameCount)+".jpg";
        imwrite( fileNameIR, gSourceImageIR );

        fileNameIR = distancePath + "/frameOut-"+to_string(frameCount)+".jpg";
        imwrite( fileNameIR, gSourceImageIROutline );
#endif
        cout << fileName << " " << fileNameIR << "\n";
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

