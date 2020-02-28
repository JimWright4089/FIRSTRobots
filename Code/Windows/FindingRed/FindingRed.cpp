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

//----------------------------------------------------------------------------
//  Includes
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
    int low1Value   =   0;
    int high1Value  = 255;
    int low2Value   =  90;
    int high2Value  = 226;
    int low3Value   = 214;
    int high3Value  = 255;

#ifndef FROM_IMAGE
    VideoCapture cap(1); //capture the video from web cam
    // Can't open the camera
    if (!cap.isOpened())  // if not success, exit program
    {
        cout << "Cannot open the web cam" << endl;
        return -1;
    }

    bool bSuccess = cap.read(mSourceImage); // read a new frame from video
#endif
    Size matSize;
    matSize.width = mSourceImage.cols;
    matSize.height = mSourceImage.rows;

    // Idle loop
    while (true)
    {
#ifdef FROM_IMAGE
        mSourceImage = imread("F:\\JimsRobot\\FIRSTRobots\\Code\\Windows\\TheBall.jpg", 3);
#else`
        bool bSuccess = cap.read(mSourceImage); // read a new frame from video

        if (!bSuccess) //if not success, break loop
        {
            cout << "Cannot read a frame from video stream" << endl;
            break;
        }
#endif
        Mat imgThresholded;
        Mat imgThresholdedCopy;

        inRange(mSourceImage, Scalar(low1Value, low2Value, low3Value),
            Scalar(high1Value, high2Value, high3Value), imgThresholded); //Threshold the image

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
            drawContours(mSourceImage, mContours, largestCont, color, 2, 8, mHierarchy, 0, Point());
            Scalar color2 = Scalar(255, 128, 255);
            rectangle(mSourceImage, mRectangle, color2, 2);
        }

        // Did we find the biggest contour, display it
        if (-1 != largestCont)
        {
            mRectangle = boundingRect(mContours.at(largestCont));
            approxPolyDP(Mat(mContours.at(largestCont)), mContours.at(largestCont), 3, true);
            printf("X:%4d Y:%4d W:%4d H:%4d A:%6.1f\n",
                mRectangle.x, mRectangle.y, mRectangle.width, mRectangle.height, largestArea);
        }

        // Show the images
        imshow("Thresholded Image", imgThresholded); //show the thresholded image
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
