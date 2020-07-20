//----------------------------------------------------------------------------
//
//  $Workfile: FollowLeftWall.java$
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
package dc;

//----------------------------------------------------------------------------
//  Imports
//----------------------------------------------------------------------------
import java.util.function.Supplier;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: Robot
//
// Purpose:
//   The entry point
//
//----------------------------------------------------------------------------
public class Robot extends TimedRobot {
  // ----------------------------------------------------------------------------
  // Class Constants
  // ----------------------------------------------------------------------------
  public static final int kLeftMotor1Port = 1;
  public static final int kRightMotor1Port = 2;
  public static final int kGyroPort = 10;
  public static final boolean kLeftEncoderReversed = false;
  public static final boolean kRightEncoderReversed = true;
  
  public static final double kRawEncoderCPR = 2048;
  public static final double kFirstStage = 50.0/10.0;
  public static final double kSecondStage = 48.0/16.0;
  public static final double kEncoderCPR = kRawEncoderCPR * kFirstStage * kSecondStage;
  public static final double kWheelDiameterMeters = 0.1524;
  public static final double kEncoderDistancePerPulse =
      // Assumes the encoders are directly mounted on the wheel shafts
      (kWheelDiameterMeters * Math.PI) / (double) kEncoderCPR;

  public static final boolean kGyroReversed = true;

  // ----------------------------------------------------------------------------
  // Class Statics
  // ----------------------------------------------------------------------------
  private final WPI_TalonFX  sMotorLeftA = new WPI_TalonFX(kLeftMotor1Port);
  private final WPI_TalonFX  sMotorRightA = new WPI_TalonFX(kRightMotor1Port);
  private final SpeedControllerGroup sLeftMotors = new SpeedControllerGroup(sMotorLeftA);
  private final SpeedControllerGroup sRightMotors = new SpeedControllerGroup(sMotorRightA);
  private final DifferentialDrive sDrive = new DifferentialDrive(sLeftMotors, sRightMotors);

  // ----------------------------------------------------------------------------
  // Class Attributes
  // ----------------------------------------------------------------------------
  Joystick mStick;
  Supplier<Double> mLeftEncoderPosition;
  Supplier<Double> mLeftEncoderRate;
  Supplier<Double> mRightEncoderPosition;
  Supplier<Double> mRightEncoderRate;
  Supplier<Double> mGyroAngleRadians;

  NetworkTableEntry mAutoSpeedEntry =  NetworkTableInstance.getDefault().getEntry("/robot/autospeed");
  NetworkTableEntry mTelemetryEntry =  NetworkTableInstance.getDefault().getEntry("/robot/telemetry");
  NetworkTableEntry mRotateEntry = NetworkTableInstance.getDefault().getEntry("/robot/rotate");

  double mPriorAutospeed = 0;
  Number[] mNumberArray = new Number[10];

  // ----------------------------------------------------------------------------
  // Purpose:
  // Get Robot ready to go
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  @Override
  public void robotInit() {
    if (!isReal()) SmartDashboard.putData(new SimEnabler());

    mStick = new Joystick(0);

    PigeonIMU pigeon = new PigeonIMU(10);
    mGyroAngleRadians = () -> {
      // Allocating a new array every loop is bad but concise
      double[] xyz = new double[3];
      pigeon.getAccumGyro(xyz);
      return Math.toRadians(xyz[2]);
    };

    sDrive.setDeadband(0);

    double encoderConstant =
    (1 / kEncoderCPR) * kWheelDiameterMeters * Math.PI;

    mLeftEncoderPosition  = () -> (double)(sMotorLeftA.getSelectedSensorPosition()) * encoderConstant;
    mLeftEncoderRate      = () -> (double)(sMotorLeftA.getSelectedSensorVelocity()) * encoderConstant;
    mRightEncoderPosition = () -> -1.0*(double)(sMotorRightA.getSelectedSensorPosition()) * encoderConstant;
    mRightEncoderRate     = () -> -1.0*(double)(sMotorRightA.getSelectedSensorVelocity()) * encoderConstant;

    // Reset encoders
    sMotorLeftA.setSelectedSensorPosition(-0);
    sMotorRightA.setSelectedSensorPosition(-0);

    // Set the update rate instead of using flush because of a ntcore bug
    // -> probably don't want to do this on a robot in competition
    NetworkTableInstance.getDefault().setUpdateRate(0.010);
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Get Robot ready to go
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  @Override
  public void robotPeriodic() {
    // feedback for users, but not used by the control program
    SmartDashboard.putNumber("l_encoder_pos",  mLeftEncoderPosition.get());
    SmartDashboard.putNumber("l_encoder_rate", mLeftEncoderRate.get());
    SmartDashboard.putNumber("r_encoder_pos",  mRightEncoderPosition.get());
    SmartDashboard.putNumber("r_encoder_rate", mRightEncoderRate.get());
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Get Teleop ready to go
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  @Override
  public void teleopInit() {
    System.out.println("Robot in operator control mode");
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Tele Loop
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  @Override
  public void teleopPeriodic() {
    System.out.printf("%7.3f %7.3f %7.3f %7.3f %7.3f %7.3f %7.3f\n",
    mLeftEncoderPosition.get(),
    mRightEncoderPosition.get(),
    mLeftEncoderRate.get(),
    mRightEncoderRate.get(),
    sMotorLeftA.getMotorOutputVoltage(),
    sMotorRightA.getMotorOutputVoltage(),
    mGyroAngleRadians.get());
    sDrive.arcadeDrive(-mStick.getY()*.5, mStick.getX()*.4);

    if(true == mStick.getRawButton(1))
    {
      // Reset encoders
      sMotorLeftA.setSelectedSensorPosition(-0);
      sMotorRightA.setSelectedSensorPosition(-0);
    }
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Gets the auto ready to run
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  @Override
  public void autonomousInit() {
    System.out.println("Robot in autonomous mode");
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Auto Loop
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  @Override
  public void autonomousPeriodic() {

    // Retrieve values to send back before telling the motors to do something
    double now = Timer.getFPGATimestamp();

    double leftPosition    = mLeftEncoderPosition.get();
    double leftRate        = mLeftEncoderRate.get();
    double rightPosition   = mRightEncoderPosition.get();
    double rightRate       = mRightEncoderRate.get();
    double battery         = RobotController.getBatteryVoltage();
    double leftMotorVolts  = sMotorLeftA.getMotorOutputVoltage();
    double rightMotorVolts = -1.0*sMotorRightA.getMotorOutputVoltage();

    // Retrieve the commanded speed from NetworkTables
    double autospeed = mAutoSpeedEntry.getDouble(0);
    mPriorAutospeed = autospeed;

    // command motors to do things
    sDrive.tankDrive(
      (mRotateEntry.getBoolean(false) ? -1 : 1) * autospeed, autospeed,
      false
    );

    // send telemetry data array back to NT
    mNumberArray[0] = now;
    mNumberArray[1] = battery;
    mNumberArray[2] = autospeed;
    mNumberArray[3] = leftMotorVolts;
    mNumberArray[4] = rightMotorVolts;
    mNumberArray[5] = leftPosition;
    mNumberArray[6] = rightPosition;
    mNumberArray[7] = leftRate;
    mNumberArray[8] = rightRate;
    mNumberArray[9] = mGyroAngleRadians.get();

    mTelemetryEntry.setNumberArray(mNumberArray);
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Get Disable ready to go
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  @Override
  public void disabledInit() {
    System.out.println("Robot disabled");
    sDrive.tankDrive(0, 0);
  }

  // ----------------------------------------------------------------------------
  // Purpose:
  // Loop while disabled
  //
  // Notes:
  // None
  //
  // ----------------------------------------------------------------------------
  @Override
  public void disabledPeriodic() {}

  // ----------------------------------------------------------------------------
  // Purpose:
  // Test Loop
  //
  // Notes:
  // Make sure everything is driving in the right direction
  //
  // ----------------------------------------------------------------------------
  @Override
  public void testPeriodic()
  {
    System.out.printf("%f %f %f %f \n",
    mLeftEncoderPosition.get(),
    mRightEncoderPosition.get(),
    mLeftEncoderRate.get(),
    mRightEncoderRate.get());
    sDrive.tankDrive(.4,.4);
  }
}
