//----------------------------------------------------------------------------
//
//  $Workfile: WaitForTime.java$
//
//  $Revision: X$
//
//  Project:    Tornado Platypus
//
//                            Copyright (c) 2020
//                              James A Wright
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
package frc.robot.command;

//----------------------------------------------------------------------------
//  Imports
//----------------------------------------------------------------------------
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.hal.HALUtil;

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: WaitForTime
//
// Purpose:
//   Wait a  bit of time
//
//----------------------------------------------------------------------------
public class WaitForTime extends CommandBase {
  long mLengthOfTimeMs = 1000;
  long mStartOfTime = 0;
  long mHALStartOfTime = 0;

  // ----------------------------------------------------------------------------
  // Purpose:
  // Constructor
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  public WaitForTime() {
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Constructor
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  public WaitForTime(long lengthOfTimeMs) {
    mLengthOfTimeMs = lengthOfTimeMs;
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Initialize the command
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  @Override
  public void initialize() {
    System.out.println("public void initialize()");
    mHALStartOfTime = HALUtil.getFPGATime();
    mStartOfTime = System.currentTimeMillis();
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Main loop frame
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  @Override
  public void execute() {
    System.out.print("public void execute(");
    System.out.print(System.currentTimeMillis() - mStartOfTime);
    System.out.print(" ");
    System.out.print((HALUtil.getFPGATime() - mHALStartOfTime) / 1000.0);
    System.out.println(")");
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Has the timer expired
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  @Override
  public boolean isFinished() {
    boolean finished = ((System.currentTimeMillis() - mStartOfTime) > mLengthOfTimeMs);
    System.out.print("public boolean isFinished(");
    System.out.print(finished);
    System.out.println(")");
    return finished;
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // End the command
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  @Override
  public void end(boolean interupted) {
    System.out.print("public void end(");
    System.out.print(interupted);
    System.out.println(")");
  }
}
