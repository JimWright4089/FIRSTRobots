//----------------------------------------------------------------------------
//
//  $Workfile: DriveRamsete.java$
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
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.trajectory.DefaultTrajectory;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.controller.PIDController;

import static frc.robot.Constants.AutoConstants.kRamseteB;
import static frc.robot.Constants.AutoConstants.kRamseteZeta;
import static frc.robot.Constants.DriveConstants.ksVolts;
import static frc.robot.Constants.DriveConstants.kvVoltSecondsPerMeter;
import static frc.robot.Constants.DriveConstants.kaVoltSecondsSquaredPerMeter;
import static frc.robot.Constants.DriveConstants.kDriveKinematics;
import static frc.robot.Constants.DriveConstants.kPDriveVel;

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: DriveRamsete
//
// Purpose:
//   Drive a trajectory either forward or backward
//
//----------------------------------------------------------------------------
public class DriveRamsete {
    // ----------------------------------------------------------------------------
    // Class Attributes
    // ----------------------------------------------------------------------------
    private final DriveSubsystem mRobotDrive = DriveSubsystem.getInstance();
    private Trajectory mTrajectory = DefaultTrajectory.getTrajectory();

    // ----------------------------------------------------------------------------
    // Purpose:
    // Constructor
    //
    // Notes:
    // None
    //
    // ----------------------------------------------------------------------------
    public DriveRamsete() {
    }

    public DriveRamsete(Trajectory trajectory) {
        mTrajectory = trajectory;
    }

    // ----------------------------------------------------------------------------
    // Purpose:
    // Return the command
    //
    // Notes:
    // None
    //
    // ----------------------------------------------------------------------------
    public Command getRamsete()
    {
        RamseteCommand ramseteCommand = new RamseteCommand(
            mTrajectory,
            mRobotDrive::getPose,
            new RamseteController(
                    kRamseteB, 
                    kRamseteZeta),
            new SimpleMotorFeedforward(ksVolts,
                                       kvVoltSecondsPerMeter,
                                       kaVoltSecondsSquaredPerMeter),
            kDriveKinematics,
            mRobotDrive::getWheelSpeeds,
            new PIDController(kPDriveVel, 0, 0),
            new PIDController(kPDriveVel, 0, 0),
            // RamseteCommand passes volts to the callback
            mRobotDrive::tankDriveVolts,
            mRobotDrive
        );
    
        // Run path following command, then stop at the end.
        return ramseteCommand.andThen(() -> mRobotDrive.tankDriveVolts(0, 0));
    }
}