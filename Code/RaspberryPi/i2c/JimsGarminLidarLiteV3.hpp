//----------------------------------------------------------------------------
//
//  $Workfile: JimsGarminLidarLiteV3.hpp$
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
#ifndef JimsGarminLidarLiteV3_h
#define JimsGarminLidarLiteV3_h

//----------------------------------------------------------------------------
// Includes
//----------------------------------------------------------------------------
#include <stdio.h>
#include <stdint.h>
#include <sys/ioctl.h>
#include <iostream>
#include <unistd.h>
#include <fcntl.h>
#include <sstream>
#include <linux/i2c-dev.h>

//----------------------------------------------------------------------------
//Class Declarations
//----------------------------------------------------------------------------
//
//Class Name: JimsGarminLidarLiteV3
//
//Purpose:
//  The class that interfaces with the Garmin Lidar Lite V3 both the standard
//  V3 as well as the V3HP.  The registers used are the same over both.
//
//  This also allows you to connect multiple units to I2C and use the 
//  serial number of each to set a unique address.
//
//----------------------------------------------------------------------------
class JimsGarminLidarLiteV3
{
  public:
      //----------------------------------------------------------------------------
      // Public Constants
      //----------------------------------------------------------------------------
      const uint16_t ER_NO_RESPONSE     = 0xFFFF;
      const uint16_t ER_TIMEOUT_EXPIRED = 0xFFFE;
      const uint16_t ER_ADDRESS_ODD     = 0xFFFD;
      const uint16_t ER_FILE_OPEN_ERROR = 0xFFFC;
      const uint16_t ER_NO_RESPONSE_READ= 0xFFFB;
      const uint16_t ER_LAST_ERROR      = 0xFFF0;
      const uint16_t OK_RETURNED_GOOD   = 0x0000;

      #define BALANCED_PERFORMANCE   0x00
      #define SHORT_RANGE_HIGH_SPEED 0x01
      #define DEFAULT_RANGE          0x02
      #define MAXIMUM_RANGE          0x03
      #define HIGH_SENSITIVITY_DETECTION 0x04
      #define LOW_SENSITIVITY_DETECTION  0x05
      
      // --------------------------------------------------------------------
      // Purpose:
      // Constructor
      //
      // Notes:
      // None.
      // --------------------------------------------------------------------
      JimsGarminLidarLiteV3();
      
      // --------------------------------------------------------------------
      // Purpose:
      // Turn the debugging prints on
      //
      // Notes:
      // None.
      // --------------------------------------------------------------------
      void turnOnDebug(void);
      
      // --------------------------------------------------------------------
      // Purpose:
      // Turn the debugging prints off
      //
      // Notes:
      // None.
      // --------------------------------------------------------------------
      void turnOffDebug(void);

      // --------------------------------------------------------------------
      // Purpose:
      // Return the serial number of a unit
      //
      // Notes:
      // This method assumes that only one Lidar is connected to the I2C bus
      // --------------------------------------------------------------------
      uint16_t getSerialNumber(void);

      // --------------------------------------------------------------------
      // Purpose:
      // Set the i2cAddress and serialNumber
      //
      // Notes:
      // Once this function returns clean, then the unit no longer listens on 0x62
      // --------------------------------------------------------------------
      uint16_t setI2CAddressToSerialNumber(uint8_t i2cAddress, uint16_t serialNumber, bool keepDefault);
      
      // --------------------------------------------------------------------
      // Purpose:
      // Configure the speed of getting the distance
      //
      // Notes:
      // None
      // --------------------------------------------------------------------
      uint16_t configure(int configuration);

      // --------------------------------------------------------------------
      // Purpose:
      // Returns the distance in cm.
      //
      // Notes:
      // none
      // --------------------------------------------------------------------
      uint16_t distance(bool biasCorrection);

  private:
      //----------------------------------------------------------------------------
      // Private Constants
      //----------------------------------------------------------------------------
      const uint8_t READ_TWO_BYTES = 0x80;
      const uint8_t ACQ_COMMAND    = 0x00;
      const uint8_t STATUS         = 0x01;
      const uint8_t SIG_COUNT_VAL  = 0x02;
      const uint8_t ACQ_CONFIG_REG = 0x04;
      const uint8_t FULL_DELAY_HIGH = 0x0f;
      const uint8_t FULL_DELAY_LOW = 0x10;
      const uint8_t UINT_ID_HIGH   = 0x16;
      const uint8_t UINT_ID_LOW    = 0x17;
      const uint8_t I2C_ID_HIGH    = 0x18;
      const uint8_t I2C_ID_LOW     = 0x19;
      const uint8_t I2C_SEC_ADDR   = 0x1A;
      const uint8_t THRESHOLD_BHYPASS = 0x1C;
      const uint8_t I2C_CONFIG     = 0x1E;
      
      const uint8_t ONLY_RESPOND_TO_ADDRESS = 0x08;
      const uint8_t RESPOND_TO_DEFAULT      = 0x00;
      const uint8_t MAX_ACQUISITONS         = 0xFF;
      const uint8_t MIDDLE_ACQUISITONS      = 0x80;
      const uint8_t LOW_ACQUISITONS         = 0x1D;
      const uint8_t QUICK_TERM_MEASURMENT   = 0x08;
      const uint8_t CLEAR_ACQ_CONFIG        = 0x00;
      const uint8_t DEFAULT_THRESHOLD       = 0x00;
      const uint8_t MIDDLE_THRESHOLD        = 0x80;
      const uint8_t HIGH_THRESHOLD          = 0xB0;
      const uint8_t WITHOUT_RECEIVER_BIAS   = 0x03;
      const uint8_t WITH_RECEIVER_BIAS      = 0x04;

      const uint8_t DEFAULT_ADDRESS = 0x62;

      const uint16_t TIMEOUT_COUNTER = 100;

      //----------------------------------------------------------------------------
      // Private Attributes
      //----------------------------------------------------------------------------
      uint16_t mSerialNumber = 0;
      uint8_t  mAddress = 0x62;
      bool     mDebug = false;
      int      mFilePointer = 0;

      // --------------------------------------------------------------------
      // Purpose:
      // Write data to the I2C port
      //
      // Notes:
      // None
      // --------------------------------------------------------------------
      uint16_t i2cWrite(uint8_t i2cAddress, uint8_t registerAddress, uint8_t value);

      // --------------------------------------------------------------------
      // Purpose:
      // Write data to the I2C port
      //
      // Notes:
      // None
      // --------------------------------------------------------------------
      uint16_t i2cRead(uint8_t i2cAddress, uint8_t registerAddress, uint8_t numOfBytes, 
                      uint8_t arrayToSave[2], bool monitorBusyFlag);
      
};

#endif
