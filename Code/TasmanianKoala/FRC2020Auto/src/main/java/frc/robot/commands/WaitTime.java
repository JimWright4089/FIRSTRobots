//----------------------------------------------------------------------------
//
//  $Workfile: WaitTime.java$
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
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.utils.*;

public class WaitTime extends CommandBase
{
  StopWatch mStopWatch = new StopWatch(1000);

  public WaitTime(int millisecToWait) {
    mStopWatch = new StopWatch(millisecToWait);
  } 
  
  @Override
  public void initialize()
  {
    mStopWatch.reset();
  }

  @Override
  public boolean isFinished() {
    System.out.println("SW:"+mStopWatch.timeLeft());
    return mStopWatch.isExpired();
  }
}