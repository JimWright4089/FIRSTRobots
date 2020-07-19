package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.networktables.*;
import edu.wpi.first.hal.HALUtil;
import frc.utils.JimsNetworkTableEntry;

public class Robot extends TimedRobot {
  NetworkTableEntry m0degs; 

  @Override
  public void robotInit() {
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    m0degs = inst.getEntry("/Lidar/0");
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
    System.out.format("0:%8.2f t:%9d s:%9d d:%9d st:%b\n", 
      m0degs.getDouble(-1),
      m0degs.getLastChange(),
      HALUtil.getFPGATime(),
      HALUtil.getFPGATime()-m0degs.getLastChange(),
      JimsNetworkTableEntry.isState(m0degs));
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void testInit() {
  }
}
