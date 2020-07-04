package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.*;

public class Lidar extends SubsystemBase {

  NetworkTableEntry m0degs;  
  NetworkTableEntry mNeg70degs;  
  NetworkTableEntry mNeg45degs;  
  /**
   * Creates a new DriveSubsystem.
   */
  public Lidar() {
      NetworkTableInstance inst = NetworkTableInstance.getDefault();
      m0degs = inst.getEntry("/Lidar/0");
      mNeg70degs = inst.getEntry("/Lidar/-70");
      mNeg45degs = inst.getEntry("/Lidar/-70");
  }

  @Override
  public void periodic() {
  }

  /**
   * Gets the average distance of the two encoders.
   *
   * @return the average of the two encoder readings
   */
  public double get0() {
    return (m0degs.getDouble(500));
  }
  public double getNdeg70() {
    return (mNeg70degs.getDouble(500));
  }
  public double getNdeg45() {
    return (mNeg45degs.getDouble(500));
  }

}
