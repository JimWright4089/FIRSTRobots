package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.*;
import edu.wpi.first.hal.HALUtil;

public class Lidar extends SubsystemBase {

  long mTimeout = 170000;
  NetworkTableEntry m0degs;  
  NetworkTableEntry mNeg70degs;  
  NetworkTableEntry mNeg45degs;  
  NetworkTableEntry m45degs;  
  NetworkTableEntry m60degs;  
  NetworkTableEntry m55degs;  
  NetworkTableEntry m65degs;  
  /**
   * Creates a new DriveSubsystem.
   */
  public Lidar() {
      NetworkTableInstance inst = NetworkTableInstance.getDefault();
      m0degs = inst.getEntry("/Lidar/0");
      mNeg70degs = inst.getEntry("/Lidar/-70");
      mNeg45degs = inst.getEntry("/Lidar/-45");
      m45degs = inst.getEntry("/Lidar/45");
      m60degs = inst.getEntry("/Lidar/60");
      m55degs = inst.getEntry("/Lidar/55");
      m65degs = inst.getEntry("/Lidar/65");
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
  public double get45() {
    return (m45degs.getDouble(500));
  }
  public double get60() {
    return (m60degs.getDouble(500));
  }
  public double get65() {
    return (m65degs.getDouble(500));
  }
  public double get55() {
    return (m55degs.getDouble(500));
  }

  public boolean is60Stale()
  {
      if(HALUtil.getFPGATime()-m60degs.getLastChange()>mTimeout)
      {
          return true;
      }
      return false;
  }
  public boolean is5Stale()
  {
      if(HALUtil.getFPGATime()-m55degs.getLastChange()>mTimeout)
      {
          return true;
      }
      return false;
  }
  public boolean is65Stale()
  {
      if(HALUtil.getFPGATime()-m65degs.getLastChange()>mTimeout)
      {
          return true;
      }
      return false;
  }



}
