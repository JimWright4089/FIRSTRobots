package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.PowerDistribution;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.math.MathUsageId;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.util.*;

public class Robot extends TimedRobot {
  PowerDistribution mPDP = new PowerDistribution();
  CANSparkMax mMotor = new CANSparkMax(1, MotorType.kBrushless);
  double mPrevAmps = 0;
  boolean mMoved = false;
  Joystick mStick = new Joystick(0);
  double mSetPoint = 15.0;
  double mPrevError = 0;
  double mSumError = 0;
  double kP = 0.07;
  double kI = 0.0004;
  double kD = 0.04;
  int mCount = 0;

  @Override
  public void robotInit() {
    mMotor.getEncoder().setPositionConversionFactor(1.0);
    mMotor.getEncoder().setVelocityConversionFactor(1.0);
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
    mMotor.getEncoder().setPosition(0.0);
  }

  @Override
  public void teleopInit() {
    mMotor.getEncoder().setPosition(0.0);
  }

  @Override
  public void teleopPeriodic() {
    if(true == mStick.getRawButton(2))
    {
      mMotor.getEncoder().setPosition(0.0);
      mSumError += 0;
      mPrevError = 0;
    }
    else if(true == mStick.getRawButton(1))
    {
      double error = mSetPoint - mMotor.getEncoder().getPosition();
      double power = (error * kP) + ((mPrevError-error)*kD) + (mSumError*kI);
      mMotor.set(power);
  
      mSumError += error;
      mPrevError = error;
  
      mCount++;
      if(0==(mCount%3))
      {
        System.out.format("%8.2f %8.2f %8.2f %8.2f %8.2f %8.2f\n", 
        mSetPoint,
        mMotor.getEncoder().getPosition(),
        error,
        power,
        mSumError,
        mPDP.getCurrent(3));
      }
      double adj = mStick.getRawAxis(1)*.1;
      adj = MathUtil.applyDeadband(adj, 0.01);
      mSetPoint += (adj);
      mSetPoint = MathUtil.clamp(mSetPoint, 0, 45);
    }
    else
    {
      mMotor.set(mStick.getRawAxis(1)*-.1);
      System.out.format("%8.2f %8.2f %8.2f %8.2f %8.2f\n" , 
      mMotor.getEncoder().getPositionConversionFactor(),
      mMotor.getEncoder().getPosition(),
      mMotor.getEncoder().getVelocityConversionFactor(),
      mMotor.getEncoder().getVelocity(),
      mPDP.getCurrent(3));
    }
}

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {
    mMotor.getEncoder().setPosition(0.0);
  }

  @Override
  public void testPeriodic() {
    mMotor.set(.3);
    // System.out.format("%8.2f %8.2f %8.2f %8.2f %8.2f\n" , 
    // mMotor.getEncoder().getPositionConversionFactor(),
    // mMotor.getEncoder().getPosition(),
    // mMotor.getEncoder().getVelocityConversionFactor(),
    // mMotor.getEncoder().getVelocity(),
    // mPDP.getCurrent(14));
    System.out.format("%8.2f %8.2f %8.2f %8.2f %8.2f\n" , 
    mPDP.getCurrent(11),
    mPDP.getCurrent(12),
    mPDP.getCurrent(13),
    mPDP.getCurrent(14),
    mPDP.getCurrent(15));
  }

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
