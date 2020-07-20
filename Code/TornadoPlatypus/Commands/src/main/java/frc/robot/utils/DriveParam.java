//----------------------------------------------------------------------------
//
//  $Workfile: DriveRightToPosition.java$
//
//  $Revision: X$
//
//  Project:    Tornado Platypus
//
//                            Copyright (c) 2020
//                              James A Wright
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
package frc.robot.utils;

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: DriveParam
//
// Purpose:
//   Drive a wheel to a position
//
//----------------------------------------------------------------------------
public class DriveParam  {
  private double mForward = 0.0;
  private double mRotation = 0.0;

  // ----------------------------------------------------------------------------
  // Purpose:
  // Constructor
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  public DriveParam() {
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Constructor
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  public DriveParam(double forward, double rotation) {
    mForward = forward;
    mRotation = rotation;
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Return the forward part
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  public double getForward() {
    return mForward;
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Return the rotation part
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  public double getRotation() {
    return mRotation;
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Set the forward part
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  public void setForward(double value) {
    mForward = value;
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Set the rotation part
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  public void setRotation(double value) {
    mRotation = value;
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Multiply the params
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  public void multiply(double forward, double turn)
  {
      mForward *= forward;
      mRotation *= turn;
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Clear out the params
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  public void clear()
  {
    mForward = 0.0;
    mRotation = 0.0;
  }
}