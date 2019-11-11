package frc.robot;

import frc.robot.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI
{
  public Joystick driveStick;

  public OI()
  {
    driveStick = new Joystick(0);

    // SmartDashboard Buttons
    SmartDashboard.putData("UserDrive", new UserDrive());
  }

  public Joystick getDriveStick()
  {
    return driveStick;
  }
}
