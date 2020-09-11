//----------------------------------------------------------------------------
//
//  $Workfile: InTakeBallML.java$
//
//  $Revision: X$
//
//  Project:    Tasmanian Koala
//
//                            Copyright (c) 2020
//                                 Jim Wright
//                            All Rights Reserved
//
//  Modification History:
//  $Log:
//  $
//
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
//  Package
//----------------------------------------------------------------------------
package frc.robot.commands;

//----------------------------------------------------------------------------
//  Imports
//----------------------------------------------------------------------------
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.MLBall;
import frc.robot.utils.*;

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: InTakeBallML
//
// Purpose:
//  use the Tensorflow model and camera to find the ball
//
//----------------------------------------------------------------------------
public class InTakeBallML extends CommandBase {
  final double kPOffset = 0.0008;
  final double kIOffset = 0.0001;
  final double kPSize = 0.0000;
  final double kISize = 0.00000;
  final int kTimeoutSize = 20;

  //----------------------------------------------------------------------------
  //  Class Atributes
  //----------------------------------------------------------------------------
  DriveSubsystem mDrive = DriveSubsystem.getInstance();
  MLBall mMLBall = MLBall.getInstance();
  double mAngle = 0;
  double mSize = 800;
  double mErrorOffset = 0;
  double mSumErrorOffset = 0;
  double mErrorSize = 0;
  double mSumErrorSize = 0;
  int mTimeoutCount = 0;
  
  //----------------------------------------------------------------------------
  //  Purpose:
  //   Constructor
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public InTakeBallML(double targetAngleDegrees) {
    mAngle = targetAngleDegrees;
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //   Run the setup before the loop
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  @Override 
  public void  initialize() {
    mTimeoutCount = 0;
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //   run this code every loop
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  @Override 
  public void execute() {
    double turnPower = 0;
    double forwardPower = 0;

    if(0 == mMLBall.getArea())
    {
      mDrive.arcadeDrive(0, 0);
    }
    else
    {
      mErrorOffset =  mMLBall.getX() - mAngle;
      mErrorSize = mMLBall.getArea() - mSize;
    
      turnPower = (mErrorOffset * kPOffset)+(mSumErrorOffset * kIOffset);
      forwardPower = (mErrorSize * kPSize)+(mSumErrorSize * kISize);

      turnPower = DriveMath.Cap(turnPower, 1.0);
      forwardPower = DriveMath.Cap(forwardPower, 1.0);

      System.out.printf("turn:%f forward:%f IO:%f IS:%f\n", turnPower,forwardPower,mSumErrorOffset,mSumErrorSize);

      mDrive.arcadeDrive(-forwardPower, -turnPower);
      mSumErrorOffset += mErrorOffset;
      mSumErrorOffset = DriveMath.Cap(mSumErrorOffset,2000);
      mSumErrorSize += mErrorSize;
      mSumErrorSize = DriveMath.Cap(mSumErrorSize,10000);
    }
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //   is the command done
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  @Override
  public boolean isFinished() {
    if(0 == mMLBall.getArea())
    {
      mTimeoutCount++;
    }
    else
    {
      mTimeoutCount = 0;
    }


    if(mTimeoutCount > kTimeoutSize)
    {
      return true;
    }

    return ((Math.abs(mErrorOffset) < 5) && (mMLBall.getArea() > mSize));
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //   When the command ends this is what is called
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  @Override
  public void end(boolean interupted) {
    mDrive.arcadeDrive(0, 0);
  }
}