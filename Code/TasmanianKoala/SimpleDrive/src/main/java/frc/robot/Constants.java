/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

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
    public static final double kSlowSpeed = .2;
    public static final double kSlowTurnSpeed = .3;
    public static final double kNormalSpeed = .7;
    public static final double kNormalTurnSpeed = .7;
    public static final double kDeadBand = .08;
    public static final double kDeadBandRot = .18;
    public static final double kPgain = 0.003; /* percent throttle per degree of error */
    public static final double kDgain = 0.0002; /* percent throttle per angular velocity dps */
    public static final double kMaxCorrectionRatio = 0.20; /*
                                                     * cap corrective turning throttle to 30 percent of forward throttle
                                                     */
  }

  public static final class OIConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static final class AutoConstants {
    public static final double kMaxSpeedMetersPerSecond = 3;
    public static final double kMaxAccelerationMetersPerSecondSquared = 3;

    // Reasonable baseline values for a RAMSETE follower in units of meters and seconds
    public static final double kRamseteB = 2;
    public static final double kRamseteZeta = 0.7;
  }
}
