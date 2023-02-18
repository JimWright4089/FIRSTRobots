package frc.robot.ramsete;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.math.trajectory.Trajectory;
import frc.robot.trajectory.ExampleTrajectory;
import frc.robot.subsystems.DriveSubsystem;

public class DriveExample 
{
    DriveRamsete mDriveRamsete = new DriveRamsete();
    DriveSubsystem mDriveSubsystem = DriveSubsystem.getInstance();

    public DriveExample() {
      Trajectory trajectory = ExampleTrajectory.getTrajectory();
      mDriveRamsete = new DriveRamsete(trajectory);
    }  
  
    public Command getCommand()
    {
      return mDriveRamsete.getRamsete(); 
    }      
}
