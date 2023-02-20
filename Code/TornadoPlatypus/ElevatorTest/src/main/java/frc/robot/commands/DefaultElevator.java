package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.Constants;
import frc.robot.Constants.ElevatorConstants;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.util.*;

public class DefaultElevator extends CommandBase {
  private final DriveSubsystem mDrive;
  private final BooleanSupplier mTop;
  private final BooleanSupplier mMiddle;
  private final BooleanSupplier mBottom;
  private final BooleanSupplier mUp;
  private final BooleanSupplier mDown;
  private double mTargetPos = 1000.0;
  private int mCount = 0;
  private double mPrevError = 0;
  private double mSumError = 0;

  public DefaultElevator(DriveSubsystem subsystem,
      BooleanSupplier top,
      BooleanSupplier middle,
      BooleanSupplier bottom,
      BooleanSupplier up,
      BooleanSupplier down) {
    mDrive = subsystem;
    mTop = top;
    mMiddle = middle;
    mBottom = bottom;
    mUp = up;
    mDown = down;
    addRequirements(mDrive);
  }

  @Override
  public void initialize()
  {
    mDrive.resetEncoders();
  }

  @Override
  public void execute() {
    if(true == mTop.getAsBoolean())
    {
      mTargetPos = ElevatorConstants.kTopPos;
    }
    else
    {
      if(true == mMiddle.getAsBoolean())
      {
        mTargetPos = ElevatorConstants.kMiddlePos;
      }
      else
      {
        if(true == mBottom.getAsBoolean())
        {
          mTargetPos = ElevatorConstants.kBottomPos;
        }
      }
    }

    if(true == mUp.getAsBoolean())
    {
      mTargetPos += ElevatorConstants.kSetpointAdjust;
    }

    if(true == mDown.getAsBoolean())
    {
      mTargetPos -= ElevatorConstants.kSetpointAdjust;
    }


    double curPos = mDrive.getLeftEncoderPosition();

    double error = mTargetPos - curPos;
    double div = mPrevError - error;
    double power = (error * ElevatorConstants.kP)+(div * ElevatorConstants.kD)+(mSumError * ElevatorConstants.kI);

    mPrevError = error;
    mSumError += error;
  
    mSumError = MathUtil.clamp(mSumError, ElevatorConstants.kIMin, ElevatorConstants.kIMax);

    mDrive.tankDrive(power, 0);

    mCount++;
    if(0 == (mCount % 3))
    {
      System.out.printf("TP:%8.1f CP:%8.1f E:%13.3f D:%13.3f SE:%15.3f P:%15.3f\n", mTargetPos, curPos, error, div, mSumError, power);
    }
  }

  public void setTargetPos(double targetPos)
  {
    mTargetPos = targetPos;
  }
}