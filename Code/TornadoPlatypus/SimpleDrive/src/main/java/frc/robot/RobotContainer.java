/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.DriveSubsystem;
import static frc.robot.Constants.OIConstants;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems
  private final DriveSubsystem mRobotDrive = new DriveSubsystem();
 
  // The driver's controller
  XboxController mDriverController = new XboxController(OIConstants.kDriverControllerPort);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Configure default commands
    // Set the default drive command to split-stick arcade drive
    mRobotDrive.setDefaultCommand(
        // A split-stick arcade command, with forward/backward controlled by the left
        // hand, and turning controlled by the right.
        new DefaultDrive(
            mRobotDrive,
            () -> mDriverController.getRawAxis(1),
            () -> mDriverController.getRawAxis(4),
            () -> mDriverController.getAButton(),
            () -> mDriverController.getBButton()));
}

  private void configureButtonBindings() {
    
    //mDriverController.a().onTrue(m_robotArm.setArmGoalCommand(2));
    new JoystickButton(mDriverController, Button.kRightBumper.value)
        .whileTrue(mRobotDrive.setMaxOutput(.25));

//      .whenPressed(() -> driveBase.resetOdometry(new Pose2d()));

  }

  public DriveSubsystem getDriveSubsystem()
  {
      return mRobotDrive;
  }
}