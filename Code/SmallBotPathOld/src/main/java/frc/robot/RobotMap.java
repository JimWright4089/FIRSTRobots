package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.*;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Notifier;
import jaci.pathfinder.followers.EncoderFollower;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap
{
  public static WPI_TalonSRX sMotorLeftA;
  public static WPI_TalonSRX sMotorLeftB;
  public static WPI_TalonSRX sMotorRightA;
  public static WPI_TalonSRX sMotorRightB;
  public static PigeonIMU sPigeonIMU;
  public static PowerDistributionPanel sDistributionPanel;

  public static boolean isAutoFinished;

  public static Notifier sFollowerNotifier;
  public static EncoderFollower sLeftFollower;
  public static EncoderFollower sRightFollower;

  public static void init()
  {
    sMotorLeftA = new WPI_TalonSRX(Constants.constMotorLeftA);
    sMotorLeftB = new WPI_TalonSRX(Constants.constMotorLeftB);
    sMotorRightA = new WPI_TalonSRX(Constants.constMotorRightA);
    sMotorRightB = new WPI_TalonSRX(Constants.constMotorRightB);

    sPigeonIMU = new PigeonIMU(sMotorRightB);
    sPigeonIMU.setFusedHeading(0.0, 30);

    sDistributionPanel = new PowerDistributionPanel(18);
  }

  public static void SetUpTalonsForTele()
  {
    SetUpTalonForTele(sMotorLeftA);
    SetUpTalonForTele(sMotorRightA);
    sMotorLeftB.set(ControlMode.Follower, Constants.constMotorLeftA);
    sMotorRightB.set(ControlMode.Follower, Constants.constMotorRightA);
    sMotorRightA.setInverted(true);
    sMotorRightB.setInverted(true);
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

    /* set closed loop gains in slot0 */
    talon.config_kF(Constants.kPIDLoopIdx, 0.34, Constants.kTimeoutMs);
    talon.config_kP(Constants.kPIDLoopIdx, 0.2, Constants.kTimeoutMs);
    talon.config_kI(Constants.kPIDLoopIdx, 0, Constants.kTimeoutMs);
    talon.config_kD(Constants.kPIDLoopIdx, 0, Constants.kTimeoutMs);

    talon.setSafetyEnabled(true);
    talon.setExpiration(Constants.kTimeoutMs);
  }
  public static void SetUpTalonsForAuto()
  {
    SetUpTalonForAuto(sMotorLeftA);
    SetUpTalonForAuto(sMotorRightA);
    sMotorLeftB.set(ControlMode.Follower, Constants.constMotorLeftA);
    sMotorRightB.set(ControlMode.Follower, Constants.constMotorRightA);
    sMotorRightA.setInverted(true);
    sMotorRightB.setInverted(true);
  }
  
  private static void SetUpTalonForAuto(WPI_TalonSRX talon) {
    talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    talon.setSensorPhase(true); /* keep sensor and motor in phase */
    talon.configNeutralDeadband(Constants.kNeutralDeadband, Constants.kTimeoutMs);

    talon.config_kF(0, 0.076, Constants.kTimeoutMs);
    talon.config_kP(0, 2.000, Constants.kTimeoutMs);
    talon.config_kI(0, 0.0, Constants.kTimeoutMs);
    talon.config_kD(0,20.0, Constants.kTimeoutMs);

    //talon.configMotionProfileTrajectoryPeriod(20, Constants.kTimeoutMs); //Our profile uses 10 ms timing
    /* status 10 provides the trajectory target for motion profile AND motion magic */
    //talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 20, Constants.kTimeoutMs); 
  }  
}
