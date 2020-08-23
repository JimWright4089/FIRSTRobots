//----------------------------------------------------------------------------
//
//  $Workfile: ColorLocator.cpp$
//
//  $Revision: X$
//
//  Project:    Open CV example
//
//                            Copyright (c) 2017
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
#include <iostream>

#define FROM_IMAGE

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

//--------------------------------------------------------------------
// Purpose:
//     Main entry point
//
// Notes:
//     None.
//--------------------------------------------------------------------
int main(int argc, char** argv)
{
    VideoCapture cap("/home/pi/robot/Media/Video/RobotVideo01.mp4");
//    VideoCapture cap(0); //capture the video from web cam
    // Can't open the camera
    if (!cap.isOpened())  // if not success, exit program
    {
        cout << "Cannot open the web cam" << endl;
        return -1;
    }

    bool bSuccess = cap.read(mSourceImage); // read a new frame from video
   
    // Idle loop
    while (true)
    {
        bool bSuccess = cap.read(mSourceImage); // read a new frame from video

        if (!bSuccess) //if not success, break loop
        {
            cout << "Cannot read a frame from video stream" << endl;
            break;
        }

        imshow("Original", mSourceImage); //show the original image

        //wait for 'esc' key press for 30ms. If 'esc' key is pressed, break loop
        if (waitKey(30) == 27) 
        {
            cout << "esc key is pressed by user" << endl;
            break;
        }
    }

    return 0;
}
