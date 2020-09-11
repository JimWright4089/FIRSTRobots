/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.Constants;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  private final Joystick driveJoystick = new Joystick(0);

  public static WPI_TalonSRX motorLeftA;
  public static WPI_TalonSRX motorLeftB;
  public static WPI_TalonSRX motorRightA;
  public static WPI_TalonSRX motorRightB;

  private NetworkTableEntry mNTError;
  private NetworkTableEntry mNTDerivative;
  private NetworkTableEntry mNTIntegral;
  private NetworkTableEntry mNTPower;
  private NetworkTableEntry mNTSetPoint;
  private NetworkTableEntry mNTCurPoint;

  // Constants for Velocity
  final double kP = 0.0001;
  final double kD = 0.000004;
  final double kI =  0.0000015;
  final double kICap = 50000;

  double prevError = 0;
  double integral = 0;
  double power = 0;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("FRCRobot");
    mNTError = table.getEntry("error");
    mNTDerivative = table.getEntry("derivative");
    mNTIntegral = table.getEntry("integral");
    mNTPower = table.getEntry("power");
    mNTSetPoint = table.getEntry("setPoint");
    mNTCurPoint = table.getEntry("curPoint");
  
    motorLeftA = new WPI_TalonSRX(Constants.constMotorLeftA);
    motorLeftB = new WPI_TalonSRX(Constants.constMotorLeftB);
    motorRightA = new WPI_TalonSRX(Constants.constMotorRightA);
    motorRightB = new WPI_TalonSRX(Constants.constMotorRightB);   
  }

  /**
   * This function is run once each time the robot enters autonomous mode.
   */
  @Override
  public void autonomousInit() {
    SetUpTalonsForTele();
    motorLeftA.setSelectedSensorPosition(0,0,20);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
//    VelocityPID();
    PositionPID();
  }

  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {
  }

  /**
   * This function is called periodically during teleoperated mode.
   */
  @Override
  public void teleopPeriodic() {
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }


  void VelocityPID()
  {
    double setPoint = driveJoystick.getRawAxis(2)*25;
    double curPoint = motorLeftA.getSelectedSensorVelocity(0);
    
    double error = setPoint - curPoint;
    double derivative = error - prevError;
        
    power = power + (error * kP) + 
        (derivative * kD) + 
        (integral * kI);

    power = Cap(power,1.0);
    
    prevError = error;
    integral += error;
    integral = Cap(integral,kICap);
    
    mNTError.setNumber(error);
    mNTDerivative.setNumber(derivative);
    mNTIntegral.setNumber(integral);
    mNTPower.setNumber(power);
    mNTSetPoint.setNumber(setPoint);
    mNTCurPoint.setNumber(curPoint);
 
    System.out.print(setPoint);
    System.out.print(" ");
    System.out.println(curPoint);

    //System.out.println(driveSRX.getSelectedSensorPosition(0));
    motorLeftA.set(power);
//    driveSRX.set(-1);
  }
	
	
  /*  
  Constants for Position
  final double kPPos = 0.004;
  final double kDPos = 0.000015;
  final double kIPos = 0.00003;
  final double kICapPos = 50000;

  Constants for Position overshoot
  final double kPPos = 0.002;
  final double kDPos = 0.000015;
  final double kIPos = 0.00006;
  final double kICapPos = 50000;
*/
  
  final double kPPos = 0.00004;
  final double kDPos = 0.00004;
  final double kIPos = 0.0000002;
  final double kICapPos = 500000;  
  
	void PositionPID()
	{
    double setPoint = driveJoystick.getRawAxis(2)*40000;
    double curPoint = motorLeftA.getSelectedSensorPosition(0);
    
    double error = setPoint - curPoint;
    double derivative = error - prevError;
        
    double power = (error * kPPos) + 
        (derivative * kDPos) + 
        (integral * kIPos);

    power = Cap(power,1.0);
    
    prevError = error;
    integral += error;
    integral = Cap(integral,kICapPos);
    
    mNTError.setNumber(error);
    mNTDerivative.setNumber(derivative);
    mNTIntegral.setNumber(integral);
    mNTPower.setNumber(power);
    mNTSetPoint.setNumber(setPoint);
    mNTCurPoint.setNumber(curPoint);

    System.out.print(setPoint);
    System.out.print(" ");
    System.out.println(curPoint);
    //System.out.println(driveSRX.getSelectedSensorPosition(0));
    motorLeftA.set(power);	  
	}
	
	
  // --------------------------------------------------------------------
  // Purpose:
  // Trim the top and bottom off of a number
  //
  // Notes:
  // None.
  // --------------------------------------------------------------------
  static public double Cap(double value, double peak) {
    if (value < -peak) {
      return -peak;
    }

    if (value > +peak) {
      return +peak;
    }
    return value;
  }

  public static void SetUpTalonsForTele()
  {
    SetUpTalonForTele(motorLeftA);
    SetUpTalonForTele(motorRightA);
    motorLeftB.set(ControlMode.Follower, Constants.constMotorLeftA);
    motorRightB.set(ControlMode.Follower, Constants.constMotorRightA);
    motorRightA.setInverted(true);
    motorRightB.setInverted(true);
  }

  private static void SetUpTalonForTele(WPI_TalonSRX talon)
  {
    talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.kTimeoutMs);
    talon.setSensorPhase(true);

    /* set the peak and nominal outputs, 12V means full */
    talon.configNominalOutputForward(0, Constants.kTimeoutMs);
    talon.configNominalOutputReverse(0, Constants.kTimeoutMs);
    talon.configPeakOutputForward(1, Constants.kTimeoutMs);
    talon.configPeakOutputReverse(-1, Constants.kTimeoutMs);

    talon.setSafetyEnabled(true);
    talon.setExpiration(Constants.kTimeoutMs);
  }
}
