//----------------------------------------------------------------------------
//
//  $Workfile: InTakeBall.java$
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

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.RaspPiCamera;
import frc.robot.utils.*;

/**
 * A command that will turn the robot to the specified angle using a motion profile.
 */
public class InTakeBall extends CommandBase {
  final double kPOffset = 0.0015;
  final double kIOffset = 0.0001;
  final double kPSize = 0.00015;
  final double kISize = 0.000015;

  DriveSubsystem mDrive = DriveSubsystem.getInstance();
  RaspPiCamera mCamera = RaspPiCamera.getInstance();
  double mAngle = 0;
  double mSize = 800;
  double mErrorOffset = 0;
  double mSumErrorOffset = 0;
  double mErrorSize = 0;
  double mSumErrorSize = 0;
  
  public InTakeBall(double targetAngleDegrees) {
    mAngle = targetAngleDegrees;
  }

  @Override 
  public void execute() {
    double turnPower = 0;
    double forwardPower = 0;

    mErrorOffset =  mCamera.getX() - mAngle;
    mErrorSize = mCamera.getArea() - mSize;
  
    turnPower = (mErrorOffset * kPOffset)+(mSumErrorOffset * kIOffset);
    forwardPower = (mErrorSize * kPSize)+(mSumErrorSize * kISize);

    turnPower = DriveMath.Cap(turnPower, 1.0);
    forwardPower = DriveMath.Cap(forwardPower, 1.0);

    System.out.printf("turn:%f forward:%f IO:%f IS:%f\n", turnPower,forwardPower,mSumErrorOffset,mSumErrorSize);

    mDrive.arcadeDrive(-forwardPower, -turnPower);
    mSumErrorOffset += mErrorOffset;
    mSumErrorOffset = DriveMath.Cap(mSumErrorOffset,2000);
    mSumErrorSize += mErrorSize;
    mSumErrorSize = DriveMath.Cap(mSumErrorSize,10000);
  }

  @Override
  public boolean isFinished() {
    if(-1 == mCamera.getArea())
    {
      return true;
    }

    return ((Math.abs(mErrorOffset) < 5) && (mCamera.getArea() > mSize));
  }

  @Override
  public void end(boolean interupted) {
    mDrive.arcadeDrive(0, 0);
  }
}