//----------------------------------------------------------------------------
//
//  $Workfile: RandomBackToStart.java$
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
import frc.robot.trajectory.*;
import frc.robot.utils.StopWatch;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.RaspPiCamera;

public class RandomBackToStart extends SequentialCommandGroup
{
  public RandomBackToStart() {
    DriveTrajectory drivePath1 = new DriveTrajectory(DriveFourMeters.getTrajectory());  
    DriveTrajectory drivePath2 = new DriveTrajectory(DriveOdd.getTrajectory());      
    DriveTrajectory drivePath3 = new DriveTrajectory(DriveFourMetersRev.getTrajectory());      
    addCommands(new WaitTime(1000));
    addCommands(drivePath1.getCommand());
    addCommands(new WaitTime(3000));
  //  addCommands(drivePath2.getCommand());
    addCommands(new WaitTime(3000));
    addCommands(drivePath3.getCommand());
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