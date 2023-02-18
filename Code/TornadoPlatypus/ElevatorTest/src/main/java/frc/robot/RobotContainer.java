/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.DriveSubsystem;
import static frc.robot.Constants.OIConstants;
import frc.robot.commands.DefaultElevator;

public class RobotContainer {
  private final DriveSubsystem mRobotDrive = new DriveSubsystem();
  XboxController mDriverController = new XboxController(OIConstants.kDriverControllerPort);
  private final DefaultElevator mElevator = new DefaultElevator(mRobotDrive,
  () -> mDriverController.getAButton(),
  () -> mDriverController.getBButton(),
  () -> mDriverController.getXButton());

  public RobotContainer() {
    configureButtonBindings();

    mRobotDrive.setDefaultCommand(mElevator);
  } 

  private void configureButtonBindings() {
  }

  public DriveSubsystem getDriveSubsystem()
  {
      return mRobotDrive;
  }
}