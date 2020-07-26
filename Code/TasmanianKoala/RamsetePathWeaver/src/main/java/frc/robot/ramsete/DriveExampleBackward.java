package frc.robot.ramsete;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.trajectory.ExampleTrajectoryBackward;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.trajectory.Trajectory;

public class DriveExampleBackward 
{
    DriveRamsete mDriveRamsete = new DriveRamsete();
    DriveSubsystem mDriveSubsystem = DriveSubsystem.getInstance();

    public DriveExampleBackward() {
      Trajectory trajectory = ExampleTrajectoryBackward.getTrajectory();
      mDriveRamsete = new DriveRamsete(trajectory);
    }  
  
    public Command getCommand()
    {
      return mDriveRamsete.getRamsete(); 
    }      
}
