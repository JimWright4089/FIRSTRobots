//----------------------------------------------------------------------------
//
//  $Workfile: DriveRightToPosition.java$
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
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.DriveSubsystem;

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: DriveRightToPosition
//
// Purpose:
//   Drive a wheel to a position
//
//----------------------------------------------------------------------------
public class DriveRightToPosition extends PIDCommand {

  // ----------------------------------------------------------------------------
  // Purpose:
  // Setup the PID command
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  public DriveRightToPosition(double targetPosition, DriveSubsystem drive) {
    super(new PIDController(DriveConstants.kMoveP, DriveConstants.kMoveI, DriveConstants.kMoveD),
        // Close loop on heading
        DriveSubsystem.getInstance()::getLeftEncoderPosition,
        // Set reference to target
        targetPosition,
        // Pipe output to turn robot
        output -> drive.tankDrive(output, 0),
        // Require the drive
        DriveSubsystem.getInstance());

    // Set the controller to be continuous (because it is an angle controller)
    // getController().enableContinuousInput(-199, 199);
    // Set the controller tolerance - the delta tolerance ensures the robot is
    // stationary at the
    // setpoint before it is considered as having reached the reference
    getController()
        // .setTolerance(DriveConstants.kMoveTolerancePos,
        // DriveConstants.kMoveRateTolerancePosPerS);
        .setTolerance(DriveConstants.kMoveTolerancePos, 0);

    System.out.println("DriveRightToPosition(double targetPosition, DriveSubsystem drive)");

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
  public boolean isFinished() {
    System.out.print("isFinished(");
    System.out.print(m_controller.getPositionError());
    System.out.print(" ");
    System.out.print(m_controller.getVelocityError());
    System.out.println(")");

    // End when the controller is at the reference.
    return getController().atSetpoint();
  }
}
