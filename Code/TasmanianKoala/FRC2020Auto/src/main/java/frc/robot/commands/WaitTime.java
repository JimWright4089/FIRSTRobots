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

//----------------------------------------------------------------------------
//  Imports
//----------------------------------------------------------------------------
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.utils.*;

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: WaitTime
//
// Purpose:
//  Wait for an amount of time
//
//----------------------------------------------------------------------------
public class WaitTime extends CommandBase
{
  //----------------------------------------------------------------------------
  //  Class Atributes
  //----------------------------------------------------------------------------
  StopWatch mStopWatch = new StopWatch(1000);

  //----------------------------------------------------------------------------
  //  Purpose:
  //   Constructor
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public WaitTime(int millisecToWait) {
    mStopWatch = new StopWatch(millisecToWait);
  } 
  
  //----------------------------------------------------------------------------
  //  Purpose:
  //   Run the setup before the loop
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  @Override
  public void initialize()
  {
    System.out.println("WaitTime::initialize()");
    mStopWatch.reset();
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //   is the command done
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  @Override
  public boolean isFinished() {
    System.out.println("SW:"+mStopWatch.timeLeft());
    return mStopWatch.isExpired();
  }
}