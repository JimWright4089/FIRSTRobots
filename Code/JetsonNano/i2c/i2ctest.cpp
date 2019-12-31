//----------------------------------------------------------------------------
//
//  $Workfile: i2ctest.cpp$
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

//----------------------------------------------------------------------------
//  Includes
//----------------------------------------------------------------------------
#include <stdio.h>
#include <stdint.h>
#include <sys/ioctl.h>
#include <iostream>
#include <unistd.h>
#include <fcntl.h>
#include <sstream>
#include <chrono>
#include <climits>
#include <cstdio>
#include <thread>
#include <linux/i2c-dev.h>
#include "JimsGarminLidarLiteV3.hpp"

using namespace std;

//----------------------------------------------------------------------------
//  File constants
//----------------------------------------------------------------------------
const uint8_t I2C_WAIT_TIME = 100;
const uint8_t mFridgeAddress = 0x49;  // i2C addresses
const uint8_t mFreezerAddress = 0x48;

const uint16_t gAddressL = 0x1440;
const uint16_t gAddressM = 0x2052;
const uint16_t gAddressR = 0x548C;

const uint8_t gI2CAddressL = 0x20;
const uint8_t gI2CAddressM = 0x22;
const uint8_t gI2CAddressR = 0x24;

//----------------------------------------------------------------------------
//  Local functions
//----------------------------------------------------------------------------
void InitI2C();
double ReadTempSensor(int filePointer);

//----------------------------------------------------------------------------
//  Global Variable
//----------------------------------------------------------------------------
int mFileFridge = 0;              // Fridge I2C file
int mFileFreezer = 0;             // Freezer I2C file
JimsGarminLidarLiteV3 gLiteL;
JimsGarminLidarLiteV3 gLiteM;
JimsGarminLidarLiteV3 gLiteR;

//----------------------------------------------------------------------------
//  Purpose:
//      Main entry point
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
int main(void)
{
  uint16_t count = 0;
  uint16_t error = 0;
  InitI2C();

  uint16_t goodLDist = 0;
  uint16_t goodMDist = 0;
  uint16_t goodRDist = 0;

  gLiteL.turnOnDebug();
  gLiteM.turnOnDebug();
  gLiteR.turnOnDebug();

  error = gLiteL.setI2CAddressToSerialNumber(gI2CAddressL, gAddressL, false);
  printf("error:%2x\n",error);
/*
  error = gLiteM.setI2CAddressToSerialNumber(gI2CAddressM, gAddressM, false);
  printf("error:%2x\n",error);
  error = gLiteR.setI2CAddressToSerialNumber(gI2CAddressR, gAddressR, false);
  printf("error:%2x\n",error);
*/
  error = gLiteL.configure(BALANCED_PERFORMANCE);
  printf("error:%2x\n",error);
/*
  error = gLiteM.configure(BALANCED_PERFORMANCE);
  printf("error:%2x\n",error);
  error = gLiteR.configure(BALANCED_PERFORMANCE);
  printf("error:%2x\n",error);

  error = gLiteL.distance(false);
  printf("distL:%4x\n",error);
  error = gLiteM.distance(false);
  printf("distM:%4x\n",error);
  error = gLiteR.distance(false);
  printf("distR:%4x\n",error);
*/
  gLiteL.turnOffDebug();
  gLiteM.turnOffDebug();
  gLiteR.turnOffDebug();
/*
  while(true)
  {
    if(0 == (count%3))
    {
      error = gLiteL.distance(false);
      if(error<gLiteL.ER_LAST_ERROR)
      {
        goodLDist = error;
      }
    }
    if(1 == (count%3))
    {
      error = gLiteL.distance(false);
      if(error<gLiteL.ER_LAST_ERROR)
      {
        goodLDist = error;
      }
    }
    if(2 == (count%3))
    {
      error = gLiteL.distance(false);
      if(error<gLiteL.ER_LAST_ERROR)
      {
        goodLDist = error;
      }
    }
    printf("dist:%4x %4x %4x\n",goodLDist,goodMDist,goodRDist);
    count++;
  }
*/
}

//----------------------------------------------------------------------------
//  Purpose:
//
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
double ReadTempSensor(int filePointer)
{
  uint8_t data[2];
  uint8_t length;

  // a 0x00 means ge tthe temperature
  data[0] = 0x00;

  //----- WRITE BYTES -----
  length = 1;
  if (write(filePointer, data, length) != length)
  {
    printf("Failed to write to the i2c bus.\n");
  }

  usleep(I2C_WAIT_TIME);

  //----- READ BYTES -----
  length = 2;
  if (read(filePointer, data, length) != length)
  {
    printf("Failed to read from the i2c bus.\n");
  }
  else
  {
    // The temperature is in a 12 bit int.
    short intTemp = data[0]<<8;
    intTemp += data[1];
    intTemp /=32;
    // move the decimal
    return ((double)intTemp)*0.0625;
  }
  return 0.0;
}

//----------------------------------------------------------------------------
//  Purpose:
//
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
void InitI2C()
{
  char *filename = (char*)"/dev/i2c-1";

  //----- OPEN THE I2C BUS -----
  if ((mFileFridge = open(filename, O_RDWR)) < 0)
  {
    printf("Failed to open the fridge i2c bus");
    return;
  }

  if (ioctl(mFileFridge, I2C_SLAVE, mFridgeAddress) < 0)
  {
    printf("Failed to acquire bus access and/or talk to fridge temp sensor.\n");
    return;
  }

  if ((mFileFreezer = open(filename, O_RDWR)) < 0)
  {
    printf("Failed to open the freezer i2c bus");
    return;
  }

  if (ioctl(mFileFreezer, I2C_SLAVE, mFreezerAddress) < 0)
  {
    printf("Failed to acquire bus access and/or talk to freezer temp sensor.\n");
    return;
  }
}


