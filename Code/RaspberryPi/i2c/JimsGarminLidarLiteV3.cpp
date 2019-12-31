//----------------------------------------------------------------------------
//
//  $Workfile: JimsGarminLidarLiteV3.cpp$
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

//----------------------------------------------------------------------------
// Includes
//----------------------------------------------------------------------------
#include <chrono>
#include <climits>
#include <cstdio>
#include <thread>
#include "JimsGarminLidarLiteV3.hpp"

//--------------------------------------------------------------------
// See header file for method details
//--------------------------------------------------------------------
JimsGarminLidarLiteV3::JimsGarminLidarLiteV3()
{
  char *filename = (char*)"/dev/i2c-1";

  //----- OPEN THE I2C BUS -----
  if ((mFilePointer = open(filename, O_RDWR)) < 0)
  {
    printf("Failed to open the fridge i2c bus");
    return;
  }
}

//--------------------------------------------------------------------
// See header file for method details
//--------------------------------------------------------------------
void JimsGarminLidarLiteV3::turnOffDebug(void)
{
  mDebug = false;
}

//--------------------------------------------------------------------
// See header file for method details
//--------------------------------------------------------------------
void JimsGarminLidarLiteV3::turnOnDebug(void)
{
  mDebug = true;
}

//--------------------------------------------------------------------
// See header file for method details
//--------------------------------------------------------------------
uint16_t JimsGarminLidarLiteV3::getSerialNumber(void)
{
  uint8_t dataBytes[2];

  // read the serial number
  uint16_t error = i2cRead( DEFAULT_ADDRESS, (UINT_ID_HIGH | READ_TWO_BYTES), 2, dataBytes, true);

  // Bail if there is an error
  if(error > ER_LAST_ERROR)
  {
    return error;
  }

  mSerialNumber = (dataBytes[0]<<8)+dataBytes[1];

  return mSerialNumber;
}

//--------------------------------------------------------------------
// See header file for method details
//--------------------------------------------------------------------
uint16_t JimsGarminLidarLiteV3::setI2CAddressToSerialNumber(uint8_t i2cAddress, uint16_t serialNumber, bool keepDefault)
{
  if(0 != (i2cAddress%2))
  {
    return ER_ADDRESS_ODD;
  }

  mSerialNumber = serialNumber;
  
  uint16_t error = i2cWrite(DEFAULT_ADDRESS, I2C_ID_HIGH, (mSerialNumber>>8)&0xFF);
  // Bail if there is an error
  if(error > ER_LAST_ERROR)
  {
     mAddress = i2cAddress;
     return error;
  }
  
  error = i2cWrite(DEFAULT_ADDRESS, I2C_ID_LOW,  mSerialNumber&0xFF);

  // Write the new I2C device address to registers
  error = i2cWrite(DEFAULT_ADDRESS, I2C_SEC_ADDR, mAddress);

  if(true == keepDefault)
  {
    error = i2cWrite(DEFAULT_ADDRESS, I2C_CONFIG, RESPOND_TO_DEFAULT); 
    mAddress = i2cAddress;
    return error;
  }
  
  // Remove the default address and use only the new one
  error = i2cWrite(DEFAULT_ADDRESS, I2C_CONFIG, ONLY_RESPOND_TO_ADDRESS);
  mAddress = i2cAddress;
  return error;
}

//--------------------------------------------------------------------
// See header file for method details
//--------------------------------------------------------------------
uint16_t JimsGarminLidarLiteV3::configure(int configuration)
{
  uint16_t returnValue = OK_RETURNED_GOOD;

  switch (configuration)
  {
    case BALANCED_PERFORMANCE: // Default mode, balanced performance
      returnValue = i2cWrite(mAddress,SIG_COUNT_VAL,    MIDDLE_ACQUISITONS);    // Default
      i2cWrite(mAddress,ACQ_CONFIG_REG,   QUICK_TERM_MEASURMENT); // Default
      i2cWrite(mAddress,THRESHOLD_BHYPASS,DEFAULT_THRESHOLD);     // Default
    break;

    case SHORT_RANGE_HIGH_SPEED: // Short range, high speed
      returnValue = i2cWrite(mAddress,SIG_COUNT_VAL,    LOW_ACQUISITONS);
      i2cWrite(mAddress,ACQ_CONFIG_REG,   QUICK_TERM_MEASURMENT); // Default
      i2cWrite(mAddress,THRESHOLD_BHYPASS,DEFAULT_THRESHOLD);     // Default
    break;

    case DEFAULT_RANGE: // Default range, higher speed short range
      returnValue = i2cWrite(mAddress,SIG_COUNT_VAL,    MIDDLE_ACQUISITONS); // Default
      i2cWrite(mAddress,ACQ_CONFIG_REG,   CLEAR_ACQ_CONFIG);
      i2cWrite(mAddress,THRESHOLD_BHYPASS,DEFAULT_THRESHOLD);  // Default
    break;

    case MAXIMUM_RANGE: // Maximum range
      returnValue = i2cWrite(mAddress,SIG_COUNT_VAL,    MAX_ACQUISITONS);
      i2cWrite(mAddress,ACQ_CONFIG_REG,   QUICK_TERM_MEASURMENT); // Default
      i2cWrite(mAddress,THRESHOLD_BHYPASS,DEFAULT_THRESHOLD);     // Default
    break;

    case HIGH_SENSITIVITY_DETECTION: // High sensitivity detection, high erroneous measurements
      returnValue = i2cWrite(mAddress,SIG_COUNT_VAL,    MIDDLE_ACQUISITONS);    // Default
      i2cWrite(mAddress,ACQ_CONFIG_REG,   QUICK_TERM_MEASURMENT); // Default
      i2cWrite(mAddress,THRESHOLD_BHYPASS,MIDDLE_THRESHOLD);
    break;

    case LOW_SENSITIVITY_DETECTION: // Low sensitivity detection, low erroneous measurements
      returnValue = i2cWrite(mAddress,SIG_COUNT_VAL,    MIDDLE_ACQUISITONS);    // Default
      i2cWrite(mAddress,ACQ_CONFIG_REG,   QUICK_TERM_MEASURMENT); // Default
      i2cWrite(mAddress,THRESHOLD_BHYPASS,HIGH_THRESHOLD);
    break;
  }
  return returnValue;
} 

//--------------------------------------------------------------------
// See header file for method details
//--------------------------------------------------------------------
uint16_t JimsGarminLidarLiteV3::distance(bool biasCorrection)
{
  if(true == biasCorrection)
  {
    // Take acquisition & correlation processing with receiver bias correction
    i2cWrite(mAddress,ACQ_COMMAND,WITH_RECEIVER_BIAS);
  }
  else
  {
    // Take acquisition & correlation processing without receiver bias correction
    i2cWrite(mAddress,ACQ_COMMAND,WITHOUT_RECEIVER_BIAS);
  }
  
  // Array to store high and low bytes of distance
  uint8_t distanceArray[2];
  // Read two bytes from register 0x8f (autoincrement for reading 0x0f and 0x10)
  uint16_t error = i2cRead(mAddress,(FULL_DELAY_HIGH | READ_TWO_BYTES),2,distanceArray,true);

  // Bail if there is an error
  if(error > ER_LAST_ERROR)
  {
    return error;
  }

  return((distanceArray[0] << 8) + distanceArray[1]);
}

//--------------------------------------------------------------------
// See header file for method details
//--------------------------------------------------------------------
uint16_t JimsGarminLidarLiteV3::i2cRead(uint8_t i2cAddress, uint8_t registerAddress, uint8_t numOfBytes, 
                uint8_t arrayToSave[2], bool monitorBusyFlag)
{
  std::this_thread::sleep_for(std::chrono::milliseconds(2));
  uint16_t busyCounter = 0; // busyCounter counts number of times busy flag is checked, for timeout
  uint8_t data[2];
  uint8_t length = 0;
  uint8_t error = 0;

  if (ioctl(mFilePointer, I2C_SLAVE, i2cAddress) < 0)
  {
    if(true == mDebug)
    {
      printf("Failed to acquire bus access and/or talk to fridge temp sensor.\n");
    }
    return ER_FILE_OPEN_ERROR;
  }

  while(true == monitorBusyFlag) // Loop until device is not busy
  {
    // Read status register to check busy flag
    data[0] = STATUS;
    length = 1;

    //----- WRITE BYTES -----
    error = write(mFilePointer, data, length);
    if (error != length)   
    {
      if(true == mDebug)
      {
        printf("No responce from status\n");
      }
      return ER_NO_RESPONSE;
    }

    data[0] = STATUS;
    length = 1;
    error = read(mFilePointer, data, length);
    if (error != length)
    {
      if(true == mDebug)
      {
        printf("No responce from status\n");
      }
      return ER_NO_RESPONSE;
    }

    if(0 == (data[0]&0x01))
    {
      break;
    }

    busyCounter++; // Increment busyCounter for timeout

    // Handle timeout condition, exit while loop and goto bailout
    if(busyCounter > TIMEOUT_COUNTER)
    {
      if(true == mDebug)
      {
        printf("Timeout expired\n");
      }
      return ER_TIMEOUT_EXPIRED;
    }
  }

  // The unit is not busy or we did not care
  data[0] = registerAddress;
  length = 1;

  //----- WRITE BYTES -----
  if (write(mFilePointer, data, length) != length)   
  {
    if(true == mDebug)
    {
      printf("No responce from command\n");
    }
    return ER_NO_RESPONSE;
  }

  if (read(mFilePointer, arrayToSave, numOfBytes) != numOfBytes)
  {
    if(true == mDebug)
    {
      printf("No responce from read\n");
    }
    return ER_NO_RESPONSE_READ;
  }

  if(true == mDebug)
  {
    printf("read=%02x %02x %02x %02x\n", mAddress, registerAddress, arrayToSave[0],arrayToSave[1]);
  }
  return OK_RETURNED_GOOD;
}

//--------------------------------------------------------------------
// See header file for method details
//--------------------------------------------------------------------
uint16_t JimsGarminLidarLiteV3::i2cWrite(uint8_t i2cAddress, uint8_t registerAddress, uint8_t value)
{
  std::this_thread::sleep_for(std::chrono::milliseconds(2));
  uint8_t data[2];
  uint8_t length = 0;
  if(true == mDebug)
  {
    printf("write=%02x %02x %02x\n", i2cAddress, registerAddress, value);
  }

  if (ioctl(mFilePointer, I2C_SLAVE, i2cAddress) < 0)
  {
    if(true == mDebug)
    {
      printf("Failed to acquire bus access.\n");
    }
    return ER_FILE_OPEN_ERROR;
  }

  data[0] = registerAddress;
  data[1] = value;
  length = 2;

  if (write(mFilePointer, data, length) != length)   
  {
    if(true == mDebug)
    {
      printf("No responce from command\n");
    }
    return ER_NO_RESPONSE;
  }

  return OK_RETURNED_GOOD;
}
