/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.*;
import edu.wpi.first.networktables.*;

public class Robot extends TimedRobot {
  private DriveSubsystem mDriveSubsystem = new DriveSubsystem();
  private Command mAutonomousCommand;
  private RobotContainer mRobotContainer;
  private NetworkTableEntry mEntry;

  @Override
  public void robotInit() {
    mRobotContainer = new RobotContainer(mDriveSubsystem);
    mEntry = NetworkTableInstance.getDefault().getEntry("/limelight/ty2");
  }

  @Override
  public void robotPeriodic() {
    mDriveSubsystem.periodic();
    CommandScheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    mAutonomousCommand = new AutoCommandGroup(mDriveSubsystem);
    if (mAutonomousCommand != null) {
      mAutonomousCommand.schedule();
    }
    mDriveSubsystem.resetEncoders();
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    if (mAutonomousCommand != null) {
      mAutonomousCommand.cancel();
    }
  }
 
  @Override
  public void teleopPeriodic() {
    System.out.format("%8.2f\n",mEntry.getDouble(0.00));
    //mDriveSubsystem.tankDrive(mEntry.getDouble(0.00), 0.00);
  }

  @Override
  public void testPeriodic() {
    double left = 0.5;
    double right = 0.5;
    mDriveSubsystem.tankDrive(left, right);

    System.out.format("L:%8.2f R:%8.2f H:%8.2f LS:%8.2f LP:%8.2f RS:%8.2f RP:%8.2f p:%s\n",
    left,right,mDriveSubsystem.getHeading(),
    mDriveSubsystem.getLeftEncoderSpeed(),mDriveSubsystem.getLeftEncoderPosition(),
    mDriveSubsystem.getRightEncoderSpeed(),mDriveSubsystem.getRightEncoderPosition(),
    mDriveSubsystem.getPose().toString());
  }
}
