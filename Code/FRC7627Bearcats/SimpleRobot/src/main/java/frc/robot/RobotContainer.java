package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.DriveBase;
import frc.robot.commands.DefaultDrive;

public class RobotContainer {
  private final DriveBase mRobotDrive = new DriveBase();

  Joystick mDriverController =  new Joystick(OIConstants.kDriveXBoxcontroller);

  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    mRobotDrive.setDefaultCommand(new DefaultDrive(
      mRobotDrive,
      () -> -mDriverController.getRawAxis(1),
      () -> -mDriverController.getRawAxis(5),
      () -> mDriverController.getRawButton(5),
      () -> mDriverController.getRawButton(6)));
  }

  private void configureButtonBindings() {
  }
}
