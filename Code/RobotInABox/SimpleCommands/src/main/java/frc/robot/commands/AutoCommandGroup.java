package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;
import frc.robot.commands.*;

public class AutoCommandGroup extends SequentialCommandGroup {
  
    /**
   * Creates a new ComplexAuto.
   *
   * @param drive The drive subsystem this command will run on
   */
  public AutoCommandGroup(DriveSubsystem drive) {
    addCommands(
        // Drive forward the specified distance
        new RunForward(10000, drive),
        new RunReverse(5000, drive));
  }

}