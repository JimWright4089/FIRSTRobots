//----------------------------------------------------------------------------
//
//  $Workfile: GarminLidarLiteV3.java$
//
//  $Revision: X$
//
//  Project:    Team Redacted Ri3D 2020
//
//                            Copyright (c) 2019
//                             James A. Wright
//                            All Rights Reserved
//
//  Modification History:
//  $Log:
//  $
//
//----------------------------------------------------------------------------
package frc.robot.subsystem;

//----------------------------------------------------------------------------
// Imports
//----------------------------------------------------------------------------
import edu.wpi.first.hal.I2CJNI;
import edu.wpi.first.wpilibj.I2C.Port;
import java.nio.ByteBuffer;
import edu.wpi.first.wpilibj.Timer;

//----------------------------------------------------------------------------
//Class Declarations
//----------------------------------------------------------------------------
//
//Class Name: GarminLidarLiteV3
//
//Purpose:
//  The class that interfaces with the Garmin Lidar Lite V3 both the standard
//  V3 as well as the V3HP.  The registers used are the same over both.
//
//  This also allows you to connect multiple units to I2C and use the 
//  serial number of each to set a unique address.
//
//----------------------------------------------------------------------------
public class GarminLidarLiteV3
{
      //----------------------------------------------------------------------------
      // Public Constants
      //----------------------------------------------------------------------------
      public static final int ER_NO_RESPONSE     = 0xFFFF;
      public static final int ER_TIMEOUT_EXPIRED = 0xFFFE;
      public static final int ER_ADDRESS_ODD     = 0xFFFD;
      public static final int ER_LAST_ERROR      = 0xFFF0;
      public static final int OK_RETURNED_GOOD   = 0x0000;

      public static final int BALANCED_PERFORMANCE   = 0x00;
      public static final int SHORT_RANGE_HIGH_SPEED = 0x01;
      public static final int DEFAULT_RANGE          = 0x02;
      public static final int MAXIMUM_RANGE          = 0x03;
      public static final int HIGH_SENSITIVITY_DETECTION = 0x04;
      public static final int LOW_SENSITIVITY_DETECTION  = 0x05;

      //----------------------------------------------------------------------------
      // Private Constants
      //----------------------------------------------------------------------------
      private static final int READ_TWO_BYTES = 0x80;
      private static final int ACQ_COMMAND    = 0x00;
      private static final int STATUS         = 0x01;
      private static final int SIG_COUNT_VAL  = 0x02;
      private static final int ACQ_CONFIG_REG = 0x04;
      private static final int FULL_DELAY_HIGH = 0x0f;
//      private static final int FULL_DELAY_LOW = 0x10;
      private static final int UINT_ID_HIGH   = 0x16;
//      private static final int UINT_ID_LOW    = 0x17;
      private static final int I2C_ID_HIGH    = 0x18;
      private static final int I2C_ID_LOW     = 0x19;
      private static final int I2C_SEC_ADDR   = 0x1A;
      private static final int THRESHOLD_BHYPASS = 0x1C;
      private static final int I2C_CONFIG     = 0x1E;
      
      private static final int ONLY_RESPOND_TO_ADDRESS = 0x08;
      private static final int RESPOND_TO_DEFAULT      = 0x00;
      private static final int MAX_ACQUISITONS         = 0xFF;
      private static final int MIDDLE_ACQUISITONS      = 0x80;
      private static final int LOW_ACQUISITONS         = 0x1D;
      private static final int QUICK_TERM_MEASURMENT   = 0x08;
      private static final int CLEAR_ACQ_CONFIG        = 0x00;
      private static final int DEFAULT_THRESHOLD       = 0x00;
      private static final int MIDDLE_THRESHOLD        = 0x80;
      private static final int HIGH_THRESHOLD          = 0xB0;
      private static final int WITHOUT_RECEIVER_BIAS   = 0x03;
      private static final int WITH_RECEIVER_BIAS      = 0x04;

      private static final byte DEFAULT_ADDRESS = 0x62;

      private static final int TIMEOUT_COUNTER = 3;      

      //----------------------------------------------------------------------------
      // Private Attributes
      //----------------------------------------------------------------------------
      private int mSerialNumber = 0;
      private byte mAddress = DEFAULT_ADDRESS;
      private boolean mDebug = false;
      private ByteBuffer mBuffer = ByteBuffer.allocateDirect(2);
      private int mPort = 0;

      // --------------------------------------------------------------------
      // Purpose:
      // Constructor
      //
      // Notes:
      // None.
      // --------------------------------------------------------------------
      public GarminLidarLiteV3()
      {
        mPort = Port.kOnboard.value;
        I2CJNI.i2CInitialize(mPort);
      }
      
      // --------------------------------------------------------------------
      // Purpose:
      // Turn the debugging prints on
      //
      // Notes:
      // None.
      // --------------------------------------------------------------------
      public void turnOnDebug()
      {
        mDebug = true;
      }
      
      // --------------------------------------------------------------------
      // Purpose:
      // Turn the debugging prints off
      //
      // Notes:
      // None.
      // --------------------------------------------------------------------
      public void turnOffDebug()
      {
        mDebug = false;
      }

      // --------------------------------------------------------------------
      // Purpose:
      // Return the serial number of a unit
      //
      // Notes:
      // This method assumes that only one Lidar is connected to the I2C bus
      // --------------------------------------------------------------------
      public int getSerialNumber()
      {
        // read the serial number
        int error = read ( (UINT_ID_HIGH | READ_TWO_BYTES), false);
      
        // Bail if there is an error
        if(error > ER_LAST_ERROR)
        {
          return error;
        }
      
        mSerialNumber = mBuffer.getShort(0);
      
        return mSerialNumber;
      }

      // --------------------------------------------------------------------
      // Purpose:
      // Set the i2cAddress and serialNumber
      //
      // Notes:
      // Once this function returns clean, then the unit no longer listens on 0x62
      // --------------------------------------------------------------------
      public int setI2CAddressToSerialNumber(int i2cAddress, int serialNumber, boolean keepDefault)
      {
        int error = 0;

        if(0 != (i2cAddress%2))
        {
          return ER_ADDRESS_ODD;
        }
      
        mSerialNumber = serialNumber;
        
        error = write(I2C_ID_HIGH, (mSerialNumber>>8)&0xFF);
        // Bail if there is an error
        if(error > ER_LAST_ERROR)
        {
          return error;
        }
        
        write(I2C_ID_LOW,  mSerialNumber&0xFF);
      
        // Write the new I2C device address to registers
        write(I2C_SEC_ADDR, i2cAddress);
      
        if(true == keepDefault)
        {
          error = write(I2C_CONFIG, RESPOND_TO_DEFAULT); 
          mAddress = (byte)i2cAddress;
          return error;
        }
        
        // Remove the default address and use only the new one
        error = write(I2C_CONFIG, ONLY_RESPOND_TO_ADDRESS); 
        mAddress = (byte)i2cAddress;
        return error;
      }
      
      // --------------------------------------------------------------------
      // Purpose:
      // Configure the speed of getting the distance
      //
      // Notes:
      // None
      // --------------------------------------------------------------------
      public int configure(int configuration)
      {
        int returnValue = OK_RETURNED_GOOD;

        switch (configuration)
        {
          case BALANCED_PERFORMANCE: // Default mode, balanced performance
            returnValue = write(SIG_COUNT_VAL,    MIDDLE_ACQUISITONS);    // Default
            write(ACQ_CONFIG_REG,   QUICK_TERM_MEASURMENT); // Default
            write(THRESHOLD_BHYPASS,DEFAULT_THRESHOLD);     // Default
          break;
      
          case SHORT_RANGE_HIGH_SPEED: // Short range, high speed
            returnValue = write(SIG_COUNT_VAL,    LOW_ACQUISITONS);
            write(ACQ_CONFIG_REG,   QUICK_TERM_MEASURMENT); // Default
            write(THRESHOLD_BHYPASS,DEFAULT_THRESHOLD);     // Default
          break;
      
          case DEFAULT_RANGE: // Default range, higher speed short range
            returnValue = write(SIG_COUNT_VAL,    MIDDLE_ACQUISITONS); // Default
            write(ACQ_CONFIG_REG,   CLEAR_ACQ_CONFIG);
            write(THRESHOLD_BHYPASS,DEFAULT_THRESHOLD);  // Default
          break;
      
          case MAXIMUM_RANGE: // Maximum range
            returnValue = write(SIG_COUNT_VAL,    MAX_ACQUISITONS);
            write(ACQ_CONFIG_REG,   QUICK_TERM_MEASURMENT); // Default
            write(THRESHOLD_BHYPASS,DEFAULT_THRESHOLD);     // Default
          break;
      
          case HIGH_SENSITIVITY_DETECTION: // High sensitivity detection, high erroneous measurements
            returnValue = write(SIG_COUNT_VAL,    MIDDLE_ACQUISITONS);    // Default
            write(ACQ_CONFIG_REG,   QUICK_TERM_MEASURMENT); // Default
            write(THRESHOLD_BHYPASS,MIDDLE_THRESHOLD);
          break;
      
          case LOW_SENSITIVITY_DETECTION: // Low sensitivity detection, low erroneous measurements
            returnValue = write(SIG_COUNT_VAL,    MIDDLE_ACQUISITONS);    // Default
            write(ACQ_CONFIG_REG,   QUICK_TERM_MEASURMENT); // Default
            write(THRESHOLD_BHYPASS,HIGH_THRESHOLD);
          break;
        }
        return returnValue;
      }

      // --------------------------------------------------------------------
      // Purpose:
      // Returns the distance in cm.
      //
      // Notes:
      // none
      // --------------------------------------------------------------------
      public int distance(boolean biasCorrection)
      {
        if(true == biasCorrection)
        {
          // Take acquisition & correlation processing with receiver bias correction
          write(ACQ_COMMAND,WITH_RECEIVER_BIAS);
        }
        else
        {
          // Take acquisition & correlation processing without receiver bias correction
          write(ACQ_COMMAND,WITHOUT_RECEIVER_BIAS);
        }
        
        // Read two bytes from register 0x8f (autoincrement for reading 0x0f and 0x10)
        int error = read((FULL_DELAY_HIGH | READ_TWO_BYTES),false);
      
        // Bail if there is an error

        if(error > ER_LAST_ERROR)
        {
          return error;
        }
      
        return mBuffer.getShort(0);
      }

      // --------------------------------------------------------------------
      // Purpose:
      // Write data to the I2C port
      //
      // Notes:
      // None
      // --------------------------------------------------------------------
      int write(int registerAddress, int value)
      {
        if(true == mDebug)
        {
          System.out.printf("write=%02x %02x %02x\n", mAddress, registerAddress, value);
        }

        mBuffer.put(0,(byte)registerAddress);
        mBuffer.put(1,(byte)value);
        int error = I2CJNI.i2CWrite(mPort, mAddress, mBuffer, (byte)2);

        if(1 != error)
        {
          if(true == mDebug)
          {
            System.out.printf("No responce from write command error:%d\n",error);
          }
          return ER_NO_RESPONSE;
        }
      
        Timer.delay(0.001);
        return OK_RETURNED_GOOD;
      }

      // --------------------------------------------------------------------
      // Purpose:
      // Write data to the I2C port
      //
      // Notes:
      // None
      // --------------------------------------------------------------------
      int read(int registerAddress, boolean monitorBusyFlag)
      {
        int error = 0;
        int busyCounter = 0;

        while(true == monitorBusyFlag) // Loop until device is not busy
        {
          // Read status register to check busy flag
          mBuffer.put(0,(byte)STATUS);
          error = I2CJNI.i2CWrite(mPort, mAddress, mBuffer, (byte)1);

          // A nack means the device is not responding, report the error over serial
          if(1 != error)
          {
            if(true == mDebug)
            {
              System.out.printf("No responce from read status write error:%d\n",error);
            }
            return ER_NO_RESPONSE;
          }

          error = I2CJNI.i2CRead(mPort, mAddress, mBuffer, (byte)1);
          if(1 != error)
          {
            if(true == mDebug)
            {
              System.out.printf("No responce from read status error:%d\n",error);
            }
            return ER_NO_RESPONSE;
          }
          
          if(0 == (mBuffer.get(0)&0x01))
          {
            break;
          }

          busyCounter++;
          // Handle timeout condition, exit while loop and goto bailout
          if(busyCounter > TIMEOUT_COUNTER)
          {
            if(true == mDebug)
            {
              System.out.printf("Timeout expired\n");
            }
            return ER_TIMEOUT_EXPIRED;
          }
          
        }
      
        mBuffer.put(0,(byte)registerAddress);
        error = I2CJNI.i2CWrite(mPort, mAddress, mBuffer, (byte)1);
      // The unit is not busy or we did not care
        error = I2CJNI.i2CRead(mPort, mAddress, mBuffer, (byte)2);
        error = 1;
        // A nack means the device is not responding, report the error over serial
        if(1 != error)
        {
          if(true == mDebug)
          {
            System.out.printf("No responce from read command, error=%d\n",error);
          }
          return ER_NO_RESPONSE;
        }
      
        if(true == mDebug)
        {
            System.out.printf("read=%02x %02x %04x\n", mAddress, registerAddress,mBuffer.getShort(0));
        }
        
        return OK_RETURNED_GOOD;
      }
}