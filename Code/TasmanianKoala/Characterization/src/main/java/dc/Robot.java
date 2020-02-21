/**
 * This is a very simple robot program that can be used to send telemetry to
 * the data_logger script to characterize your drivetrain. If you wish to use
 * your actual robot code, you only need to implement the simple logic in the
 * autonomousPeriodic function and change the NetworkTables update rate
 */

package dc;

import java.util.function.Supplier;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// WPI_Talon* imports are needed in case a user has a Pigeon on a Talon
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.CANCoder;

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

  static private double WHEEL_DIAMETER = 0.1526;
  static private double GEARING = 360;
  public static final int kLeftMotor1Port = 1;
  public static final int kLeftMotor2Port = 3;
  public static final int kRightMotor1Port = 2;
  public static final int kRightMotor2Port = 4;
  public static final int kLeftEncoderPort = 11;
  public static final int kRightEncoderPort = 12;
  public static final int kGyroPort = 10;


  Joystick stick;
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
        (1 / GEARING) * WHEEL_DIAMETER * Math.PI;

    leftEncoderPosition = ()
        -> sLeftEncoder.getPosition() * encoderConstant;
    leftEncoderRate = ()
        -> sLeftEncoder.getVelocity() * encoderConstant;

    rightEncoderPosition = ()
        -> sRightEncoder.getPosition() * encoderConstant;
    rightEncoderRate = ()
        -> sRightEncoder.getVelocity() * encoderConstant;

    // Reset encoders
    sLeftEncoder.setPosition(0);
    sRightEncoder.setPosition(0);

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
    sDrive.arcadeDrive(-stick.getY()*.6, stick.getX()*.6);
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
    sDrive.tankDrive(.2,.2);
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

    double leftMotorVolts = sMotorLeftA.getBusVoltage() * sMotorLeftA.getAppliedOutput();
    double rightMotorVolts = sMotorRightA.getBusVoltage() * sMotorRightA.getAppliedOutput();

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
