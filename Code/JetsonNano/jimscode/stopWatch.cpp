//----------------------------------------------------------------------------
//
//  $Workfile: stopWatch.cpp$
//
//  $Revision: X$
//
//  Project:    Ball Locator
//
//                            Copyright (c) 2020
//                               James A Wright
//                            All Rights Reserved
//
//  Modification History:
//  $Log:
//  $
//
//  Notes:
//     This is stopwatch counter class
//
//----------------------------------------------------------------------------

//----------------------------------------------------------------------------
//  Includes
//----------------------------------------------------------------------------
#include <stdio.h>
#include <sys/time.h>
#include "stopWatch.h"

//----------------------------------------------------------------------------
//  Purpose:
//   Constructor
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
StopWatch::StopWatch() :
  mLastTime(0),
  mWaitTime(1000)
{
}
	
//----------------------------------------------------------------------------
//  Purpose:
//   Constructor
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
StopWatch::StopWatch(int waitTime) :
  mLastTime(0),
  mWaitTime(waitTime)
{
}

//----------------------------------------------------------------------------
//  Purpose:
//   Return the current system time
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
long StopWatch::now(void)
{
  struct timeval theTime;
  gettimeofday(&theTime,NULL);
  return ((theTime.tv_sec * 1000) + (theTime.tv_usec/1000));
}

//----------------------------------------------------------------------------
//  Purpose:
//   Sets the time to wait
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
void StopWatch::setTime(int waitTime)
{
  mWaitTime = waitTime;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Checks to see if the time is expired
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
bool StopWatch::isExpired(void)
{
  if((now() - mLastTime)>mWaitTime)
  {
     return true;
  }
  return false;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Resets the time
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
void StopWatch::reset(void)
{
  mLastTime = now();
}

//----------------------------------------------------------------------------
//  Purpose:
//   Gets the time left to wait
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
long StopWatch::getTimeLeft()
{
  return mWaitTime - (now()-mLastTime);
}

