//----------------------------------------------------------------------------
//
//  $Workfile: DriveDefault.java$
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

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: DriveDefault
//
// Purpose:
//   Run the default trajectory
//
//----------------------------------------------------------------------------
public class DriveDefault
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
    public DriveDefault() {
      mDriveRamsete = new DriveRamsete();
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
