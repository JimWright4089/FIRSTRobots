/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.utils.DriveParam;
import frc.robot.utils.DriveMath;

import static frc.robot.Constants.DriveConstants.kSpeedGain;
import static frc.robot.Constants.DriveConstants.kSlowSpeed;
import static frc.robot.Constants.DriveConstants.kSlowTurnSpeed;
import static frc.robot.Constants.DriveConstants.kNormalSpeed;
import static frc.robot.Constants.DriveConstants.kNormalTurnSpeed;
import static frc.robot.Constants.DriveConstants.kDeadBand;
import static frc.robot.Constants.DriveConstants.kDeadBandRot;
import static frc.robot.Constants.DriveConstants.kPgain;
import static frc.robot.Constants.DriveConstants.kDgain;
import static frc.robot.Constants.DriveConstants.kMaxCorrectionRatio;

/**
 * A command to drive the robot with joystick input (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes - actual code should inline a command this simple with {@link
 * edu.wpi.first.wpilibj2.command.RunCommand}.
 */
public class DefaultDrive extends CommandBase {
  private final DriveSubsystem mDrive;
  private final DoubleSupplier mForward;
  private final DoubleSupplier mRotation;
  private final BooleanSupplier mFastButton;
  private final BooleanSupplier mSlowButton;
  private DriveParam mCurDriveParam;
  private double mTargetAngle = 0.0;

  /**
   * Creates a new DefaultDrive.
   *
   * @param subsystem The drive subsystem this command wil run on.
   * @param forward The control input for driving forwards/backwards
   * @param rotation The control input for turning
   */
  public DefaultDrive(DriveSubsystem subsystem, DoubleSupplier forward, DoubleSupplier rotation,
                       BooleanSupplier fastButton, BooleanSupplier slowButton) {
    mDrive = subsystem;
    mForward = forward;
    mRotation = rotation;
    mFastButton = fastButton;
    mSlowButton = slowButton;
    mCurDriveParam = new DriveParam();
    addRequirements(mDrive);
  }

  @Override
  public void execute() {
    DriveParam driveParam = new DriveParam(mForward.getAsDouble()*-1, mRotation.getAsDouble());
    driveParam = applyDeadBand(driveParam);
    driveParam = applyButtons(driveParam);
   // driveParam = applyGyro(driveParam);
    applyRamps(driveParam);
    mDrive.arcadeDrive(mCurDriveParam);
  }

  private DriveParam applyDeadBand(DriveParam driveParam)
  {
    driveParam.setForward(DriveMath.DeadBand(driveParam.getForward(), kDeadBand));   
    driveParam.setRotation(DriveMath.DeadBand(driveParam.getRotation(), kDeadBandRot));   
    return driveParam;
  }

  private DriveParam applyButtons(DriveParam driveParam)
  {
    // Adjust for speed, check if the fast button is pushed
    if (true == mFastButton.getAsBoolean())
    {
    // Do Nothing
    }
    else
    {
      // Is the slow button pushed
      if (true == mSlowButton.getAsBoolean())
      {
        driveParam.multiply(kSlowSpeed, kSlowTurnSpeed);
      }
      else
      {
        // normal speed
        driveParam.multiply(kNormalSpeed, kNormalTurnSpeed);
      }   
    }   
    return driveParam;
  }

  private DriveParam applyGyro(DriveParam driveParam)
  {
    double turnThrottle = driveParam.getRotation();
 
    // IF we are turning, turn off the gyro
    if (Math.abs(driveParam.getRotation()) > 0.5)
    {
      driveParam.multiply(0.6, 0.6);
      mTargetAngle = mDrive.getHeading();
    }
    else
    {
      if (Math.abs(driveParam.getForward()) > 0.1)
      {
        double angleError = (mTargetAngle - mDrive.getHeading());
        turnThrottle = angleError * kPgain - (mDrive.getTurnRate()) * kDgain;
        double maxThrot = DriveMath.MaxCorrection(driveParam.getForward(), kMaxCorrectionRatio);
        turnThrottle = -1 * DriveMath.Cap(turnThrottle, maxThrot);
        driveParam.setRotation(turnThrottle);
      }
      else
      {
        driveParam.clear();
        mTargetAngle = mDrive.getHeading();
      }
    }

    return driveParam;
  }

  private void applyRamps(DriveParam driveParam)
  {
    mCurDriveParam.setRotation(driveParam.getRotation());
    // Ramp the speed
    if (mCurDriveParam.getForward() != driveParam.getForward())
    {
      if (mCurDriveParam.getForward() < driveParam.getForward())
      {
        mCurDriveParam.setForward(Math.max(mCurDriveParam.getForward() + kSpeedGain, driveParam.getForward()));
      }
      else
      {
        mCurDriveParam.setForward(Math.max(mCurDriveParam.getForward() - kSpeedGain, driveParam.getForward()));
      }
    }    
  }
}