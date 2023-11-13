package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Swerve.DrivebaseSubsystem;

public class DO_NOTHING extends SequentialCommandGroup {

  public DO_NOTHING(DrivebaseSubsystem driveBase) {
    addCommands(
        new InstantCommand()

    );
    // grabs any requirements needed for the drivebase from other running commands.
    addRequirements(driveBase);
  }
}
