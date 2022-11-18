#include <stdio.h>
#include <opencv2/opencv.hpp>

using namespace cv;

int main(int argc, char** argv)
{
  Mat frame;

  VideoCapture capture;
  capture.open(0);
  namedWindow("Image", WINDOW_KEEPRATIO);

  while (1) 
  {
    capture >> frame;
    if (frame.empty())
    {
        break;
    }
    
    imshow("Image", frame);

    char key = (char)waitKey(30); //delay N millis, usually long enough to display and capture input
    if (key == 27) 
    {
      break;
    }
  }
}
