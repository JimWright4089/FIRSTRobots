//----------------------------------------------------------------------------
//
//  $Workfile: DrivePathBackward.java$
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
import java.nio.file.Path;
import java.io.IOException;
import edu.wpi.first.wpilibj.Filesystem;

import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import frc.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.trajectory.DefaultTrajectoryBackward;

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: DrivePathBackward
//
// Purpose:
//   Drive a path from PathWeaver backwards, eventually
//
//----------------------------------------------------------------------------
public class DrivePathBackward
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
    public DrivePathBackward() {
        Trajectory trajectory = DefaultTrajectoryBackward.getTrajectory();

        mDriveRamsete = new DriveRamsete(trajectory);
      }    

    // ----------------------------------------------------------------------------
    // Purpose:
    // Load in the path and run it backwards, eventualt
    //
    // Notes:
    // None
    //
    // ----------------------------------------------------------------------------
    public DrivePathBackward(String name) {
        Trajectory trajectory = DefaultTrajectoryBackward.getTrajectory();

        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(name);
            trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + name, ex.getStackTrace());
        }

        //mDriveSubsystem.resetOdometry(trajectory.getInitialPose());

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