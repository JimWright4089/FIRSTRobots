//----------------------------------------------------------------------------
//
//  $Workfile: DrivePath.java$
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

import frc.robot.trajectory.DefaultTrajectory;

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: DrivePath
//
// Purpose:
//   Drive a path from PathWeaver
//
//----------------------------------------------------------------------------
public class DrivePath 
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
    public DrivePath() {
        Trajectory trajectory = DefaultTrajectory.getTrajectory();

        mDriveRamsete = new DriveRamsete(trajectory);
      }    

    // ----------------------------------------------------------------------------
    // Purpose:
    // Load in a path
    //
    // Notes:
    // None
    //
    // ----------------------------------------------------------------------------
    public DrivePath(String name) {
        Trajectory trajectory = DefaultTrajectory.getTrajectory();

        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(name);
            trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + name, ex.getStackTrace());
        }

        System.out.printf("%s\n",mDriveSubsystem.getPose().toString());
        System.out.printf("%s\n",trajectory.getInitialPose().toString());
        mDriveSubsystem.resetOdometry(trajectory.getInitialPose());
        System.out.printf("%s\n",mDriveSubsystem.getPose().toString());
        System.out.printf("%s\n",trajectory.getInitialPose().toString());

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
