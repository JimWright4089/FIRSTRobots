//----------------------------------------------------------------------------
//
//  $Workfile: AutoDriveDefault.java$
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
import frc.robot.subsystems.DriveSubsystem;

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: AutoDriveDefault
//
// Purpose:
//   Drive the default auto path
//
//----------------------------------------------------------------------------
public class AutoDriveDefault extends SequentialCommandGroup
{
  DriveDefault mDriveDefault = new DriveDefault();  

  public AutoDriveDefault() {
    addCommands(mDriveDefault.getCommand());
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