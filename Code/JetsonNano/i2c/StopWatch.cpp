//----------------------------------------------------------------------------
//
//  $Workfile: StopWatch.cpp$
//
//  $Revision: X$
//
//  Project:    JimsFridge
//
//                            Copyright (c) 2017
//                                Jim Wright
//                            All Rights Reserved
//
//  Modification History:
//  $Log:
//  $
//
//----------------------------------------------------------------------------
#include <stdio.h>
#include <sys/time.h>
#include "StopWatch.h"
 
StopWatch::StopWatch() :
  mLastTime(0),
  mWaitTime(1000),
  mIsSeconds(false)
{
}

StopWatch::StopWatch(uint32_t waitTime) :
  mLastTime(0),
  mWaitTime(waitTime),
  mIsSeconds(false)
{
}

StopWatch::StopWatch(bool isSeconds) :
  mLastTime(0),
  mWaitTime(1000),
  mIsSeconds(isSeconds)
{
}

StopWatch::StopWatch(uint32_t waitTime, bool isSeconds) :
  mLastTime(0),
  mWaitTime(waitTime),
  mIsSeconds(isSeconds)
{
}

uint32_t StopWatch::Now(void)
{
  struct timeval theTime;
  gettimeofday(&theTime, NULL);
  return ((theTime.tv_sec * 1000) + (theTime.tv_usec / 1000));
}

uint32_t StopWatch::NowInSeconds(void)
{
  struct timeval theTime;
  gettimeofday(&theTime, NULL);
  return theTime.tv_sec;
}

void StopWatch::SetTime(uint32_t waitTime)
{
  mWaitTime = waitTime;
}

bool StopWatch::IsExpired(void)
{
  if (false == mIsSeconds)
  {
    if ((Now() - mLastTime) > mWaitTime)
    {
      return true;
    }
  }
  else
  {
    if ((NowInSeconds() - mLastTime) > mWaitTime)
    {
      return true;
    }
  }
  return false;
}

void StopWatch::Reset(void)
{
  if (false == mIsSeconds)
  {
    mLastTime = Now();
  }
  else
  {
    mLastTime = NowInSeconds();
  }
}

int32_t StopWatch::GetTimeLeft()
{
  if (false == mIsSeconds)
  {
    return mWaitTime - (Now() - mLastTime);
  }
  else
  {
    return mWaitTime - (NowInSeconds() - mLastTime);
  }
}

