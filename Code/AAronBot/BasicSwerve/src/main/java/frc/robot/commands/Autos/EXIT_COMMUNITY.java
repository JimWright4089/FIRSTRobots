// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autos;

import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.SharedConstants;
import frc.robot.commands.SwerveTrajectoryFollowCommand;
import frc.robot.subsystems.Swerve.DrivebaseSubsystem;

public class EXIT_COMMUNITY extends SequentialCommandGroup {
  private final TrajectoryConfig defaultConfig;

  public EXIT_COMMUNITY(DrivebaseSubsystem driveBase) {
        this.defaultConfig = new TrajectoryConfig(SharedConstants.AutoConstants.k_MAX_SPEED_MPS,
        SharedConstants.AutoConstants.k_MAX_ACCEL_MPS_SQUARED);
    addCommands(
        new SwerveTrajectoryFollowCommand(driveBase, "moveOut", defaultConfig, true)

    );
    // grabs any requirements needed for the drivebase from other running commands.
    addRequirements(driveBase);
  }
}
