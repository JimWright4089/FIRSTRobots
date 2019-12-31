//----------------------------------------------------------------------------
//
//  $Workfile: StopWatch.h$
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
#pragma once
#include <stdint.h>

class StopWatch
{
  uint32_t mLastTime;
  uint32_t mWaitTime;
  bool mIsSeconds;

  public:
    StopWatch();
    StopWatch(uint32_t waitTime);
    StopWatch(bool isSeconds);
    StopWatch(uint32_t waitTime, bool isSeconds);
    void SetTime(uint32_t waitTime);
    bool IsExpired(void);
    void Reset(void);
    int32_t GetTimeLeft(void);
    static uint32_t Now(void);
    static uint32_t NowInSeconds(void);
};

