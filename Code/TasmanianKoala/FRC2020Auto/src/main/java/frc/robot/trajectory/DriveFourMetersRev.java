//----------------------------------------------------------------------------
//
//  $Workfile: DriveFourMetersRev.java$
//
//  $Revision: X$
//
//  Project:    Tasmanian Koala
//
//                            Copyright (c) 2020
//                                 Jim Wright
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
package frc.robot.trajectory;

import java.util.List;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;

import static frc.robot.Constants.AutoConstants.kMaxSpeedMetersPerSecond;
import static frc.robot.Constants.AutoConstants.kMaxAccelerationMetersPerSecondSquared;
import static frc.robot.Constants.DriveConstants.kDriveKinematics;
import static frc.robot.Constants.DriveConstants.ksVolts;
import static frc.robot.Constants.DriveConstants.kvVoltSecondsPerMeter;
import static frc.robot.Constants.DriveConstants.kaVoltSecondsSquaredPerMeter;

public class DriveFourMetersRev {

    static DifferentialDriveVoltageConstraint mAutoVoltageConstraint =
    new DifferentialDriveVoltageConstraint(
        new SimpleMotorFeedforward(ksVolts,
                                   kvVoltSecondsPerMeter,
                                   kaVoltSecondsSquaredPerMeter),
        kDriveKinematics,
        10);

    public static Trajectory getTrajectory()
    {
      TrajectoryConfig trajectoryConf = new TrajectoryConfig(kMaxSpeedMetersPerSecond,
        kMaxAccelerationMetersPerSecondSquared);
        // Add kinematics to ensure max speed is actually obeyed
        trajectoryConf.setKinematics(kDriveKinematics);
        trajectoryConf.setReversed(true);
        // Apply the voltage constraint
        trajectoryConf.addConstraint(mAutoVoltageConstraint);

        return(TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(4, 0, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path

            List.of(
                new Translation2d(2, 0)
            ),
            
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(0, 0, new Rotation2d(0)),
            // Pass config
            trajectoryConf
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(kDriveKinematics)
            // Apply the voltage constraint
            .addConstraint(mAutoVoltageConstraint)
        ));
    }
}
