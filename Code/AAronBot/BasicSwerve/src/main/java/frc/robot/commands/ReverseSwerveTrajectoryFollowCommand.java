// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.trajectory.TrajectoryConfig;
import frc.robot.subsystems.Swerve.DrivebaseSubsystem;

/**
 * Custom implementation of
 * {@link edu.wpi.first.wpilibj2.command.SwerveControllerCommand
 * SwerveControllerCommand}
 * to simplify construction, allow PathPlanner paths, and enable custom logging
 */
public class ReverseSwerveTrajectoryFollowCommand extends SwerveTrajectoryFollowCommand {

    public ReverseSwerveTrajectoryFollowCommand(DrivebaseSubsystem drivetrain, String pathFilename,
            TrajectoryConfig config,
            boolean isInitial) {
        super(drivetrain, pathFilename, config, true, isInitial);
    }

}