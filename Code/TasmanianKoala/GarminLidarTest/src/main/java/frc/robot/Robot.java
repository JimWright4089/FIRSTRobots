/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystem.Arduino;
import frc.robot.subsystem.GarminLidarLiteV3;
import frc.robot.subsystem.LIDARLite;

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

  private GarminLidarLiteV3 mLiteL = new GarminLidarLiteV3();
  private GarminLidarLiteV3 mLiteM = new GarminLidarLiteV3();
  private GarminLidarLiteV3 mLiteR = new GarminLidarLiteV3();

  public static final int mSerialL = 0x1440;
  public static final int mSerialM = 0x2052;
  public static final int mSerialR = 0x548C;

  public static final int mAddressL = 0x20;
  public static final int mAddressM = 0x22;
  public static final int mAddressR = 0x24;

  private int mDistL = 0;
  private int mDistM = 0;
  private int mDistR = 0;

  private LIDARLite mLite;
  private Arduino mArduino;
  private int mCount = 0;
  private int mSensorCount = 0;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    mLite = new LIDARLite(Port.kOnboard);
    mArduino = new Arduino(Port.kOnboard);

    mLiteL.turnOnDebug();
    mLiteM.turnOnDebug();
    mLiteR.turnOnDebug();

    mLiteL.setI2CAddressToSerialNumber(mAddressL, mSerialL, true);
    mLiteM.setI2CAddressToSerialNumber(mAddressM, mSerialM, true);
    mLiteR.setI2CAddressToSerialNumber(mAddressR, mSerialR, true);

    mLiteL.configure(GarminLidarLiteV3.BALANCED_PERFORMANCE);
    mLiteM.configure(GarminLidarLiteV3.BALANCED_PERFORMANCE);
    mLiteR.configure(GarminLidarLiteV3.BALANCED_PERFORMANCE);

    mLiteL.turnOffDebug();
    mLiteM.turnOffDebug();
    mLiteR.turnOffDebug();
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
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);

  }

@Override
public void disabledInit() {
  super.disabledInit();
  mLite.stopMeasuring();
}

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    mCount++;
    if(mCount>5)
    {
      mSensorCount++;
      switch(mSensorCount%3)
      {
        case 0:
          mDistL = mLiteL.distance(false);
          break;
        case 1:
          mDistM = mLiteM.distance(false);
          break;
        case 2:
          mDistR = mLiteR.distance(false);
          break;
      }
      System.out.printf("dist=%d %d %d\n", mDistL,mDistM,mDistR);

      mCount = 0;
    }

    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    super.testInit();
    System.out.println("Make lite ");
    
    mLite.startMeasuring();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    mCount++;
    if(mCount>20)
    {
      int dist = mLite.getDistance();
      System.out.printf("dist:%4x\n",dist);

      if((dist<0)||(dist>8000))
      {
        mLite.reset();
      }

      mCount = 0;
    }
  }
}
