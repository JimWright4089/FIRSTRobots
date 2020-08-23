
#pragma once

class StopWatch
{
	long mLastTime;
	long mWaitTime;

        long Now(void);

	public:
	StopWatch();
	StopWatch(int waitTime);
	void SetTime(int waitTime);
	bool IsExpired(void);
	void Reset(void);
        long GetTimeLeft(void);
};

