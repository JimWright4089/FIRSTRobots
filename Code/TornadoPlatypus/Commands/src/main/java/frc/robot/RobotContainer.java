package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.DriveSubsystem;

import static frc.robot.Constants.OIConstants.kDriverControllerPort;

public class RobotContainer {
  DriveSubsystem mDriveSystem = new DriveSubsystem();
  Joystick mDriveStick = new Joystick(kDriverControllerPort);
    
  public RobotContainer() {
    configureButtonBindings();
  }

  private void configureButtonBindings() {
  }

  public DriveSubsystem getDriveSubsystem()
  {
      return mDriveSystem;
  }

  public Joystick getDriveStick()
  {
      return mDriveStick;
  }
}