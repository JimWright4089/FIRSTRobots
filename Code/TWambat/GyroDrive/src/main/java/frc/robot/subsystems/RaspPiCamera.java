/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.*;

import static frc.robot.Constants.RaspPiImage.kXCenter;
import static frc.robot.Constants.RaspPiImage.kWidth;;

public class RaspPiCamera extends SubsystemBase {

  NetworkTableEntry mX;  
  /**
   * Creates a new DriveSubsystem.
   */
  public RaspPiCamera() {
      NetworkTableInstance inst = NetworkTableInstance.getDefault();
      mX = inst.getEntry("/rp/x");
  }

  @Override
  public void periodic() {
  }

  /**
   * Gets the average distance of the two encoders.
   *
   * @return the average of the two encoder readings
   */
  public double getX() {
    return (kXCenter - mX.getDouble(kXCenter));
  }

}
