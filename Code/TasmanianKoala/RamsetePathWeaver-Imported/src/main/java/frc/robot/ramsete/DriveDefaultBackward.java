package frc.robot.ramsete;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.math.trajectory.Trajectory;
import frc.robot.trajectory.DefaultTrajectoryBackward;

public class DriveDefaultBackward 
{
    DriveRamsete mDriveRamsete = new DriveRamsete();
//    DriveSubsystem mDriveSubsystem = DriveSubsystem.getInstance();

    public DriveDefaultBackward() {
      Trajectory trajectory = DefaultTrajectoryBackward.getTrajectory();
//      mDriveSubsystem.resetOdometry(trajectory.getInitialPose());

      mDriveRamsete = new DriveRamsete(trajectory);
    }  
  
    public Command getCommand()
    {
      return mDriveRamsete.getRamsete(); 
    }      
}
