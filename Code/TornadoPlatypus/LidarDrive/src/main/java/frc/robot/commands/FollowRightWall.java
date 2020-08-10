//----------------------------------------------------------------------------
//
//  $Workfile: FollowRightWall.java$
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
// Class Name: FollowLeftWall
//
// Purpose:
//   Follow the wall, right hand
//
//----------------------------------------------------------------------------
public class FollowRightWall extends CommandBase {
  // ----------------------------------------------------------------------------
  // Class Attributes
  // ----------------------------------------------------------------------------
  double mTarget = 500;
  double mCurrent = 10000;
  DriveSubsystem mDrive = DriveSubsystem.getInstance();
  Lidar mLidar = Lidar.getInstance();
  double mCenter = 0;
  double mSpeed = 0.55;
  double mSlowSpeed = 0.30;
  double mCorrection = 0.05;
  int mFrontCount = 0;

  // ----------------------------------------------------------------------------
  // Purpose:
  // Contstructor
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  public FollowRightWall(double target) {
    mTarget = target;
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Check if the command is finished
  //
  // Notes:
  // This should follow the wall forever
  //
  // ----------------------------------------------------------------------------
  @Override
  public boolean isFinished() {
    return false;
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Follow the wall
  //
  // Notes:
  // This is a really rough way of following
  //
  // ----------------------------------------------------------------------------
  @Override
  public void execute() {

    if ((true == mLidar.is60Stale()) && (true == mLidar.is5Stale()) && (true == mLidar.is65Stale())) {
      System.out.format("%b %b %b \n", mLidar.is5Stale(), mLidar.is60Stale(), mLidar.is65Stale());
      mDrive.tankDrive(mSlowSpeed, mSlowSpeed);
    } else {
      double center = 0;
      if (false == mLidar.is60Stale()) {
        center = mLidar.get60();
      } else {
        if (false == mLidar.is65Stale()) {
          center = mLidar.get65();
        } else {
          center = mLidar.get55();
        }
      }

      center = (center - mTarget) / 10000;
      mCenter = DriveMath.Cap(center, mCorrection);

      System.out.print("execute ");
      System.out.print(mTarget);
      System.out.print(" ");
      System.out.print(mLidar.get60());
      System.out.print(" ");
      System.out.print(mLidar.get0());

      if (mLidar.get0() < (mTarget*1.5)) {
        mFrontCount = 2;
      }

      if (mFrontCount > 0) {
        mFrontCount--;
        System.out.print(" front ");
        mDrive.tankDrive(-mSlowSpeed, mSlowSpeed);
      } else {
        mDrive.tankDrive(mSpeed + mCenter, mSpeed - mCenter);
      }

      System.out.println(mCenter);
    }
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
  }
}