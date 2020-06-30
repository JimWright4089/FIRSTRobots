/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private RobotContainer mRobotContainer;
  private DriveSubsystem mDrive;
  NetworkTableEntry mLEDRing;
  private Joystick mJoystick = new Joystick(0);

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    mRobotContainer = new RobotContainer();
    mDrive = mRobotContainer.getDriveSubsystem();
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    mLEDRing = inst.getEntry("/LEDColor");
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
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

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    mDrive.zeroHeading();
    mDrive.resetOdometry();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override

  public void autonomousPeriodic() {
  }


  public void teleopInit() {
    mDrive.zeroHeading();
    mDrive.resetOdometry();
  }
  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    mDrive.arcadeDrive(mJoystick.getRawAxis(1)*.6,mJoystick.getRawAxis(4)*.6);
  }

  
  PowerDistributionPanel mPDP = new PowerDistributionPanel(18);
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    double left = .6;
    double right = .6;

    mDrive.tankDrive(left, right);

    if(mPDP.getCurrent(15) > 0) 
    {
    System.out.format("L:%8.2f R:%8.2f H:%8.2f LS:%8.2f LP:%8.2f RS:%8.2f RP:%8.2f T:%8.2f E:%8.2f 1:%8.2f 2:%8.2f\n",
    left,right,
    mDrive.getHeading(),
    mDrive.getLeftEncoderSpeed(),mDrive.getLeftEncoderPosition(),
    mDrive.getRightEncoderSpeed(),mDrive.getRightEncoderPosition(),
    mPDP.getTotalCurrent(),mPDP.getTotalPower(),
    mPDP.getCurrent(14), mPDP.getCurrent(15));
    }  
  }
}
