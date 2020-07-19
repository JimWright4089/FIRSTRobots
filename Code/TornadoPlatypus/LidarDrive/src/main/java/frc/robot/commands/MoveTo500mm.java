//----------------------------------------------------------------------------
//
//  $Workfile: MoveTo500mm.java$
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
package frc.robot.commands;

//----------------------------------------------------------------------------
//  Imports
//----------------------------------------------------------------------------
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Lidar;
import frc.robot.utils.DriveMath;

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: MoveTo500mm
//
// Purpose:
//   Move the robot to 500mm in front of a wall, and adjust the perpendicularness
//
//----------------------------------------------------------------------------
public class MoveTo500mm extends CommandBase {
  // ----------------------------------------------------------------------------
  // Class Attributes
  // ----------------------------------------------------------------------------
  double mTarget = 500;
  double mCurrent = 10000;
  DriveSubsystem mDrive = DriveSubsystem.getInstance();
  Lidar mLidar = Lidar.getInstance();
  double mCenter = 0;

  // ----------------------------------------------------------------------------
  // Purpose:
  // Contstructor
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  public MoveTo500mm(double target) {
    mTarget = target;
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Check to see if the robot is in the right place
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  @Override
  public boolean isFinished() {
    return ((mCurrent < mTarget) && (DriveMath.DeadBand(mCenter, .06) == 0.0));
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Move the robot into place
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  @Override
  public void execute() {
    mCurrent = mLidar.get0();
    double center = (mLidar.get45() - mLidar.getNdeg45()) / 10000;
    mCenter = DriveMath.Cap(center, .05);

    System.out.print("execute ");
    System.out.print(mCurrent);
    System.out.print(" ");
    System.out.print(mTarget);
    System.out.print(" ");
    System.out.print(mLidar.get45());
    System.out.print(" ");
    System.out.print(mLidar.getNdeg45());

    if (mCurrent > mTarget) {
      mDrive.tankDrive(.3 - mCenter, .3 + mCenter);
    } else {
      System.out.print(" ");
      System.out.print(mCenter);
      System.out.print(" here ");
      center = mCenter * 30;
      center = DriveMath.Cap(center, .30);
      mDrive.tankDrive(-center, center);
    }
    System.out.print(" ");
    System.out.println(mCenter);

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
    mDrive.tankDrive(0, 0);
    System.out.print("End:");
    System.out.println(interupted);
  }
}