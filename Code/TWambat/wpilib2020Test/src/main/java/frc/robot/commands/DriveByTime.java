package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveTrain;

public class DriveByTime extends SequentialCommandGroup {

  /**
   * Drives the robot straight for a fixed time and then stops.
   * @param timeSec how long to drive, in seconds
   * @param driveTrain drive subsystem to use
   */
  public DriveByTime(double timeSec, DriveTrain driveTrain) {
    addCommands(
      deadline(new WaitCommand(timeSec),
        new Drive(0.6, 0.6, driveTrain)
      ),
      new Stop(driveTrain)
    );
  }
}
