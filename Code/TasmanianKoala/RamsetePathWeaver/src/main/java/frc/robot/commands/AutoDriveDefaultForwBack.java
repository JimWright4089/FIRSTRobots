package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.ramsete.*;
import frc.robot.subsystems.DriveSubsystem;

public class AutoDriveDefaultForwBack extends SequentialCommandGroup
{
  DriveDefault mDriveDefault = new DriveDefault();  
  DriveDefaultBackward mDriveDefaultBackward = new DriveDefaultBackward();  

  public AutoDriveDefaultForwBack() {
    addCommands(mDriveDefault.getCommand());
    addCommands(mDriveDefaultBackward.getCommand());
  } 
  
  @Override
  public void initialize()
  {
    super.initialize();
    System.out.printf("S:%s\n",DriveSubsystem.getInstance().getPose().toString());
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    System.out.printf("E:%s\n",DriveSubsystem.getInstance().getPose().toString());
  }

}