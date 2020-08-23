#include <stdio.h>
#include <sys/time.h>
#include "StopWatch.hpp"

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

