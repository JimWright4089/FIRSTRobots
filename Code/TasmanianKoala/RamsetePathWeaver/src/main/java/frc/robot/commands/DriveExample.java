package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.DriveRamseteForward;
import frc.robot.trajectory.ExampleTrajectory;

public class DriveExample extends SequentialCommandGroup
{
    DriveRamseteForward mDriveRamseteForward = new DriveRamseteForward();

    public DriveExample() {
        mDriveRamseteForward = new DriveRamseteForward(ExampleTrajectory.getTrajectory());
        addCommands(
            mDriveRamseteForward.getRamsete());
      }    
}
