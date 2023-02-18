package frc.robot.ramsete;

import edu.wpi.first.wpilibj2.command.Command;

public class DriveDefault
{
    DriveRamsete mDriveRamsete = new DriveRamsete();

    public DriveDefault() {
      mDriveRamsete = new DriveRamsete();
    }    

    public Command getCommand()
    {
      return mDriveRamsete.getRamsete(); 
    }
}