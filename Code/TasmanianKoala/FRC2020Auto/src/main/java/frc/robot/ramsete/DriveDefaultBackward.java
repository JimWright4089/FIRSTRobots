//----------------------------------------------------------------------------
//
//  $Workfile: DriveDefaultBackward.java$
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
import frc.robot.trajectory.DefaultTrajectoryBackward;

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: DriveDefaultBackward
//
// Purpose:
//   Run the default trajectory backwards
//
//----------------------------------------------------------------------------
public class DriveDefaultBackward 
{
    // ----------------------------------------------------------------------------
    // Class Attributes
    // ----------------------------------------------------------------------------
    DriveRamsete mDriveRamsete = new DriveRamsete();

    // ----------------------------------------------------------------------------
    // Purpose:
    // Constructor
    //
    // Notes:
    // None
    //
    // ----------------------------------------------------------------------------
    public DriveDefaultBackward() {
      Trajectory trajectory = DefaultTrajectoryBackward.getTrajectory();

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
