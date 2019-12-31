/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.SparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import static frc.robot.Constants.DriveConstants.kEncoderDistancePerPulse;
import static frc.robot.Constants.DriveConstants.kEncoderPulsePerSecond;
import static frc.robot.Constants.DriveConstants.kGyroReversed;
import static frc.robot.Constants.DriveConstants.kLeftMotor1Port;
import static frc.robot.Constants.DriveConstants.kLeftMotor2Port;
import static frc.robot.Constants.DriveConstants.kRightMotor1Port;
import static frc.robot.Constants.DriveConstants.kRightMotor2Port;
import static frc.robot.Constants.DriveConstants.kTimeoutMs;

public class DriveSubsystem  {
  private final CANSparkMax  sMotorLeftA = new CANSparkMax(kLeftMotor1Port,MotorType.kBrushless);
  private final CANSparkMax  sMotorLeftB = new CANSparkMax(kLeftMotor2Port,MotorType.kBrushless);
  private final CANSparkMax  sMotorRightA = new CANSparkMax(kRightMotor1Port,MotorType.kBrushless);
  private final CANSparkMax  sMotorRightB = new CANSparkMax(kRightMotor2Port,MotorType.kBrushless);

  // The motors on the left side of the drive.
  private final SpeedControllerGroup sLeftMotors = new SpeedControllerGroup(sMotorLeftA,sMotorLeftB);

  // The motors on the right side of the drive.
  private final SpeedControllerGroup sRightMotors = new SpeedControllerGroup(sMotorRightA,sMotorRightB);

  // The robot's drive
  private final DifferentialDrive sDrive = new DifferentialDrive(sLeftMotors, sRightMotors);
  
  // The gyro sensor
  private final PigeonIMU sGyro = new PigeonIMU(0x20);

  private PigeonIMU.FusionStatus mFusionStatus = new PigeonIMU.FusionStatus();
  private double[] mXYZDegreePerSecond = new double[3];

  /**
   * Creates a new DriveSubsystem.
   */
  public DriveSubsystem() {
      sLeftMotors.setInverted(true);
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
}
