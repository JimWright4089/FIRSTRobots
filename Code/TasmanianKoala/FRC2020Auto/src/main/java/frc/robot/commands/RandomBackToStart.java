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

//----------------------------------------------------------------------------
//  Imports
//----------------------------------------------------------------------------
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.ramsete.*;
import frc.robot.trajectory.*;
import frc.robot.subsystems.DriveSubsystem;

//----------------------------------------------------------------------------
//Class Declarations
//----------------------------------------------------------------------------
//
//  Class Name: RandomBackToStart
//
//  Purpose:
//    Run a path with a break in the path, show the robot can adapt
//
//----------------------------------------------------------------------------
public class RandomBackToStart extends SequentialCommandGroup
{

  //----------------------------------------------------------------------------
  //  Purpose:
  //   Constructor
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public RandomBackToStart() {
    System.out.println("public RandomBackToStart()");

    DriveTrajectory drivePath1 = new DriveTrajectory(DriveFourMeters.getTrajectory());  
    DriveTrajectory drivePath2 = new DriveTrajectory(DriveOdd.getTrajectory());      
    DriveTrajectory drivePath3 = new DriveTrajectory(DriveFourMetersRev.getTrajectory());      
    addCommands(drivePath1.getCommand());
    addCommands(new WaitTime(30));
    addCommands(drivePath2.getCommand());
    addCommands(new WaitTime(30));
    addCommands(drivePath3.getCommand());
  } 
  
  //----------------------------------------------------------------------------
  //  Purpose:
  //   Run the setup before the loop
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  @Override
  public void initialize()
  {
    super.initialize();
    System.out.printf("S:%s\n",DriveSubsystem.getInstance().getPose().toString());
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //   When the command ends this is what is called
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    System.out.printf("E:%s\n",DriveSubsystem.getInstance().getPose().toString());
  }
}