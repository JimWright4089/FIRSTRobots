//----------------------------------------------------------------------------
//
//  $Workfile: BlueSixBallAutoCollectThree.java$
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

//----------------------------------------------------------------------------
//  Imports
//----------------------------------------------------------------------------
import java.util.List;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;

//----------------------------------------------------------------------------
//  Import static consts
//----------------------------------------------------------------------------
import static frc.robot.Constants.AutoConstants.kMaxSpeedMetersPerSecond;
import static frc.robot.Constants.AutoConstants.kMaxAccelerationMetersPerSecondSquared;
import static frc.robot.Constants.DriveConstants.kDriveKinematics;
import static frc.robot.Constants.DriveConstants.ksVolts;
import static frc.robot.Constants.DriveConstants.kvVoltSecondsPerMeter;
import static frc.robot.Constants.DriveConstants.kaVoltSecondsSquaredPerMeter;

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: BlueSixBallAutoCollectThree
//
// Purpose:
//   Go from the shooting spot to collect three balls from the trench
//
//----------------------------------------------------------------------------
public class BlueSixBallAutoCollectThree {

  // ----------------------------------------------------------------------------
  // Class Static Attributes
  // ----------------------------------------------------------------------------
  static DifferentialDriveVoltageConstraint mAutoVoltageConstraint =
    new DifferentialDriveVoltageConstraint(
        new SimpleMotorFeedforward(ksVolts,
                                   kvVoltSecondsPerMeter,
                                   kaVoltSecondsSquaredPerMeter),
        kDriveKinematics,
        10);

    // ----------------------------------------------------------------------------
    // Purpose:
    // Return the trajectory to the ramesete command
    //
    // Notes:
    // None
    //
    // ----------------------------------------------------------------------------
    public static Trajectory getTrajectory()
    {
        return(TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(3.221, -1.85, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path

            List.of(
                new Translation2d(4.982, -0.740)
            ),
            
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(7.8885, -0.740, new Rotation2d(0)),
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
