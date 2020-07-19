
package frc.robot.command;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;

import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.DriveSubsystem;

/**
 * A command that will turn the robot to the specified angle.
 */
public class DriveRightToPosition extends PIDCommand {
  /**
   * Turns to robot to the specified angle.
   *
   * @param targetAngleDegrees The angle to turn to
   * @param drive              The drive subsystem to use
   */
  public DriveRightToPosition(double targetPosition, DriveSubsystem drive) {
    super(
        new PIDController(DriveConstants.kMoveP, DriveConstants.kMoveI, DriveConstants.kMoveD),
        // Close loop on heading
        drive::getLeftEncoderPosition,
        // Set reference to target
        targetPosition,
        // Pipe output to turn robot
        output -> drive.tankDrive(output, 0),
        // Require the drive
        drive);

    // Set the controller to be continuous (because it is an angle controller)
    //getController().enableContinuousInput(-199, 199);
    // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
    // setpoint before it is considered as having reached the reference
    getController()
//    .setTolerance(DriveConstants.kMoveTolerancePos, DriveConstants.kMoveRateTolerancePosPerS);
    .setTolerance(DriveConstants.kMoveTolerancePos, 0);

    System.out.println("DriveRightToPosition(double targetPosition, DriveSubsystem drive)");

    }

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
