/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utils;

public class DriveParam  {
  private double mForward = 0.0;
  private double mRotation = 0.0;

  public DriveParam() {
  }

  public DriveParam(double forward, double rotation) {
    mForward = forward;
    mRotation = rotation;
  }

  public double getForward() {
    return mForward;
  }

  public double getRotation() {
    return mRotation;
  }

  public void setForward(double value) {
    mForward = value;
  }

  public void setRotation(double value) {
    mRotation = value;
  }

  public void multiply(double forward, double turn)
  {
      mForward *= forward;
      mRotation *= turn;
  }

  public void clear()
  {
    mForward = 0.0;
    mRotation = 0.0;
  }

}