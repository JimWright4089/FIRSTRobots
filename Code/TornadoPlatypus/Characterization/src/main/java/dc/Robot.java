/**
 * This is a very simple robot program that can be used to send telemetry to
 * the data_logger script to characterize your drivetrain. If you wish to use
 * your actual robot code, you only need to implement the simple logic in the
 * autonomousPeriodic function and change the NetworkTables update rate
 */

package dc;

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

public class Robot extends TimedRobot {

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


  Joystick stick;
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

  Supplier<Double> leftEncoderPosition;
  Supplier<Double> leftEncoderRate;
  Supplier<Double> rightEncoderPosition;
  Supplier<Double> rightEncoderRate;
  Supplier<Double> gyroAngleRadians;

  NetworkTableEntry autoSpeedEntry =
      NetworkTableInstance.getDefault().getEntry("/robot/autospeed");
  NetworkTableEntry telemetryEntry =
      NetworkTableInstance.getDefault().getEntry("/robot/telemetry");
  NetworkTableEntry rotateEntry =
    NetworkTableInstance.getDefault().getEntry("/robot/rotate");

  double priorAutospeed = 0;
  Number[] numberArray = new Number[10];

  @Override
  public void robotInit() {
    if (!isReal()) SmartDashboard.putData(new SimEnabler());

    stick = new Joystick(0);

    PigeonIMU pigeon = new PigeonIMU(10);
    gyroAngleRadians = () -> {
      // Allocating a new array every loop is bad but concise
      double[] xyz = new double[3];
      pigeon.getAccumGyro(xyz);
      return Math.toRadians(xyz[2]);
    };

    sDrive.setDeadband(0);

    double encoderConstant =
    (1 / kEncoderCPR) * kWheelDiameterMeters * Math.PI;

    leftEncoderPosition = ()
        -> (double)(sMotorLeftA.getSelectedSensorPosition()) * encoderConstant;
    leftEncoderRate = ()
        -> (double)(sMotorLeftA.getSelectedSensorVelocity()) * encoderConstant;

    rightEncoderPosition = ()
        -> -1.0*(double)(sMotorRightA.getSelectedSensorPosition()) * encoderConstant;
    rightEncoderRate = ()
        -> -1.0*(double)(sMotorRightA.getSelectedSensorVelocity()) * encoderConstant;

    // Reset encoders
    sMotorLeftA.setSelectedSensorPosition(-0);
    sMotorRightA.setSelectedSensorPosition(-0);

    // Set the update rate instead of using flush because of a ntcore bug
    // -> probably don't want to do this on a robot in competition
    NetworkTableInstance.getDefault().setUpdateRate(0.010);
  }

  @Override
  public void disabledInit() {
    System.out.println("Robot disabled");
    sDrive.tankDrive(0, 0);
  }

  @Override
  public void disabledPeriodic() {}

  @Override
  public void robotPeriodic() {
    // feedback for users, but not used by the control program
    SmartDashboard.putNumber("l_encoder_pos", leftEncoderPosition.get());
    SmartDashboard.putNumber("l_encoder_rate", leftEncoderRate.get());
    SmartDashboard.putNumber("r_encoder_pos", rightEncoderPosition.get());
    SmartDashboard.putNumber("r_encoder_rate", rightEncoderRate.get());
  }

  @Override
  public void teleopInit() {
    System.out.println("Robot in operator control mode");
  }

  @Override
  public void teleopPeriodic() {
    System.out.printf("%7.3f %7.3f %7.3f %7.3f %7.3f %7.3f %7.3f\n",
    leftEncoderPosition.get(),
    rightEncoderPosition.get(),
    leftEncoderRate.get(),
    rightEncoderRate.get(),
    sMotorLeftA.getMotorOutputVoltage(),
    sMotorRightA.getMotorOutputVoltage(),
    gyroAngleRadians.get());
    sDrive.arcadeDrive(-stick.getY()*.5, stick.getX()*.4);

    if(true == stick.getRawButton(1))
    {
      // Reset encoders
      sMotorLeftA.setSelectedSensorPosition(-0);
      sMotorRightA.setSelectedSensorPosition(-0);
    }
  }

  @Override
  public void autonomousInit() {
    System.out.println("Robot in autonomous mode");
  }

  @Override
  public void testPeriodic()
  {
    System.out.printf("%f %f %f %f \n",
    leftEncoderPosition.get(),rightEncoderPosition.get(),
    leftEncoderRate.get(),rightEncoderRate.get());
    sDrive.tankDrive(.4,.4);
  }

  @Override
  public void autonomousPeriodic() {

    // Retrieve values to send back before telling the motors to do something
    double now = Timer.getFPGATimestamp();

    double leftPosition = leftEncoderPosition.get();
    double leftRate = leftEncoderRate.get();

    double rightPosition = rightEncoderPosition.get();
    double rightRate = rightEncoderRate.get();

    double battery = RobotController.getBatteryVoltage();

    double leftMotorVolts = sMotorLeftA.getMotorOutputVoltage();
    double rightMotorVolts = -1.0*sMotorRightA.getMotorOutputVoltage();

    // Retrieve the commanded speed from NetworkTables
    double autospeed = autoSpeedEntry.getDouble(0);
    priorAutospeed = autospeed;

    // command motors to do things
    sDrive.tankDrive(
      (rotateEntry.getBoolean(false) ? -1 : 1) * autospeed, autospeed,
      false
    );

    // send telemetry data array back to NT
    numberArray[0] = now;
    numberArray[1] = battery;
    numberArray[2] = autospeed;
    numberArray[3] = leftMotorVolts;
    numberArray[4] = rightMotorVolts;
    numberArray[5] = leftPosition;
    numberArray[6] = rightPosition;
    numberArray[7] = leftRate;
    numberArray[8] = rightRate;
    numberArray[9] = gyroAngleRadians.get();

    telemetryEntry.setNumberArray(numberArray);
  }
}
