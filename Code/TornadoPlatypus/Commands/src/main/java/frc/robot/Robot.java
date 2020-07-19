package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.command.DriveRightToPosition;
import frc.robot.command.WaitForTime;

import static frc.robot.Constants.OIConstants.kBackButton;

public class Robot extends TimedRobot {
  RobotContainer mRobotContainer = new RobotContainer();
  boolean mDisplay = false;
  boolean mOldBackButton = false;
  Joystick mJoystick;
  DriveSubsystem mDriveSystem;

  WaitForTime mWaitForTime;
  DriveRightToPosition mDriveRightToPosition;

  @Override
  public void robotInit() {
    mJoystick    = mRobotContainer.getDriveStick();
    mDriveSystem = mRobotContainer.getDriveSubsystem();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    mWaitForTime = new WaitForTime(20000);

    if (mWaitForTime != null) {
      mWaitForTime.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    mDriveRightToPosition = new DriveRightToPosition(50000,mDriveSystem);

    if (mDriveRightToPosition != null) {
      mDriveRightToPosition.schedule();
    }
  }

  @Override
  public void teleopPeriodic() {
  }

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

  @Override
  public void disabledInit() {
    mDriveSystem.tankDrive(0, 0);
  }

  @Override
  public void disabledPeriodic() {
    if((true == mJoystick.getRawButton(kBackButton)) &&
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

    mOldBackButton = mJoystick.getRawButton(kBackButton);
  }
}
