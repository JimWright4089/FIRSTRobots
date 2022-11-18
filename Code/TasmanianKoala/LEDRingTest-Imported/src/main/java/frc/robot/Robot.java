//----------------------------------------------------------------------------
//
//  $Workfile: LEDRing.cpp$
//
//  $Revision: X$
//
//  Project:    Team Redacted
//
//                            Copyright (c) 2019
//                                Jim Wright
//                            All Rights Reserved
//
//  Modification History:
//  $Log:
//  $
//
//----------------------------------------------------------------------------

package frc.robot;

//----------------------------------------------------------------------------
//  Imports
//----------------------------------------------------------------------------
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

//----------------------------------------------------------------------------
//Class Declarations
//----------------------------------------------------------------------------
//
//Class Name: Robot
//
//Purpose:
//  The class that run the robot
//
//----------------------------------------------------------------------------
public class Robot extends TimedRobot 
{
  //----------------------------------------------------------------------------
  //  Class Atributes
  //----------------------------------------------------------------------------
  NetworkTableEntry mLEDRing;
  Joystick mJoystick = new Joystick(0);

//----------------------------------------------------------------------------
//  Purpose:
//   Gets everything ready to go
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
@Override
  public void robotInit() 
  {
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    mLEDRing = inst.getEntry("/LEDColor");
  
  }

//----------------------------------------------------------------------------
//  Purpose:
//   Run this method every frame
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
@Override
  public void robotPeriodic() 
  {
    if(false == DriverStation.isDisabled())
    {
      boolean spin = mJoystick.getRawButton(5);
      mLEDRing.setDouble((double)'O');

      if(true == mJoystick.getRawButton(1))
      {
        if(true == spin)
        {
          mLEDRing.setDouble((double)'g');
        }
        else
        {
          mLEDRing.setDouble((double)'G');
        }
      }

      if(true == mJoystick.getRawButton(2))
      {
        if(true == spin)
        {
          mLEDRing.setDouble((double)'r');
        }
        else
        {
          mLEDRing.setDouble((double)'R');
        }
      }
      
      if(true == mJoystick.getRawButton(3))
      {
        if(true == spin)
        {
          mLEDRing.setDouble((double)'b');
        }
        else
        {
          mLEDRing.setDouble((double)'B');
        }
      }

      if(true == mJoystick.getRawButton(4))
      {
        if(true == spin)
        {
          mLEDRing.setDouble((double)'y');
        }
        else
        {
          mLEDRing.setDouble((double)'Y');
        }
      }


    }
    else
    {
      if(Alliance.Blue == DriverStation.getAlliance())
      {
        mLEDRing.setDouble((double)'b');
      }
      else
      {
        mLEDRing.setDouble((double)'r');
      }
    }
  }
}
