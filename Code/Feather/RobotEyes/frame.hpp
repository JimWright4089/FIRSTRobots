#ifndef __FRAME_HPP__
#define __FRAME_HPP__

class Frame {
public:
  int mLeftEye = 1;
  int mLeftEyeTop = 0;
  int mLeftEyeLeft = 3;
  
  int mRightEye = 1;
  int mRightEyeTop = 0;
  int mRightEyeLeft = -3;

  Frame();
  Frame(int leftEye, int leftEyeTop, int leftEyeLeft, 
                 int rightEye, int rightEyeTop, int rightEyeLeft);
private:
};

#endif
