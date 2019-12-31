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
const uint8_t ALL_OFF      = 0x20;
const uint8_t SOLID_GREEN  = 0x30;
const uint8_t SOLID_WHITE  = 0x32;
const uint8_t SOLID_BLUE   = 0x34;
const uint8_t SOLID_YELLOW = 0x36;
const uint8_t SOLID_RED    = 0x38;
const uint8_t SPIN_GREEN   = 0x40;
const uint8_t SPIN_WHITE   = 0x42;
const uint8_t SPIN_BLUE    = 0x44;
const uint8_t SPIN_YELLOW  = 0x46;
const uint8_t SPIN_RED     = 0x48;

const uint8_t RING_ADDRESS = 0x30;
const uint16_t SLEEP_TIME  = 2000;

//----------------------------------------------------------------------------
//  Local functions
//----------------------------------------------------------------------------
void InitI2C();
void SendCommand(uint8_t command);

//----------------------------------------------------------------------------
//  Global Variable
//----------------------------------------------------------------------------
int mGlobalFilePointer = 0;
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
  InitI2C();

  while(true)
  {
    SendCommand(SOLID_GREEN);
    std::this_thread::sleep_for(std::chrono::milliseconds(SLEEP_TIME));
    SendCommand(SOLID_WHITE);
    std::this_thread::sleep_for(std::chrono::milliseconds(SLEEP_TIME));
    SendCommand(SOLID_BLUE);
    std::this_thread::sleep_for(std::chrono::milliseconds(SLEEP_TIME));
    SendCommand(SOLID_YELLOW);
    std::this_thread::sleep_for(std::chrono::milliseconds(SLEEP_TIME));
    SendCommand(SOLID_RED);
    std::this_thread::sleep_for(std::chrono::milliseconds(SLEEP_TIME));
    SendCommand(SPIN_GREEN);
    std::this_thread::sleep_for(std::chrono::milliseconds(SLEEP_TIME));
    SendCommand(SPIN_WHITE);
    std::this_thread::sleep_for(std::chrono::milliseconds(SLEEP_TIME));
    SendCommand(SPIN_BLUE);
    std::this_thread::sleep_for(std::chrono::milliseconds(SLEEP_TIME));
    SendCommand(SPIN_YELLOW);
    std::this_thread::sleep_for(std::chrono::milliseconds(SLEEP_TIME));
    SendCommand(SPIN_RED);
    std::this_thread::sleep_for(std::chrono::milliseconds(SLEEP_TIME));
    SendCommand(ALL_OFF);
    std::this_thread::sleep_for(std::chrono::milliseconds(SLEEP_TIME));
  }
}

//----------------------------------------------------------------------------
//  Purpose:
//
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
void SendCommand(uint8_t command)
{
  uint8_t data[2];
  uint8_t length=1;

  // a 0x00 means ge tthe temperature
  data[0] = command;

  //----- WRITE BYTES -----
  length = 1;
  if (write(mGlobalFilePointer, data, length) != length)
  {
    printf("Failed to write to the i2c bus.\n");
  }
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
  if ((mGlobalFilePointer = open(filename, O_RDWR)) < 0)
  {
    printf("Failed to open the fridge i2c bus");
    return;
  }

  if (ioctl(mGlobalFilePointer, I2C_SLAVE, RING_ADDRESS) < 0)
  {
    printf("Failed to acquire bus access and/or talk to fridge temp sensor.\n");
    return;
  }
}


