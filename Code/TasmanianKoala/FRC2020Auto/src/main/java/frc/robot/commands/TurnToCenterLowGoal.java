/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;

import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.RaspPiCamera;

/**
 * A command that will turn the robot to the specified angle using a motion profile.
 */
public class TurnToCenterLowGoal extends ProfiledPIDCommand {
  RaspPiCamera mCamera = RaspPiCamera.getInstance();
  double mAngle = 0;
  int mDebugCount = 0;

  public TurnToCenterLowGoal(double targetAngleDegrees, DriveSubsystem drive, RaspPiCamera camera) {
    super(
        new ProfiledPIDController(DriveConstants.kTurnP, DriveConstants.kTurnI,
                                  DriveConstants.kTurnD, new TrapezoidProfile.Constraints(
            DriveConstants.kMaxTurnRateDegPerS,
            DriveConstants.kMaxTurnAccelerationDegPerSSquared)),
        // Close loop on heading
        camera::getX,
        // Set reference to target
        targetAngleDegrees,
        // Pipe output to turn robot
        (output, setpoint) -> drive.arcadeDrive(0, output),
        // Require the drive
        drive);

    // Set the controller to be continuous (because it is an angle controller)
    getController().enableContinuousInput(-180, 180);
    // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
    // setpoint before it is considered as having reached the reference
    getController()
        .setTolerance(DriveConstants.kTurnToleranceDeg, DriveConstants.kTurnRateToleranceDegPerS);
    mAngle = targetAngleDegrees;
  }

  @Override
  public boolean isFinished() {
    double posError = Math.abs(mCamera.getX() - mAngle);
    
    mDebugCount++;

    if(0 == (mDebugCount % 30))
    {
      System.out.printf("ciF:%f %s\n",posError,DriveSubsystem.getInstance().getPose().toString());
    }
    
    return posError < 5;
  }
}
