//----------------------------------------------------------------------------
//
//  $Workfile: Constants.java$
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
package frc.robot;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final class DriveConstants {
    public static final int kLeftMotor1Port = 1;
    public static final int kLeftMotor2Port = 3;
    public static final int kRightMotor1Port = 2;
    public static final int kRightMotor2Port = 4;

    public static final int kLeftEncoderPort = 11;
    public static final int kRightEncoderPort = 12;
    public static final int kGyroPort = 10;
    public static final boolean kLeftEncoderReversed = true;
    public static final boolean kRightEncoderReversed = false;

    public static final double kTrackwidthMeters = 0.4318;
    public static final DifferentialDriveKinematics kDriveKinematics =
        new DifferentialDriveKinematics(kTrackwidthMeters);

    public static final int kEncoderCPR = 360;
    public static final double kWheelDiameterMeters = 0.1524;
    public static final double kEncoderDistancePerPulse =
        // Assumes the encoders are directly mounted on the wheel shafts
        (kWheelDiameterMeters * Math.PI) / (double) kEncoderCPR;

    public static final boolean kGyroReversed = false;
    public static final int kTimeoutMs = 20;

    public static final double ksVolts = 0.101;
    public static final double kvVoltSecondsPerMeter = 1.87;
    public static final double kaVoltSecondsSquaredPerMeter = 0.0656;

    // Example value only - as above, this must be tuned for your drive!
    public static final double kPDriveVel = 2.4;

    public static final double kTurnP = 0.002;
    public static final double kTurnI = 0.0005;
    public static final double kTurnD = 0.0006;
    public static final double kTurnPFind = 0.002;
    public static final double kTurnIFind = 0.0005;
    public static final double kTurnDFind = 0.0006;

    public static final double kTurnToleranceDeg = 2;
    public static final double kTurnRateToleranceDegPerS = 10; // degrees per second

    public static final double kMaxTurnRateDegPerS = 100;
    public static final double kMaxTurnAccelerationDegPerSSquared = 300; 
    public static final double kMaxTurnRateDegPerSFind = 100;
    public static final double kMaxTurnAccelerationDegPerSSquaredFind = 300; 
  }

  public static final class OIConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static final class AutoConstants {
    public static final double kMaxSpeedMetersPerSecond = 1;
    public static final double kMaxAccelerationMetersPerSecondSquared = .2;

    // Reasonable baseline values for a RAMSETE follower in units of meters and seconds
    public static final double kRamseteB = 2;
    public static final double kRamseteZeta = 0.7;
  }

  public static final class RaspPiImage {
    public static final double kWidth = 320;
    public static final double kXCenter = kWidth/4;
  }
}