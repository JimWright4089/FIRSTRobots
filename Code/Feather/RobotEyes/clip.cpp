#include "clip.hpp"

void Clip::add(Frame frame)
{
  mFrames.push_back(frame);
}

int Clip::getFrameNumber(void)
{
  return mCurFrame;
}

int Clip::getFrameSize(void)
{
  return mFrames.size();
}

void Clip::reset(bool rev)
{
  mReverse = rev;

  if(false == mReverse)
  {
    mCurFrame = 0;
  }
  else
  {
    mCurFrame = mFrames.size()-2;
  }
}

Frame Clip::getFrame(int num)
{
  return mFrames[num];  
}

Frame Clip::getNextFrame()
{
  Frame frame = mFrames[mCurFrame];

  if(false == mReverse)
  {  
    mCurFrame++;
  }
  else
  {
    mCurFrame--;
  }
  return(frame);
}

bool Clip::finished()
{
  if(false == mReverse)
  {
    return (mCurFrame >= mFrames.size());
  }
  else
  {
    return (mCurFrame < 0);
  }
}
