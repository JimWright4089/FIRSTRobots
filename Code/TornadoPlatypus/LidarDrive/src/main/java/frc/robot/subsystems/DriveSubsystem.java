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
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import static frc.robot.Constants.DriveConstants.kGyroReversed;
import static frc.robot.Constants.DriveConstants.kLeftMotor1Port;
import static frc.robot.Constants.DriveConstants.kRightMotor1Port;
import static frc.robot.Constants.DriveConstants.kTimeoutMs;
import static frc.robot.Constants.DriveConstants.kGyroPort;
import frc.robot.utils.DriveParam;

public class DriveSubsystem extends SubsystemBase {
  private final WPI_TalonFX  sMotorLeftA = new WPI_TalonFX(kLeftMotor1Port);
  private final WPI_TalonFX  sMotorRightA = new WPI_TalonFX(kRightMotor1Port);
  // The motors on the left side of the drive.
  private final SpeedControllerGroup sLeftMotors =
      new SpeedControllerGroup(sMotorLeftA);

  // The motors on the right side of the drive.
  private final SpeedControllerGroup sRightMotors =
      new SpeedControllerGroup(sMotorRightA);

  // The robot's drive
  private final DifferentialDrive sDrive = new DifferentialDrive(sLeftMotors, sRightMotors);

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
  }

  @Override
  public void periodic() {
    // Update the odometry in the periodic block
    sOdometry.update(Rotation2d.fromDegrees(getHeading()), sMotorLeftA.getSelectedSensorPosition(),
        sMotorRightA.getSelectedSensorPosition());
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
    return new DifferentialDriveWheelSpeeds(sMotorLeftA.getSelectedSensorVelocity(), sMotorRightA.getSelectedSensorVelocity());
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
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   */
  public void resetOdometry() {
    resetEncoders();
    sOdometry.resetPosition(new Pose2d(), Rotation2d.fromDegrees(getHeading()));
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
   * Drives the robot using arcade controls.
   *
   * @param fwd the commanded forward movement
   * @param rot the commanded rotation
   */
  public void arcadeDrive(DriveParam driveParam) {
    sDrive.arcadeDrive(driveParam.getForward(), driveParam.getRotation());
    //System.out.format("F:%8.2f R:%8.2f\n", driveParam.getForward(), driveParam.getRotation());
  }

  /**
   * Controls the left and right sides of the drive directly with voltages.
   *
   * @param leftVolts  the commanded left output
   * @param rightVolts the commanded right output
   */
  public void tankDrive(double left, double right) {
    sDrive.tankDrive(left, right);
    sDrive.feed();
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
    sMotorLeftA.setSelectedSensorPosition(-0);
    sMotorRightA.setSelectedSensorPosition(-0);
  }

  /**
   * Gets the average distance of the two encoders.
   *
   * @return the average of the two encoder readings
   */
  public double getAverageEncoderDistance() {
    return (sMotorLeftA.getSelectedSensorPosition() + sMotorRightA.getSelectedSensorPosition()) / 2.0;
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
    return sMotorLeftA.getSelectedSensorPosition();
  }
  public double getRightEncoderPosition()
  {
    return sMotorRightA.getSelectedSensorPosition();
  }
  public double getLeftEncoderSpeed()
  {
    return sMotorLeftA.getSelectedSensorVelocity();
  }
  public double getRightEncoderSpeed()
  {
    return sMotorRightA.getSelectedSensorVelocity();
  }
}
