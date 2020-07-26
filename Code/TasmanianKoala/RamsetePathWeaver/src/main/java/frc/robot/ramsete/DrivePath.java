package frc.robot.ramsete;

import java.nio.file.Path;
import java.io.IOException;
import edu.wpi.first.wpilibj.Filesystem;

import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import frc.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.DriverStation;

import frc.robot.trajectory.DefaultTrajectory;

public class DrivePath 
{
    DriveRamsete mDriveRamsete = new DriveRamsete();
    DriveSubsystem mDriveSubsystem = DriveSubsystem.getInstance();

    public DrivePath() {
        Trajectory trajectory = DefaultTrajectory.getTrajectory();

        mDriveRamsete = new DriveRamsete(trajectory);
      }    

    public DrivePath(String name) {
        Trajectory trajectory = DefaultTrajectory.getTrajectory();

        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(name);
            trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + name, ex.getStackTrace());
        }

        System.out.printf("%s\n",mDriveSubsystem.getPose().toString());
        System.out.printf("%s\n",trajectory.getInitialPose().toString());
        mDriveSubsystem.resetOdometry(trajectory.getInitialPose());
        System.out.printf("%s\n",mDriveSubsystem.getPose().toString());
        System.out.printf("%s\n",trajectory.getInitialPose().toString());

        mDriveRamsete = new DriveRamsete(trajectory);
    }  
    
    public Command getCommand()
    {
      return mDriveRamsete.getRamsete(); 
    }      
    
}
