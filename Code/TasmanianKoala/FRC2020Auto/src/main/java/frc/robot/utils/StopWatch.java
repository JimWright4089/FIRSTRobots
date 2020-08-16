//----------------------------------------------------------------------------
//
//  $Workfile: StopWatch.java$
//
//  $Revision: X$
//
//  Project:    Tasmanian Koala
//
//                            Copyright (c) 2020
//                                 Jim Wright
//                            All Rights Reserved
//
//  Modification History:
//  $Log:
//  $
//
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
//  Package
//----------------------------------------------------------------------------
package frc.robot.utils;

public class StopWatch 
{
	long mLastTime = 0;
	int mWaitTime = 0;
	
	public StopWatch(int waitTime)
	{
		mWaitTime = waitTime;
	}
	
	public void setTime(int waitTime)
	{
		mWaitTime = waitTime;
	}
	
	public boolean isExpired()
	{
		if((System.currentTimeMillis()-mLastTime)>mWaitTime)
		{
			return true;
		}
		return false;
	}
	
	public void reset()
	{
		mLastTime = System.currentTimeMillis(); 
	}
	
	public long timeLeft()
	{
		return mWaitTime - (System.currentTimeMillis()-mLastTime);
	}
}