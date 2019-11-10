package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.*;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap
{
  public static WPI_TalonSRX motorleftA;
  public static WPI_TalonSRX motorleftB;
  public static WPI_TalonSRX motorRightA;
  public static WPI_TalonSRX motorRightB;
  public static PigeonIMU pigeonIMU;
  public static PowerDistributionPanel distributionPanel;

  public static boolean isAutoFinished;

  public static void init()
  {
    motorleftA = new WPI_TalonSRX(Constants.constMotorleftA);
    motorleftB = new WPI_TalonSRX(Constants.constMotorleftB);
    motorRightA = new WPI_TalonSRX(Constants.constMotorRightA);
    motorRightB = new WPI_TalonSRX(Constants.constMotorRightB);

    pigeonIMU = new PigeonIMU(motorRightB);
    pigeonIMU.setFusedHeading(0.0, 30);

    distributionPanel = new PowerDistributionPanel(18);
  }

  public static void SetUpTalonsForTele()
  {
    SetUpTalonForTele(motorleftA);
    SetUpTalonForTele(motorRightA);
    motorleftB.set(ControlMode.Follower, Constants.constMotorleftA);
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
    SetUpTalonForAuto(motorleftA);
    SetUpTalonForAuto(motorRightA);
    motorleftB.set(ControlMode.Follower, Constants.constMotorleftA);
    motorRightB.set(ControlMode.Follower, Constants.constMotorRightA);
    motorRightA.setInverted(true);
    motorRightB.setInverted(true);
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
