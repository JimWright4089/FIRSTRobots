//----------------------------------------------------------------------------
//
//  $Workfile: RaspPiCamera.java$
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

import static frc.robot.Constants.RaspPiImage.kXCenter;

public class RaspPiCamera extends SubsystemBase {
  //----------------------------------------------------------------------------
  //  Static Class Atributes
  //----------------------------------------------------------------------------
  static RaspPiCamera mInstance;

  //----------------------------------------------------------------------------
  //  Class Atributes
  //----------------------------------------------------------------------------
  NetworkTableEntry mX;  
  NetworkTableEntry mArea;  

  //----------------------------------------------------------------------------
  //  Purpose:
  //   Constructor
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public static RaspPiCamera getInstance()
  {
    if(null == mInstance)
    {
      mInstance = new RaspPiCamera();
    }
    return mInstance;
  }

  private RaspPiCamera() {
      NetworkTableInstance inst = NetworkTableInstance.getDefault();
      mX = inst.getEntry("/rp/x");
      mArea = inst.getEntry("/rp/area");
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
