//----------------------------------------------------------------------------
//
//  $Workfile: TurnToCenterLowGoal.java$
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
import frc.robot.subsystems.RaspPiCamera;

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: TurnToCenterLowGoal
//
// Purpose:
//  Point the robot to the goal
//
//----------------------------------------------------------------------------
public class TurnToCenterLowGoal extends ProfiledPIDCommand {
  //----------------------------------------------------------------------------
  //  Class Atributes
  //----------------------------------------------------------------------------
  RaspPiCamera mCamera = RaspPiCamera.getInstance();
  double mAngle = 0;
  int mDebugCount = 0;

  //----------------------------------------------------------------------------
  //  Purpose:
  //   Constructor
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public TurnToCenterLowGoal(double targetAngleDegrees, DriveSubsystem drive, RaspPiCamera camera) {
    super(
        new ProfiledPIDController(DriveConstants.kTurnPFind, DriveConstants.kTurnIFind,
                                  DriveConstants.kTurnDFind, new TrapezoidProfile.Constraints(
            DriveConstants.kMaxTurnRateDegPerSFind,
            DriveConstants.kMaxTurnAccelerationDegPerSSquaredFind)),
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
    double posError = Math.abs(mCamera.getX() - mAngle);
    
    mDebugCount++;

    if(0 == (mDebugCount % 30))
    {
      System.out.printf("ciF:%f %s\n",posError,DriveSubsystem.getInstance().getPose().toString());
    }
    
    return posError < 5;
  }
}
