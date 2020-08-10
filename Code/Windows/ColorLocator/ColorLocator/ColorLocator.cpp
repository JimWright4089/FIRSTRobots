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

    namedWindow("Control"); //create a window called "Control"

    // Tracker values
    int iLowH = 35;
    int iHighH = 255;

    int iLowS = 117;
    int iHighS = 255;

    int iLowV = 89;
    int iHighV = 188;

    //Create trackbars in "Control" window
    createTrackbar("LowH", "Control", &iLowH, 255); //Hue (0 - 179)
    createTrackbar("HighH", "Control", &iHighH, 255);

    createTrackbar("LowS", "Control", &iLowS, 255); //Saturation (0 - 255)
    createTrackbar("HighS", "Control", &iHighS, 255);

    createTrackbar("LowV", "Control", &iLowV, 255); //Value (0 - 255)
    createTrackbar("HighV", "Control", &iHighV, 255);

    Size matSize;
    matSize.width = mSourceImage.cols;
    matSize.height = mSourceImage.rows;
   
    // Idle loop
    while (true)
    {
#ifdef FROM_IMAGE
        mSourceImage = imread("F:\\JimsRobot\\FIRSTRobots\\Code\\Windows\\Ball.png", 3);
#else`
        bool bSuccess = cap.read(mSourceImage); // read a new frame from video

        if (!bSuccess) //if not success, break loop
        {
            cout << "Cannot read a frame from video stream" << endl;
            break;
        }
#endif

        Mat imageInHueSatVal;

        // Convert the captured frame from BGR to HSV
        // I like working in Hue Sat Value
        cvtColor(mSourceImage, imageInHueSatVal, COLOR_BGR2HSV);
        //mSourceImage.copyTo(imageInHueSatVal);

        Mat imgThresholded;
        Mat imgThresholdedCopy;

        inRange(imageInHueSatVal, Scalar(iLowH, iLowS, iLowV), 
            Scalar(iHighH, iHighS, iHighV), imgThresholded); //Threshold the image

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
/*
        vector<Vec3f> circles;
        HoughCircles(imgThresholdedCopy, circles, HOUGH_GRADIENT, 1,
            mSourceImage.rows / 16,  // change this value to detect circles with different distances to each other
            100, 30, 1, 30 // change the last two parameters
       // (min_radius & max_radius) to detect larger circles
        );
        for (size_t i = 0; i < circles.size(); i++)
        {
            Vec3i c = circles[i];
            Point center = Point(c[0], c[1]);
            // circle center
            circle(mSourceImage, center, 1, Scalar(0, 100, 100), 3, LINE_AA);
            // circle outline
            int radius = c[2];
            circle(mSourceImage, center, radius, Scalar(255, 0, 255), 3, LINE_AA);
        }
*/


        findContours(imgThresholdedCopy, mContours, mHierarchy, 
            RETR_TREE, CHAIN_APPROX_SIMPLE, Point(0, 0));

        double largestArea = -1;
        double largestCirc = 1;
        int largestCont = -1;
        
        for (int i = 0; i< mContours.size(); i++)
        {
            double area = contourArea(mContours.at(i));
            //Moments hu = moments(mContours[i], false);
            double circle = 4 * CV_PI * area / pow(arcLength(mContours[i], true), 2);

            if ((area > 4000) && (circle > largestArea))
            {
                largestArea = circle;
                largestCirc = circle;
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
            printf("X:%4d Y:%4d W:%4d H:%4d A:%6.1f C:%6.1f\n", 
                mRectangle.x, mRectangle.y, mRectangle.width, mRectangle.height, largestArea, largestCirc);
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
