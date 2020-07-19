package frc.robot.commands;

import java.nio.file.Path;
import java.io.IOException;
import edu.wpi.first.wpilibj.Filesystem;

import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import frc.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.DriverStation;

import frc.robot.commands.DriveRamseteForward;
import frc.robot.trajectory.DefaultTrajectory;

public class DrivePath extends SequentialCommandGroup
{
    DriveRamseteForward mDriveRamseteForward = new DriveRamseteForward();
    DriveSubsystem mDriveSubsystem = DriveSubsystem.getInstance();

    public DrivePath() {
        Trajectory trajectory = DefaultTrajectory.getTrajectory();

        mDriveRamseteForward = new DriveRamseteForward(trajectory);
        addCommands(
            mDriveRamseteForward.getRamsete());
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

        mDriveRamseteForward = new DriveRamseteForward(trajectory);
        addCommands(mDriveRamseteForward.getRamsete());
    }    
}
