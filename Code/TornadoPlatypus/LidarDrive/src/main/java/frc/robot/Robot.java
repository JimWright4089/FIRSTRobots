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
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Lidar;
import frc.robot.commands.FollowLeftWall;;

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
  private DriveSubsystem mDrive = DriveSubsystem.getInstance();
  NetworkTableEntry mLEDRing;
  private Joystick mJoystick = new Joystick(0);
  private Lidar mLidar = Lidar.getInstance();
  private Command mAutoCommand;
  PowerDistributionPanel mPDP = new PowerDistributionPanel(18);

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
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    mLEDRing = inst.getEntry("/LEDColor");
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
    if(false == DriverStation.getInstance().isDisabled())
    {
        mLEDRing.setDouble((double)'G');
    }
    else
    {
      if(Alliance.Blue == DriverStation.getInstance().getAlliance())
      {
        mLEDRing.setDouble((double)'b');
      }
      else
      {
        mLEDRing.setDouble((double)'r');
      }
    }
    mDrive.periodic();
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
    mDrive.zeroHeading();
    mDrive.resetOdometry();

    mAutoCommand = new FollowLeftWall(800).withTimeout(200);

    if (mAutoCommand != null) 
    {
      mAutoCommand.schedule();
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
  public void teleopInit() {
    mDrive.zeroHeading();
    mDrive.resetOdometry();

    if (mAutoCommand != null) {
      mAutoCommand.cancel();
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
    System.out.format("-45:%8.2f 0:%8.2f 45:%8.2f 60:%8.2f \n", 
    mLidar.getNdeg45(),
    mLidar.get0(),
    mLidar.get45(),
    mLidar.get60());
    ;

    double speed = -1*mJoystick.getRawAxis(1);
    double turn = mJoystick.getRawAxis(0);

    if(true == mJoystick.getRawButton(1))
    {
      speed *= frc.robot.Constants.DriveConstants.kSlowSpeed;
      turn *= frc.robot.Constants.DriveConstants.kSlowTurnSpeed;
    }
    else
    {
      if(false == mJoystick.getRawButton(2))
      {
        speed *= frc.robot.Constants.DriveConstants.kNormalSpeed;
        turn *= frc.robot.Constants.DriveConstants.kNormalTurnSpeed ;
      }
    }

    mDrive.arcadeDrive(speed,turn);
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
  }
}
