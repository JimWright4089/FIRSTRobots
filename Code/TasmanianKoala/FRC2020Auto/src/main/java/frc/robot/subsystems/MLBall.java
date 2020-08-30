//----------------------------------------------------------------------------
//
//  $Workfile: MLBall.java$
//
//  $Revision: X$
//
//  Project:    Tasmanian Koala
//
//                            Copyright (c) 2020
//                                 Jim Wright
//                            All Rights Reserved
//
//  Modification History:
//  $Log:
//  $
//
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
//  Package
//----------------------------------------------------------------------------
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.*;

public class MLBall extends SubsystemBase {

  NetworkTableEntry mX;  
  NetworkTableEntry mArea;  
  final double kXCenter = 360.0;

  static MLBall mInstance;

  public static MLBall getInstance()
  {
    if(null == mInstance)
    {
      mInstance = new MLBall();
    }
    return mInstance;
  }

  private MLBall() {
      NetworkTableInstance inst = NetworkTableInstance.getDefault();
      mX = inst.getEntry("/ML/centerX");
      mArea = inst.getEntry("/ML/area");
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

  public double getArea() {
    return (mArea.getDouble(0));
  }

}
