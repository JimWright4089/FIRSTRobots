//----------------------------------------------------------------------------
//
//  $Workfile: AutoDriveExampleForwBack.java$
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
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.ramsete.*;
import frc.robot.subsystems.DriveSubsystem;

public class AutoDriveExampleForwBack extends SequentialCommandGroup
{
  public AutoDriveExampleForwBack() {
    DriveExample drivePathForw = new DriveExample();  
//    DrivePathBackward drivePathBack = new DrivePathBackward("Paths/DriveTenFeetBackwards.wpilib.json");  
    DriveExampleBackward drivePathBack = new DriveExampleBackward();  
    addCommands(drivePathForw.getCommand());
    addCommands(drivePathBack.getCommand());
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