package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

/**
 * A command to drive the robot with joystick input (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes - actual code should inline a command this simple with {@link
 * edu.wpi.first.wpilibj2.command.RunCommand}.
 */
public class RunForward extends CommandBase {
  private int mPosition = 0;
  private DriveSubsystem mDriveSubsystem;

  /**
   * Creates a new DefaultDrive.
   *
   * @param subsystem The drive subsystem this command wil run on.
   * @param forward The control input for driving forwards/backwards
   * @param rotation The control input for turning
   */
  public RunForward(int position, DriveSubsystem driveSubsystem) {
    mPosition = position;
    mDriveSubsystem = driveSubsystem;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
      mDriveSubsystem.tankDrive(0.5, 0.5);
      System.out.println("Run Forward");
    }

  @Override
  public boolean isFinished() {
    if( mDriveSubsystem.getLeftEncoderPosition() > mPosition)
    {
      return true;
    }
    else 
    {
      return false;
    }
      
  }

  @Override
  public void end(boolean interrupted) {
    mDriveSubsystem.tankDrive(0, 0);
  }
}