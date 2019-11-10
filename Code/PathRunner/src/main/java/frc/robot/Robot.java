/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Notifier;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.sensors.PigeonIMU;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private static final int k_ticks_per_rev = 2048;
  private static final double k_wheel_diameter = 6.0 / 12.0;
  private static final double k_max_velocity = 10;

  private Notifier m_follower_notifier;

  private static final String k_path_name = "SimpleForward";

  public static WPI_TalonSRX motorLeftA;
  public static WPI_TalonSRX motorLeftB;
  public static WPI_TalonSRX motorRightA;
  public static WPI_TalonSRX motorRightB;
  public static PigeonIMU pigeonIMU;

  private EncoderFollower m_left_follower;
  private EncoderFollower m_right_follower;
  
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    motorLeftA = new WPI_TalonSRX(Constants.constMotorLeftA);
    motorLeftB = new WPI_TalonSRX(Constants.constMotorLeftB);
    motorRightA = new WPI_TalonSRX(Constants.constMotorRightA);
    motorRightB = new WPI_TalonSRX(Constants.constMotorRightB);

    pigeonIMU = new PigeonIMU(motorRightB);
    pigeonIMU.setFusedHeading(0.0, 30);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    SetUpTalonsForTele();

    try
    {
      System.out.println("Start of try");
      System.out.println(k_path_name + ".Left");
      System.out.println(k_path_name + ".Right");
      System.out.println(PathfinderFRC.DEFAULT_JERK);
      Trajectory left_trajectory = PathfinderFRC.getTrajectory(k_path_name + ".left");
      Trajectory right_trajectory = PathfinderFRC.getTrajectory(k_path_name + ".right");
      m_left_follower = new EncoderFollower(left_trajectory);
      m_right_follower = new EncoderFollower(right_trajectory);
      System.out.println("After Init");
   
      m_left_follower.configureEncoder(motorLeftA.getSelectedSensorPosition(0), k_ticks_per_rev, k_wheel_diameter);
      // You must tune the PID values on the following line!
      m_left_follower.configurePIDVA(1.0, 0.0, 0.0, 1 / k_max_velocity, 0);
  
      m_right_follower.configureEncoder(motorRightA.getSelectedSensorPosition(0), k_ticks_per_rev, k_wheel_diameter);
      // You must tune the PID values on the following line!
      m_right_follower.configurePIDVA(1.0, 0.0, 0.0, 1 / k_max_velocity, 0);
      
      m_follower_notifier = new Notifier(this::followPath);
      m_follower_notifier.startPeriodic(left_trajectory.get(0).dt);
    }
    catch(Exception e)
    {
      System.console().printf(e.toString());
    }
  }

  private void followPath() {
    if (m_left_follower.isFinished() || m_right_follower.isFinished()) {
      m_follower_notifier.stop();
    } else {

      PigeonIMU.FusionStatus fusionStatus = new PigeonIMU.FusionStatus();
      double[] xyz_dps = new double[3];
      pigeonIMU.getRawGyro(xyz_dps);
      pigeonIMU.getFusedHeading(fusionStatus);
  
      double left_speed = m_left_follower.calculate(motorLeftA.getSelectedSensorPosition(0));
      double right_speed = m_right_follower.calculate(motorRightA.getSelectedSensorPosition(0));
      double heading = fusionStatus.heading;
      double desired_heading = Pathfinder.r2d(m_left_follower.getHeading());
      double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
      double turn =  0.8 * (-1.0/80.0) * heading_difference;
      
      System.out.print(left_speed);
      System.out.print(" ");
      System.out.print(right_speed);
      System.out.print(" ");
      System.out.print(heading);
      System.out.print(" ");
      System.out.print(desired_heading);
      System.out.print(" ");
      System.out.println(turn);

      motorLeftA.set(left_speed + turn);
      motorRightA.set(right_speed - turn);
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  /**
   * This function is called periodically during operator control.
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
