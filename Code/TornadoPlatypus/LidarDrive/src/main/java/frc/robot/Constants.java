//----------------------------------------------------------------------------
//
//  $Workfile: Constants.java$
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
package frc.robot;

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: Constants
//
// Purpose:
//   The entry point
//
//----------------------------------------------------------------------------
public final class Constants {
  // ----------------------------------------------------------------------------
  // Class Statics for Driving the Robot
  // ----------------------------------------------------------------------------
  public static final class DriveConstants {
    public static final int kLeftMotor1Port = 1;
    public static final int kRightMotor1Port = 2;
    public static final int kGyroPort = 10;
    public static final boolean kLeftEncoderReversed = false;
    public static final boolean kRightEncoderReversed = true;
    
    public static final int kEncoderCPR = 4096;
    public static final int kEncoderPulsePerSecond = 10;
    public static final double kWheelDiameterMeters = 0.1524;
    public static final double kEncoderDistancePerPulse =
        // Assumes the encoders are directly mounted on the wheel shafts
        (kWheelDiameterMeters * Math.PI) / (double) kEncoderCPR;

    public static final boolean kGyroReversed = true;

    // These are example values only - DO NOT USE THESE FOR YOUR OWN ROBOT!
    // These characterization values MUST be determined either experimentally or theoretically
    // for *your* robot's drive.
    // The RobotPy Characterization Toolsuite provides a convenient tool for obtaining these
    // values for your robot.
    public static final double ksVolts = 1;
    public static final double kvVoltSecondsPerMeter = 0.8;
    public static final double kaVoltSecondsSquaredPerMeter = 0.15;
    public static final int kTimeoutMs = 20;

    // Example value only - as above, this must be tuned for your drive!
    public static final double kPDriveVel = 0.5;

    public static final double kSpeedGain = 0.02; // The ramp for the speed
    public static final double kSlowSpeed = .4;
    public static final double kSlowTurnSpeed = .3;
    public static final double kNormalSpeed = .6;
    public static final double kNormalTurnSpeed = .5;
    public static final double kDeadBand = .08;
    public static final double kDeadBandRot = .18;
    public static final double kPgain = 0.003; /* percent throttle per degree of error */
    public static final double kDgain = 0.0002; /* percent throttle per angular velocity dps */
    public static final double kMaxCorrectionRatio = 0.20; /*
                                                     * cap corrective turning throttle to 30 percent of forward throttle
                                                     */




    public static final double kMoveP = .005;
    public static final double kMoveI = 0;
    public static final double kMoveD = 0;                                                     

    public static final double kMaxMoveRateDegPerS = 1;
    public static final double kMaxMoveAccelerationDegPerSSquared = .4;

    public static final double kMoveToleranceDeg = 50;
    public static final double kMoveRateToleranceDegPerS = .3; // degrees per second

  }

  // ----------------------------------------------------------------------------
  // Class Statics for Operator Interface
  // ----------------------------------------------------------------------------
  public static final class OIConstants {
    public static final int kDriverControllerPort = 0;
  }

  // ----------------------------------------------------------------------------
  // Class Statics for Auto
  // ----------------------------------------------------------------------------
  public static final class AutoConstants {
    public static final double kMaxSpeedMetersPerSecond = 3;
    public static final double kMaxAccelerationMetersPerSecondSquared = 3;

    // Reasonable baseline values for a RAMSETE follower in units of meters and seconds
    public static final double kRamseteB = 2;
    public static final double kRamseteZeta = 0.7;
  }
}
