//----------------------------------------------------------------------------
//
//  $Workfile: bumpberfinder.cpp$
//
//  $Revision: X$
//
//  Project:    Open CV example
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
//     This is a tool for tuning a camera
//
//----------------------------------------------------------------------------
#include "opencv2/core.hpp"
#include "opencv2/imgproc.hpp"
#include "opencv2/highgui.hpp"
#include <stdio.h>
#include <sys/time.h>
#include <iostream>
#include <fstream>
#include <jsoncpp/json/json.h>
#include "StopWatch.hpp"

using namespace cv;
using namespace std;

//----------------------------------------------------------------------------
//  File Locals
//----------------------------------------------------------------------------
int mMorphSize = 7;
vector<vector<Point> > mContours;
vector<Vec4i> mHierarchy;
Rect mRectangle;
Mat mSourceImage;
Mat mMaskImage;
Json::Value mColorData;
StopWatch mStopWatch;

//----------------------------------------------------------------------------
//  Funtion defines
//----------------------------------------------------------------------------
void loadColorFile();

//--------------------------------------------------------------------
// Purpose:
//     Main entry point
//
// Notes:
//     None.
//--------------------------------------------------------------------
int main(int argc, char** argv)
{
    int count = 0;
    
    loadColorFile();

    VideoCapture cap("/home/pi/robot/Media/Video/RobotVideo01.mp4");
    //    VideoCapture cap(0); //capture the video from web cam
    // Can't open the camera
    if (!cap.isOpened())  // if not success, exit program
    {
        cout << "Cannot open the web cam" << endl;
        return -1;
    }
    
    mMaskImage = imread("maskFrame.jpg", 1);

    mStopWatch.Reset();
    // Idle loop
    while (true)
    {
        bool bSuccess = cap.read(mSourceImage); // read a new frame from video
        count++;

        if (!bSuccess) //if not success, break loop
        {
            cout << "Cannot read a frame from video stream" << endl;
            break;
        }

        imwrite("Image.jpg",mSourceImage);

        Mat imageInHueSatVal;

        // Convert the captured frame from BGR to HSV
        // I like working in Hue Sat Value
        cvtColor(mSourceImage, imageInHueSatVal, COLOR_BGR2HSV);

        const Json::Value colors = mColorData["colors"];  
        for ( int index = 0; index < (int)colors.size(); ++index )
        {
            String colorName = colors[index]["color"].asString() ;

            Mat imgThresholded;
            Mat imgThresholdedCopy;

            inRange(imageInHueSatVal, 
                Scalar( colors[index]["code"]["h"][0].asUInt(), 
                        colors[index]["code"]["s"][0].asUInt(), 
                        colors[index]["code"]["v"][0].asUInt()), 
                Scalar( colors[index]["code"]["h"][1].asUInt(), 
                        colors[index]["code"]["s"][1].asUInt(), 
                        colors[index]["code"]["v"][1].asUInt()), 
                imgThresholded); //Threshold the image

            //bitwise_and(imgThresholded, mMaskImage, imgThresholded);

            //morphological opening (remove small objects from the foreground)
            erode(imgThresholded, imgThresholded, 
                getStructuringElement(MORPH_ELLIPSE, Size(mMorphSize, mMorphSize)));
            dilate(imgThresholded, imgThresholded, 
                getStructuringElement(MORPH_ELLIPSE, Size(mMorphSize, mMorphSize)));

            //morphological closing (fill small holes in the foreground)
            dilate(imgThresholded, imgThresholded, 
                getStructuringElement(MORPH_ELLIPSE, Size(mMorphSize, mMorphSize)));
            erode(imgThresholded, imgThresholded, 
                getStructuringElement(MORPH_ELLIPSE, Size(mMorphSize, mMorphSize)));

            // finding the contours corupts the image passed in
            imgThresholded.copyTo(imgThresholdedCopy);

            findContours(imgThresholdedCopy, mContours, mHierarchy, 
                RETR_TREE, CHAIN_APPROX_SIMPLE, Point(0, 0));
            
            for (int i = 0; i< (int)mContours.size(); i++)
            {
                vector<Point> countour = mContours.at(i);
                double area = contourArea(countour);

                if ((area > 100) && (area < 1000))
                {
                    mRectangle = boundingRect(countour);
                    approxPolyDP(Mat(countour), countour, 3, true);

                    Scalar color = Scalar(255, 255, 255);
                    drawContours(mSourceImage, mContours, i, color, 2, 8, mHierarchy, 0, Point());
                    Scalar color2 = Scalar( colors[index]["code"]["rgb"][0].asUInt(), 
                                            colors[index]["code"]["rgb"][1].asUInt(), 
                                            colors[index]["code"]["rgb"][2].asUInt());
                    rectangle(mSourceImage, mRectangle, color2, 2);

                    mRectangle = boundingRect(countour);
                    
/*                    
                    cout << colorName << " ";
                    
                    printf("X:%4d Y:%4d W:%4d H:%4d A:%6.1f\n", 
                        mRectangle.x, mRectangle.y, mRectangle.width, mRectangle.height, area);
*/
                }
            }
           // imshow("Thresholded Image", imgThresholded); //show the thresholded image
        }
        // Show the images
        imshow("Original", mSourceImage); //show the original image

        if(true == mStopWatch.IsExpired())
        {
            cout << "Count:"<<count<<"\n";
            count=0;
            mStopWatch.Reset();
        }

        //wait for 'esc' key press for 30ms. If 'esc' key is pressed, break loop
        if (waitKey(30) == 27) 
        {
            cout << "esc key is pressed by user" << endl;
            break;
        }
    }
    return 0;
}

//--------------------------------------------------------------------
// Purpose:
//     Load the color file into memory
//
// Notes:
//     None.
//--------------------------------------------------------------------
void loadColorFile()
{
  ifstream ifs("colors.json");
  Json::Reader reader;

  reader.parse(ifs, mColorData);
}


StopWatch::StopWatch() :
  mLastTime(0),
  mWaitTime(1000)
{
}
	
StopWatch::StopWatch(int waitTime) :
  mLastTime(0),
  mWaitTime(waitTime)
{
}

long StopWatch::Now(void)
{
  struct timeval theTime;
  gettimeofday(&theTime,NULL);
  return ((theTime.tv_sec * 1000) + (theTime.tv_usec/1000));
}

void StopWatch::SetTime(int waitTime)
{
  mWaitTime = waitTime;
}

bool StopWatch::IsExpired(void)
{
  if((Now() - mLastTime)>mWaitTime)
  {
     return true;
  }
  return false;
}

void StopWatch::Reset(void)
{
  mLastTime = Now();
}

long StopWatch::GetTimeLeft()
{
  return mWaitTime - (Now()-mLastTime);
}

