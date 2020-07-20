//----------------------------------------------------------------------------
//
//  $Workfile: Robot.java$
//
//  $Revision: X$
//
//  Project:    Tornado Platypus
//
//                            Copyright (c) 2020
//                              James A Wright
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
package frc.robot;

//----------------------------------------------------------------------------
//  Imports
//----------------------------------------------------------------------------
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.command.DriveRightToPosition;
import frc.robot.command.WaitForTime;
import static frc.robot.Constants.OIConstants.kBackButton;
import static frc.robot.Constants.OIConstants.kDriverControllerPort;

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: Robot
//
// Purpose:
//   The entry point
//
//----------------------------------------------------------------------------
public class Robot extends TimedRobot {
  // ----------------------------------------------------------------------------
  // Class Attributes
  // ----------------------------------------------------------------------------
  Joystick mDriveStick = new Joystick(kDriverControllerPort);
  boolean mDisplay = false;
  boolean mOldBackButton = false;
  DriveSubsystem mDriveSystem = DriveSubsystem.getInstance();
  WaitForTime mWaitForTime;
  DriveRightToPosition mDriveRightToPosition;

  // ----------------------------------------------------------------------------
  // Purpose:
  // Gets everything ready to run the robot
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  @Override
  public void robotInit() {
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Call this every frame
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Gets the auto ready to run
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  @Override
  public void autonomousInit() {
    mWaitForTime = new WaitForTime(20000);

    if (mWaitForTime != null) {
      mWaitForTime.schedule();
    }
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Auto loop
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  @Override
  public void autonomousPeriodic() {
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Stops any auto and gets the teleop really to run
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  @Override
  public void teleopInit() {
    mDriveRightToPosition = new DriveRightToPosition(50000,mDriveSystem);

    if (mDriveRightToPosition != null) {
      mDriveRightToPosition.schedule();
    }
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Tele loop
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  @Override
  public void teleopPeriodic() {
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Test loop
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  @Override
  public void testPeriodic() {
    mDriveSystem.tankDrive(.4, .4);
    System.out.format("LP:%7.2f RP:%7.2f LV:%7.2f RV:%7.2f H:%7.2f\n", 
    mDriveSystem.getLeftEncoderPosition(),
    mDriveSystem.getRightEncoderPosition(),
    mDriveSystem.getLeftEncoderSpeed(),
    mDriveSystem.getRightEncoderSpeed(),
    mDriveSystem.getHeading());
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Get ready for disabled
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  @Override
  public void disabledInit() {
    mDriveSystem.tankDrive(0, 0);
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Disabled loop
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  @Override
  public void disabledPeriodic() {
    if((true == mDriveStick.getRawButton(kBackButton)) &&
    (false == mOldBackButton)) {
        mDisplay = !mDisplay;        
    }

    if(true == mDisplay)
    {
        System.out.format("LE:%7.2f RE:%7.2f H:%7.2f\n", 
            mDriveSystem.getLeftEncoderPosition(),
            mDriveSystem.getRightEncoderPosition(),
            mDriveSystem.getHeading());
    }

    mOldBackButton = mDriveStick.getRawButton(kBackButton);
  }
}
