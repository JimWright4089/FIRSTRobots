//----------------------------------------------------------------------------
//
//  $Workfile: BlueSixBallAuto.java$
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
import frc.robot.subsystems.RaspPiCamera;

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: BlueSixBallAuto
//
// Purpose:
//  Run a path from the auto line to get three balls
//
//----------------------------------------------------------------------------
public class BlueSixBallAuto extends SequentialCommandGroup
{
  //----------------------------------------------------------------------------
  //  Purpose:
  //   Constructor
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public BlueSixBallAuto() {
    DriveTrajectory drivePath1 = new DriveTrajectory(BlueSixBallAutoCollectThree.getTrajectory());  
    DriveTrajectory drivePath2 = new DriveTrajectory(BlueSixBallAutoBackToLine.getTrajectory());      
    addCommands(new TurnToCenterLowGoal(0, DriveSubsystem.getInstance(), RaspPiCamera.getInstance()));
    addCommands(new WaitTime(1000));
    addCommands(new TurnToAngle(0,DriveSubsystem.getInstance()));
    addCommands(drivePath1.getCommand());
    addCommands(new WaitTime(1000));
    addCommands(drivePath2.getCommand());
    addCommands(new TurnToAngle(180,DriveSubsystem.getInstance()));
    addCommands(new TurnToCenterLowGoal(0, DriveSubsystem.getInstance(), RaspPiCamera.getInstance()));
    addCommands(new WaitTime(1000));
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