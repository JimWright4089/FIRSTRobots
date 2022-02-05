#include "frame.hpp"

Frame::Frame()
{

}

Frame::Frame(int leftEye, int leftEyeLeft, int leftEyeTop, 
             int rightEye, int rightEyeLeft, int rightEyeTop)
{
  mLeftEye = leftEye;
  mLeftEyeTop = leftEyeTop;
  mLeftEyeLeft = leftEyeLeft;
  mRightEye = rightEye;
  mRightEyeTop = rightEyeTop;
  mRightEyeLeft = rightEyeLeft;
}
