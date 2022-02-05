#ifndef __CLIP_HPP__
#define __CLIP_HPP__

#include "frame.hpp"
#include "Arduino.h"
#include <vector>

class Clip {
public:
  void add(Frame frame);
  void reset(bool rev);
  Frame getNextFrame();
  int getFrameSize(void);
  bool finished();
  int getFrameNumber(void);
  Frame getFrame(int num);

private:
  std::vector<Frame> mFrames;
  int mCurFrame = 0;
  bool mReverse = false;
};

#endif
