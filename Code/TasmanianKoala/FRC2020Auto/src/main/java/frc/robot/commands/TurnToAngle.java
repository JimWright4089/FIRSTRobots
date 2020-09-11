//----------------------------------------------------------------------------
//
//  $Workfile: TurnToAngle.java$
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
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;

import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.DriveSubsystem;

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: TurnToAngle
//
// Purpose:
//  Turn robot to an angle
//
//----------------------------------------------------------------------------
public class TurnToAngle extends ProfiledPIDCommand {
  //----------------------------------------------------------------------------
  //  Class Atributes
  //----------------------------------------------------------------------------
  DriveSubsystem mDrive = DriveSubsystem.getInstance();
  double mAngle = 0;

  //----------------------------------------------------------------------------
  //  Purpose:
  //   Constructor
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public TurnToAngle(double targetAngleDegrees, DriveSubsystem drive) {
    super(
        new ProfiledPIDController(DriveConstants.kTurnP, DriveConstants.kTurnI,
                                  DriveConstants.kTurnD, new TrapezoidProfile.Constraints(
            DriveConstants.kMaxTurnRateDegPerS,
            DriveConstants.kMaxTurnAccelerationDegPerSSquared)),
        // Close loop on heading
        drive::getPoseThetaDegrees,
        // Set reference to target
        targetAngleDegrees,
        // Pipe output to turn robot
        (output, setpoint) -> drive.arcadeDrive(0, -output),
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
    double posError = Math.abs(mDrive.getPoseThetaDegrees() - mAngle);

    System.out.printf("iF:%f %s\n",posError,DriveSubsystem.getInstance().getPose().toString());

    return posError < DriveConstants.kTurnToleranceDeg;

//    return getController().atGoal();
  }
}