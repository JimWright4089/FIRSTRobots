/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.*;

import static frc.robot.Constants.DriveConstants.kEncoderDistancePerPulse;
import static frc.robot.Constants.DriveConstants.kEncoderPulsePerSecond;
import static frc.robot.Constants.DriveConstants.kGyroReversed;
import static frc.robot.Constants.DriveConstants.kLeftMotor1Port;
import static frc.robot.Constants.DriveConstants.kLeftMotor2Port;
import static frc.robot.Constants.DriveConstants.kRightMotor1Port;
import static frc.robot.Constants.DriveConstants.kRightMotor2Port;
import static frc.robot.Constants.DriveConstants.kTimeoutMs;

public class DriveSubsystem extends SubsystemBase {
  private final WPI_TalonSRX sMotorLeftA = new WPI_TalonSRX(kLeftMotor1Port);
  private final WPI_TalonSRX sMotorLeftB = new WPI_TalonSRX(kLeftMotor2Port);
  private final WPI_TalonSRX sMotorRightA = new WPI_TalonSRX(kRightMotor1Port);
  private final WPI_TalonSRX sMotorRightB = new WPI_TalonSRX(kRightMotor2Port);

  // The motors on the left side of the drive.
  private final SpeedControllerGroup sLeftMotors = new SpeedControllerGroup(sMotorLeftA,sMotorLeftB);

  // The motors on the right side of the drive.
  private final SpeedControllerGroup sRightMotors = new SpeedControllerGroup(sMotorRightA,sMotorRightB);

  // The robot's drive
  private final DifferentialDrive sDrive = new DifferentialDrive(sLeftMotors, sRightMotors);
  
  // The gyro sensor
  private final PigeonIMU sGyro = new PigeonIMU(sMotorRightB);

  // Odometry class for tracking robot pose
  private final DifferentialDriveOdometry sOdometry;

  private PigeonIMU.FusionStatus mFusionStatus = new PigeonIMU.FusionStatus();
  private double[] mXYZDegreePerSecond = new double[3];

  /**
   * Creates a new DriveSubsystem.
   */
  public DriveSubsystem() {
    // Sets the distance per pulse for the encoders
    SetUpEncoder(sMotorLeftA);
    SetUpEncoder(sMotorLeftB);
    SetUpEncoder(sMotorRightA);
    SetUpEncoder(sMotorRightB);

    resetEncoders();
    sOdometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));
  }

  @Override
  public void periodic() {
    // Update the odometry in the periodic block
    sOdometry.update(Rotation2d.fromDegrees(getHeading()), getLeftEncoderDistance(), getRightEncoderDistance());
  }

  /**
   * Returns the currently-estimated pose of the robot.
   *
   * @return The pose.
   */
  public Pose2d getPose() {
    return sOdometry.getPoseMeters();
  }

  /**
   * Returns the current wheel speeds of the robot.
   *
   * @return The current wheel speeds.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(getLeftEncoderRate(), getRightEncoderRate());
  }

  /**
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   */
  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    sOdometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
  }

  /**
   * Drives the robot using arcade controls.
   *
   * @param fwd the commanded forward movement
   * @param rot the commanded rotation
   */
  public void arcadeDrive(double fwd, double rot) {
    sDrive.arcadeDrive(fwd, rot);
  }

  /**
   * Controls the left and right sides of the drive directly with voltages.
   *
   * @param leftVolts  the commanded left output
   * @param rightVolts the commanded right output
   */
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    sLeftMotors.setVoltage(leftVolts);
    sRightMotors.setVoltage(-rightVolts);
  }

  /**
   * Resets the drive encoders to currently read a position of 0.
   */
  public void resetEncoders() {
    sMotorLeftA.setSelectedSensorPosition(0,0,kTimeoutMs);
    sMotorRightA.setSelectedSensorPosition(0,0,kTimeoutMs);
  }

  /**
   * Gets the average distance of the two encoders.
   *
   * @return the average of the two encoder readings
   */
  public double getAverageEncoderDistance() {
    return (getLeftEncoderDistance() + getRightEncoderDistance()) / 2.0;
  }

  /**
   * Gets the left drive encoder.
   *
   * @return the left drive encoder
   */
  public double getLeftEncoderDistance() {
    return kEncoderDistancePerPulse * (double)sMotorLeftA.getSelectedSensorPosition(0);
  }

  /**
   * Gets the left drive encoder.
   *
   * @return the left drive encoder
   */
  public double getLeftEncoderRate() {
    return kEncoderDistancePerPulse * (double)kEncoderPulsePerSecond * (double)sMotorLeftA.getSelectedSensorVelocity(0);
  }

  /**
   * Gets the right drive encoder.
   *
   * @return the right drive encoder
   */
  public double getRightEncoderDistance() {
    return kEncoderDistancePerPulse * (double)sMotorRightA.getSelectedSensorPosition(0);
  }

  /**
   * Gets the right drive encoder.
   *
   * @return the right drive encoder
   */
  public double getRightEncoderRate() {
    return kEncoderDistancePerPulse * (double)kEncoderPulsePerSecond * (double)sMotorRightA.getSelectedSensorVelocity(0);
  }

  /**
   * Sets the max output of the drive.  Useful for scaling the drive to drive more slowly.
   *
   * @param maxOutput the maximum output to which the drive will be constrained
   */
  public void setMaxOutput(double maxOutput) {
    sDrive.setMaxOutput(maxOutput);
  }

  /**
   * Zeroes the heading of the robot.
   */
  public void zeroHeading() {
    sGyro.setFusedHeading(0.0, kTimeoutMs);
  }

  /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in degrees, from 180 to 180
   */
  public double getHeading() {
    sGyro.getFusedHeading(mFusionStatus);

    return Math.IEEEremainder(mFusionStatus.heading, 360) * (kGyroReversed ? -1.0 : 1.0);
  }

  /**
   * Returns the turn rate of the robot.
   *
   * @return The turn rate of the robot, in degrees per second
   */
  public double getTurnRate() {
    sGyro.getRawGyro(mXYZDegreePerSecond);

    return mXYZDegreePerSecond[2] * (kGyroReversed ? -1.0 : 1.0);
  }

  private static void SetUpEncoder(WPI_TalonSRX talon)
  {
    talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, kTimeoutMs);
    talon.setSensorPhase(true);
  }
}
