//----------------------------------------------------------------------------
//
//  $Workfile: DefaultDrive.java$
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

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: DefaultDrive
//
// Purpose:
//   Drives the robot with a joystick, this builds up the input stack
//
//----------------------------------------------------------------------------
public class DefaultDrive extends CommandBase 
{
  //----------------------------------------------------------------------------
  //  Class Attributes
  //----------------------------------------------------------------------------
  private final DriveSubsystem mDrive;
  private final DoubleSupplier mForward;
  private final DoubleSupplier mRotation;
  private final BooleanSupplier mFastButton;
  private final BooleanSupplier mSlowButton;
  private DriveParam mCurDriveParam;
  private double mTargetAngle = 0.0;

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Contstructor
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
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

  //----------------------------------------------------------------------------
  //  Purpose:
  //      This is called every frame of the scheduler
  //
  //  Notes:
  //      This builds up the drive params, you can pull things in or out
  //
  //----------------------------------------------------------------------------
  @Override
  public void execute() 
  {
    DriveParam driveParam = new DriveParam(mForward.getAsDouble()*-1, mRotation.getAsDouble());
    driveParam = applyDeadBand(driveParam);
    driveParam = applyButtons(driveParam);
   // driveParam = applyGyro(driveParam);
    applyRamps(driveParam);
    mDrive.arcadeDrive(mCurDriveParam);
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      This is applies dead bands
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  private DriveParam applyDeadBand(DriveParam driveParam)
  {
    driveParam.setForward(DriveMath.DeadBand(driveParam.getForward(), kDeadBand));   
    driveParam.setRotation(DriveMath.DeadBand(driveParam.getRotation(), kDeadBandRot));   
    return driveParam;
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      This is applies buttons
  //
  //  Notes:
  //      The buttons control the speed of the robot
  //
  //----------------------------------------------------------------------------
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

  //----------------------------------------------------------------------------
  //  Purpose:
  //      This is applies gyro to the driver input to help go straight
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
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

  //----------------------------------------------------------------------------
  //  Purpose:
  //      This is applies speed ramps so that if the user moves the joystick
  //      from all the forward to all the way backward, it does not jam the motors
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
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