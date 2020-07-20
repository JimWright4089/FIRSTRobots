//----------------------------------------------------------------------------
//
//  $Workfile: DriveSubsystem.java$
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
package frc.robot.subsystems;

//----------------------------------------------------------------------------
//  Imports
//----------------------------------------------------------------------------
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

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: DriveSubsystem
//
// Purpose:
//   Handles all the hardware and management of the drive base
//
//----------------------------------------------------------------------------
public class DriveSubsystem extends SubsystemBase 
{
  //----------------------------------------------------------------------------
  //  Class Statics
  //----------------------------------------------------------------------------
  private final WPI_TalonFX          sMotorLeftA  = new WPI_TalonFX(kLeftMotor1Port);
  private final WPI_TalonFX          sMotorRightA = new WPI_TalonFX(kRightMotor1Port);
  private final SpeedControllerGroup sLeftMotors  = new SpeedControllerGroup(sMotorLeftA);
  private final SpeedControllerGroup sRightMotors = new SpeedControllerGroup(sMotorRightA);
  private final DifferentialDrive    sDrive       = new DifferentialDrive(sLeftMotors, sRightMotors);
  private final PigeonIMU            sGyro        = new PigeonIMU(kGyroPort);
  private final DifferentialDriveOdometry sOdometry;
   
  //----------------------------------------------------------------------------
  //  Class Attributes
  //----------------------------------------------------------------------------
  private PigeonIMU.FusionStatus mFusionStatus = new PigeonIMU.FusionStatus();
  private double[] mXYZDegreePerSecond = new double[3];
  static DriveSubsystem mInstance;

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Returns the singleton instance
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

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Contstructor
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  private DriveSubsystem() 
  {
    resetEncoders();
    sOdometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      This is called every frame
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  @Override
  public void periodic() 
  {
    sOdometry.update(Rotation2d.fromDegrees(getHeading()), sMotorLeftA.getSelectedSensorPosition(),
        sMotorRightA.getSelectedSensorPosition());
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Return the drives pose
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public Pose2d getPose() 
  {
    return sOdometry.getPoseMeters();
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Return the differential wheel speeds, mostly for following paths
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public DifferentialDriveWheelSpeeds getWheelSpeeds() 
  {
    return new DifferentialDriveWheelSpeeds(sMotorLeftA.getSelectedSensorVelocity(), sMotorRightA.getSelectedSensorVelocity());
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Resets the pose
  //
  //  Notes:
  //      Sort of teleporting the robot around the field
  //
  //----------------------------------------------------------------------------
  public void resetOdometry(Pose2d pose) 
  {
    resetEncoders();
    sOdometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Resets the odometry to 0,0 theta 0
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public void resetOdometry() 
  {
    resetEncoders();
    sOdometry.resetPosition(new Pose2d(), Rotation2d.fromDegrees(getHeading()));
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      One stick drive
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public void arcadeDrive(double fwd, double rot) 
  {
    sDrive.arcadeDrive(fwd, rot);
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      One stick drive with processed parameters
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public void arcadeDrive(DriveParam driveParam) 
  {
    sDrive.arcadeDrive(driveParam.getForward(), driveParam.getRotation());
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      two stick drive from joystick
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public void tankDrive(double left, double right) 
  {
    sDrive.tankDrive(left, right);
    sDrive.feed();
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      two stick drive from volts
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    sLeftMotors.setVoltage(leftVolts);
    sRightMotors.setVoltage(-rightVolts);
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Clear the encocders to 0
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public void resetEncoders() 
  {
    sMotorLeftA.setSelectedSensorPosition(-0);
    sMotorRightA.setSelectedSensorPosition(-0);
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Get the center of the robots position
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public double getAverageEncoderDistance() 
  {
    return (sMotorLeftA.getSelectedSensorPosition() + sMotorRightA.getSelectedSensorPosition()) / 2.0;
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Sets the max output percentage that the motors can go
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public void setMaxOutput(double maxOutput) 
  {
    sDrive.setMaxOutput(maxOutput);
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Clear the gyro to 0
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public void zeroHeading() 
  {
    sGyro.setFusedHeading(0.0, kTimeoutMs);
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Return the heading in CW dir
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public double getHeading() 
  {
    sGyro.getFusedHeading(mFusionStatus);

    return Math.IEEEremainder(mFusionStatus.heading, 360) * (kGyroReversed ? -1.0 : 1.0);  
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Return how fast the gyro is going
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public double getTurnRate() 
  {
    sGyro.getRawGyro(mXYZDegreePerSecond);

    return mXYZDegreePerSecond[2] * (kGyroReversed ? -1.0 : 1.0);
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Return the position of the wheel 
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public double getLeftEncoderPosition()
  {
    return sMotorLeftA.getSelectedSensorPosition();
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Return the position of the wheel 
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public double getRightEncoderPosition()
  {
    return sMotorRightA.getSelectedSensorPosition();
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Return the speed of the wheel 
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public double getLeftEncoderSpeed()
  {
    return sMotorLeftA.getSelectedSensorVelocity();
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Return the speed of the wheel 
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public double getRightEncoderSpeed()
  {
    return sMotorRightA.getSelectedSensorVelocity();
  }
}
