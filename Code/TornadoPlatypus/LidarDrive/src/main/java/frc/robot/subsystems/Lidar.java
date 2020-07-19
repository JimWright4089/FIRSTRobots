//----------------------------------------------------------------------------
//
//  $Workfile: Lidar.java$
//
//  $Revision: X$
//
//  Project:    Tornado Platypus
//
//                            Copyright (c) 2020
//                              James A Wright
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
package frc.robot.subsystems;

//----------------------------------------------------------------------------
//  Imports
//----------------------------------------------------------------------------
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.*;
import edu.wpi.first.hal.HALUtil;

//----------------------------------------------------------------------------
// Class Declarations
//----------------------------------------------------------------------------
//
// Class Name: Lidar
//
// Purpose:
//   Handles all the hardware and management of the drive base
//
//----------------------------------------------------------------------------
public class Lidar extends SubsystemBase 
{
  //----------------------------------------------------------------------------
  //  Class Attributes
  //----------------------------------------------------------------------------
  long              mTimeout = 170000;
  NetworkTableEntry m0degs;  
  NetworkTableEntry mNeg70degs;  
  NetworkTableEntry mNeg45degs;  
  NetworkTableEntry m45degs;  
  NetworkTableEntry m60degs;  
  NetworkTableEntry m55degs;  
  NetworkTableEntry m65degs;  

  static Lidar mInstance;

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Returns the singleton instance
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public static Lidar getInstance()
  {
    if(null == mInstance)
    {
      mInstance = new Lidar();
    }
    return mInstance;
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Contstructor
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  private Lidar() 
  {
      NetworkTableInstance inst = NetworkTableInstance.getDefault();
      m0degs = inst.getEntry("/Lidar/0");
      mNeg70degs = inst.getEntry("/Lidar/-70");
      mNeg45degs = inst.getEntry("/Lidar/-45");
      m45degs = inst.getEntry("/Lidar/45");
      m60degs = inst.getEntry("/Lidar/60");
      m55degs = inst.getEntry("/Lidar/55");
      m65degs = inst.getEntry("/Lidar/65");
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      This is called every frame
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  @Override
  public void periodic() 
  {
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Returns a distance in mm of a degree
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public double getNdeg70() 
  {
    return (mNeg70degs.getDouble(500));
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Returns a distance in mm of a degree
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public double getNdeg45() 
  {
    return (mNeg45degs.getDouble(500));
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Returns a distance in mm of a degree
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public double get0() 
  {
    return (m0degs.getDouble(500));
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Returns a distance in mm of a degree
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public double get45() 
  {
    return (m45degs.getDouble(500));
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Returns a distance in mm of a degree
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public double get60() 
  {
    return (m60degs.getDouble(500));
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Returns a distance in mm of a degree
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public double get55() 
  {
    return (m55degs.getDouble(500));
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Returns a distance in mm of a degree
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public double get65() 
  {
    return (m65degs.getDouble(500));
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Returns if the entry has not been updated in a while
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public boolean isStale(NetworkTableEntry entry)
  {
      if(HALUtil.getFPGATime()-entry.getLastChange()>mTimeout)
      {
          return true;
      }
      return false;
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Returns if the entry has not been updated in a while
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public boolean is5Stale()
  {
    return isStale(m55degs);  
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Returns if the entry has not been updated in a while
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public boolean is60Stale()
  {
    return isStale(m60degs);  
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //      Returns if the entry has not been updated in a while
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  public boolean is65Stale()
  {
    return isStale(m65degs);  
  }



}
