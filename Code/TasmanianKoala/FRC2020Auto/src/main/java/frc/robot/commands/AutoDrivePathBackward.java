//----------------------------------------------------------------------------
//
//  $Workfile: AutoDrivePathBackward.java$
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
// Class Name: AutoDrivePathBackward
//
// Purpose:
//  Run a path from a file in reverse
//
//----------------------------------------------------------------------------
public class AutoDrivePathBackward extends SequentialCommandGroup
{
  //----------------------------------------------------------------------------
  //  Purpose:
  //   Constructor
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public AutoDrivePathBackward(String path) {
    DrivePathBackward drivePath = new DrivePathBackward(path);  
    addCommands(drivePath.getCommand());
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