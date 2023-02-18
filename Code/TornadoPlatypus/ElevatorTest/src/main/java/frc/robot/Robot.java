/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.DriveSubsystem;

public class Robot extends TimedRobot {
  private RobotContainer mRobotContainer;
  private DriveSubsystem mDrive;

  @Override
  public void robotInit() {
    mRobotContainer = new RobotContainer();
    mDrive = mRobotContainer.getDriveSubsystem();
  }

  @Override
  public void robotPeriodic() {
    mDrive.periodic();
    CommandScheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
  }

  @Override

  public void autonomousPeriodic() {
  }

  public void teleopInit() {
    mDrive.zeroHeading();
    mDrive.resetOdometry();
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testPeriodic() {
  }
}
