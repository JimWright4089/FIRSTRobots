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

import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import static frc.robot.Constants.DriveConstants.kGyroReversed;
import static frc.robot.Constants.DriveConstants.kLeftMotor1Port;
import static frc.robot.Constants.DriveConstants.kLeftMotor2Port;
import static frc.robot.Constants.DriveConstants.kRightMotor1Port;
import static frc.robot.Constants.DriveConstants.kRightMotor2Port;
import static frc.robot.Constants.DriveConstants.kTimeoutMs;
import static frc.robot.Constants.DriveConstants.kLeftEncoderPort;
import static frc.robot.Constants.DriveConstants.kRightEncoderPort;
import static frc.robot.Constants.DriveConstants.kGyroPort;
import static frc.robot.Constants.DriveConstants.kEncoderDistancePerPulse;

public class DriveSubsystem extends SubsystemBase {
  private final CANSparkMax  sMotorLeftA = new CANSparkMax(kLeftMotor1Port,MotorType.kBrushless);
  private final CANSparkMax  sMotorLeftB = new CANSparkMax(kLeftMotor2Port,MotorType.kBrushless);
  private final CANSparkMax  sMotorRightA = new CANSparkMax(kRightMotor1Port,MotorType.kBrushless);
  private final CANSparkMax  sMotorRightB = new CANSparkMax(kRightMotor2Port,MotorType.kBrushless);
  // The motors on the left side of the drive.
  private final SpeedControllerGroup sLeftMotors =
      new SpeedControllerGroup(sMotorLeftA,sMotorLeftB);

  // The motors on the right side of the drive.
  private final SpeedControllerGroup sRightMotors =
      new SpeedControllerGroup(sMotorRightA,sMotorRightB);

  // The robot's drive
  private final DifferentialDrive sDrive = new DifferentialDrive(sLeftMotors, sRightMotors);

  // The left-side drive encoder
  private final CANCoder sLeftEncoder = new CANCoder(kLeftEncoderPort);

  // The right-side drive encoder
  private final CANCoder sRightEncoder = new CANCoder(kRightEncoderPort);

  // The gyro sensor
  private final PigeonIMU sGyro = new PigeonIMU(kGyroPort);
  private PigeonIMU.FusionStatus mFusionStatus = new PigeonIMU.FusionStatus();
  private double[] mXYZDegreePerSecond = new double[3];

  // Odometry class for tracking robot pose
  private final DifferentialDriveOdometry sOdometry;

  /**
   * Creates a new DriveSubsystem.
   */
  public DriveSubsystem() {
    resetEncoders();
    sOdometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));
    sDrive.setSafetyEnabled(false);
  }

  @Override
  public void periodic() {
    sOdometry.update(Rotation2d.fromDegrees(getHeading()), getLeftEncoderPosition(), getRightEncoderPosition());
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
    return new DifferentialDriveWheelSpeeds(getLeftEncoderSpeed(), getRightEncoderSpeed());
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
  public void tankDrive(double left, double right) {
    sDrive.tankDrive(left, right);
  }

  /**
   * Controls the left and right sides of the drive directly with voltages.
   *
   * @param leftVolts  the commanded left output
   * @param rightVolts the commanded right output
   */
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    double left = leftVolts;
    double right = rightVolts;
    //System.out.printf("%f %f \n",left,right);
    System.out.printf("%s\n",sOdometry.getPoseMeters().toString());
    sLeftMotors.setVoltage(left);
    sRightMotors.setVoltage(-right);
    sDrive.feed();
  }

  /**
   * Resets the drive encoders to currently read a position of 0.
   */
  public void resetEncoders() {
    sLeftEncoder.setPosition(0.00);
    sRightEncoder.setPosition(0.00);
  }

  /**
   * Gets the average distance of the two encoders.
   *
   * @return the average of the two encoder readings
   */
  public double getAverageEncoderDistance() {
    return (sLeftEncoder.getPosition() + sRightEncoder.getPosition()) / 2.0;
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

  public double getLeftEncoderPosition()
  {
    return sLeftEncoder.getPosition() * kEncoderDistancePerPulse;
  }
  public double getRightEncoderPosition()
  {
    return sRightEncoder.getPosition() * kEncoderDistancePerPulse;
  }
  public double getLeftEncoderSpeed()
  {
    return sLeftEncoder.getVelocity() * kEncoderDistancePerPulse;
  }
  public double getRightEncoderSpeed()
  {
    return sRightEncoder.getVelocity() * kEncoderDistancePerPulse;
  }
}
