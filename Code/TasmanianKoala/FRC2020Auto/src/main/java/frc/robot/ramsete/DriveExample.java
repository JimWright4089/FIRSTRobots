//----------------------------------------------------------------------------
//
//  $Workfile: DriveExample.java$
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
package frc.robot.ramsete;

//----------------------------------------------------------------------------
//  Imports
//----------------------------------------------------------------------------
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import frc.robot.trajectory.ExampleTrajectory;
import frc.robot.subsystems.DriveSubsystem;

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: DriveExample
//
// Purpose:
//   Run the example trajectory
//
//----------------------------------------------------------------------------
public class DriveExample 
{
    // ----------------------------------------------------------------------------
    // Class Attributes
    // ----------------------------------------------------------------------------
    DriveRamsete mDriveRamsete = new DriveRamsete();
    DriveSubsystem mDriveSubsystem = DriveSubsystem.getInstance();

    // ----------------------------------------------------------------------------
    // Purpose:
    // Constructor
    //
    // Notes:
    // None
    //
    // ----------------------------------------------------------------------------
    public DriveExample() {
      Trajectory trajectory = ExampleTrajectory.getTrajectory();
      mDriveRamsete = new DriveRamsete(trajectory);
    }  
  
    // ----------------------------------------------------------------------------
    // Purpose:
    // Return the command
    //
    // Notes:
    // None
    //
    // ----------------------------------------------------------------------------
    public Command getCommand()
    {
      return mDriveRamsete.getRamsete(); 
    }      
}
