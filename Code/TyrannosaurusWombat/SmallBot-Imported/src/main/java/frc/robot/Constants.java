//----------------------------------------------------------------------------
//
//  $Workfile: Constants.java$
//
//  $Revision: X$
//
//  Project:    Stealth Libraries
//
//                            Copyright (c) 2018
//                           Cedarcrest High School
//                            All Rights Reserved
//
//  Modification History:
//  $Log:
//  $
//
//----------------------------------------------------------------------------
package frc.robot;

//----------------------------------------------------------------------------
//Class Declarations
//----------------------------------------------------------------------------
//
//Class Name: Constants
//
//Purpose:
//  The Constants
//
//----------------------------------------------------------------------------
public class Constants
{
  // Drive Assignments
  public static final int constMotorleftA = 1;
  public static final int constMotorleftB = 3;
  public static final int constMotorRightA = 2;
  public static final int constMotorRightB = 4;
  
  // Drivers Speed
  public static final double kNormalSpeed = 0.3;
  public static final double kNormalTurnSpeed = 0.4;
  public static final double kSlowSpeed = 0.15;
  public static final double kSlowTurnSpeed = 0.3;
  
  // User Interface consts
  public static final int kFastButton = 1;
  public static final int kSlowButton = 2;
  public static final int kForwardAxes = 1;
  public static final int kMainTurnAxes = 0;
  public static final int kBackupTurnAxes = 3;
  
  // Gyro Constants
  public static final int kGyroZ = 2;
  public static final double kGyroDeadband = 0.1;
  
  // Motor setup constants
  public static final int kPIDLoopIdx = 0;
  public static final int kTimeoutMs = 30;
  public static final int kBaseTrajPeriodMs = 0;
  public static final double kNeutralDeadband  = 0.01;  
}
