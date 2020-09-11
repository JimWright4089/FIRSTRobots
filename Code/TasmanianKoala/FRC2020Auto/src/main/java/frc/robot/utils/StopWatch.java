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

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: StopWatch
//
// Purpose:
//  mark off an amount of time
//
//----------------------------------------------------------------------------
public class StopWatch 
{
  //----------------------------------------------------------------------------
  //  Class atributes
  //----------------------------------------------------------------------------
  long mLastTime = 0;
	int mWaitTime = 0;
	
  // --------------------------------------------------------------------
  // Purpose:
  //   Constructor
  //
  // Notes:
  //   None.
  // --------------------------------------------------------------------
  public StopWatch(int waitTime)
	{
		mWaitTime = waitTime;
	}
	
  // --------------------------------------------------------------------
  // Purpose:
  //   set the time to wait
  //
  // Notes:
  //   None.
  // --------------------------------------------------------------------
	public void setTime(int waitTime)
	{
		mWaitTime = waitTime;
	}
	
  // --------------------------------------------------------------------
  // Purpose:
  //   is the watch finished
  //
  // Notes:
  //   None.
  // --------------------------------------------------------------------
	public boolean isExpired()
	{
		if((System.currentTimeMillis()-mLastTime)>mWaitTime)
		{
			return true;
		}
		return false;
	}
	
  // --------------------------------------------------------------------
  // Purpose:
  //   Reset the watch
  //
  // Notes:
  //   None.
  // --------------------------------------------------------------------
	public void reset()
	{
		mLastTime = System.currentTimeMillis(); 
	}
	
  // --------------------------------------------------------------------
  // Purpose:
  //   return the amount of time left
  //
  // Notes:
  //   None.
  // --------------------------------------------------------------------
	public long timeLeft()
	{
		return mWaitTime - (System.currentTimeMillis()-mLastTime);
	}
}