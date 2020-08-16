//----------------------------------------------------------------------------
//
//  $Workfile: DriveExampleBackward.java$
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
import frc.robot.trajectory.ExampleTrajectoryBackward;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.trajectory.Trajectory;

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: DriveExampleBackward
//
// Purpose:
//   Run the example trajectory backwards
//
//----------------------------------------------------------------------------
public class DriveExampleBackward 
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
    public DriveExampleBackward() {
      Trajectory trajectory = ExampleTrajectoryBackward.getTrajectory();
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
