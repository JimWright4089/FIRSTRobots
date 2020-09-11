//----------------------------------------------------------------------------
//
//  $Workfile: DriveSubsystem.java$
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
  //----------------------------------------------------------------------------
  //  Static Class Atributes
  //----------------------------------------------------------------------------
  static DriveSubsystem mInstance;

  //----------------------------------------------------------------------------
  //  Class Atributes
  //----------------------------------------------------------------------------
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

  //----------------------------------------------------------------------------
  //  Purpose:
  //   Constructor
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public static DriveSubsystem getInstance()
  {
    if(null == mInstance)
    {
      mInstance = new DriveSubsystem();
    }
    return mInstance;
  }

  /**
   * Creates a new DriveSubsystem.
   */
  private DriveSubsystem() {
    resetEncoders();
    sOdometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));
    System.out.println("new DifferentialDriveOdometry");
    sDrive.setSafetyEnabled(true);
  }

  @Override
  public void periodic() {
    sOdometry.update(Rotation2d.fromDegrees(getHeading()), getLeftEncoderPosition(), getRightEncoderPosition());
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //   Return the current pose
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public Pose2d getPose() {
    return sOdometry.getPoseMeters();
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //   Return the degrees the robot is pointing
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public double getPoseThetaDegrees()
  {
    return sOdometry.getPoseMeters().getRotation().getDegrees();
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //   Return the speed of the wheels
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(getLeftEncoderSpeed(), getRightEncoderSpeed());
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //   Reset the robot to 0,0 and theta 0
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    sOdometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //   Drive the base with one joystick
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public void arcadeDrive(double fwd, double rot) {
    sDrive.arcadeDrive(fwd, rot);
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //   Drive the robot with two joysticks
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public void tankDrive(double left, double right) {
    sDrive.tankDrive(left, right);
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //   Drive the robot with voltages as the speed
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    double left = leftVolts;
    double right = rightVolts;
    sLeftMotors.setVoltage(left);
    sRightMotors.setVoltage(-right);
    sDrive.feed();
  }

  public void tankDriveVoltsBackwards(double leftVolts, double rightVolts) {
    double left = leftVolts;
    double right = rightVolts;
    //System.out.printf("%f %f \n",left,right);
   //System.out.printf("%s\n",sOdometry.getPoseMeters().toString());
    sLeftMotors.setVoltage(-left);
    sRightMotors.setVoltage(right);
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
