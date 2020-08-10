package frc.robot.trajectory;

import java.util.List;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Transform2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import frc.robot.subsystems.DriveSubsystem;

import static frc.robot.Constants.AutoConstants.kMaxSpeedMetersPerSecond;
import static frc.robot.Constants.AutoConstants.kMaxAccelerationMetersPerSecondSquared;
import static frc.robot.Constants.DriveConstants.kDriveKinematics;
import static frc.robot.Constants.DriveConstants.ksVolts;
import static frc.robot.Constants.DriveConstants.kvVoltSecondsPerMeter;
import static frc.robot.Constants.DriveConstants.kaVoltSecondsSquaredPerMeter;

public class DefaultTrajectory {
    static DriveSubsystem mDriveSubsystem = DriveSubsystem.getInstance();
    static DifferentialDriveVoltageConstraint mAutoVoltageConstraint =
    new DifferentialDriveVoltageConstraint(
        new SimpleMotorFeedforward(ksVolts,
                                   kvVoltSecondsPerMeter,
                                   kaVoltSecondsSquaredPerMeter),
        kDriveKinematics,
        10);

    public static Trajectory getTrajectory()
    {
      Pose2d startPose = mDriveSubsystem.getPose();
      Pose2d endPose = startPose.transformBy(new  Transform2d(new Translation2d(1, 0), new Rotation2d()));
      System.out.printf("StartT:%s\n",startPose.toString());
      System.out.printf("EndT  :%s\n",endPose.toString());
      return(TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            startPose,
            // Pass through these two interior waypoints, making an 's' curve path

            List.of(),
            
            // End 3 meters straight ahead of where we started, facing forward
            endPose,
            // Pass config
            new TrajectoryConfig(kMaxSpeedMetersPerSecond,
                                 kMaxAccelerationMetersPerSecondSquared)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(kDriveKinematics)
            // Apply the voltage constraint
            .addConstraint(mAutoVoltageConstraint)
        ));
    }
}
