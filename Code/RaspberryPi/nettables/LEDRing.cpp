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

#include "ntcore.h"
#include "stdio.h"

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

const uint8_t COMMAND_SOLID_GREEN  = 'G';
const uint8_t COMMAND_SOLID_WHITE  = 'W';
const uint8_t COMMAND_SOLID_RED    = 'R';
const uint8_t COMMAND_SOLID_YELLOW = 'Y';
const uint8_t COMMAND_SOLID_BLUE   = 'B';
const uint8_t COMMAND_SPIN_BLUE    = 'b';
const uint8_t COMMAND_SPIN_GREEN   = 'g';
const uint8_t COMMAND_SPIN_WHITE   = 'w';
const uint8_t COMMAND_SPIN_RED     = 'r';
const uint8_t COMMAND_SPIN_YELLOW  = 'y';
const uint8_t COMMAND_ALL_OFF      = 'O';

const uint8_t RING_ADDRESS = 0x30;
const uint16_t SLEEP_TIME  = 20;

//----------------------------------------------------------------------------
//  Local functions
//----------------------------------------------------------------------------
void InitI2C();
void SendCommand(uint8_t command);

//----------------------------------------------------------------------------
//  Global Variable
//----------------------------------------------------------------------------
int mGlobalFilePointer = 0;
int mCurrentCommand = COMMAND_ALL_OFF;

//----------------------------------------------------------------------------
//  Purpose:
//   Main Entry Point
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
int main() 
{
  auto inst = nt::GetDefaultInstance();
  InitI2C();

  nt::StartClient(inst, "10.40.89.2",1735);
  std::this_thread::sleep_for(std::chrono::seconds(2));
  auto LEDColorEntry = nt::GetEntry(inst, "/LEDColor");

  while(true)
  {
    auto LEDColorVal = nt::GetEntryValue(LEDColorEntry);
    if (LEDColorVal && LEDColorVal->IsDouble())
    {
      mCurrentCommand = (int)LEDColorVal->GetDouble();
    }

    switch(mCurrentCommand)
    {
      case(COMMAND_ALL_OFF):
        SendCommand(ALL_OFF);
	break;
      case(COMMAND_SOLID_GREEN):
        SendCommand(SOLID_GREEN);
	break;
      case(COMMAND_SOLID_WHITE):
        SendCommand(SOLID_WHITE);
	break;
      case(COMMAND_SOLID_BLUE):
        SendCommand(SOLID_BLUE);
	break;
      case(COMMAND_SOLID_RED):
        SendCommand(SOLID_RED);
	break;
      case(COMMAND_SOLID_YELLOW):
        SendCommand(SOLID_YELLOW);
	break;
      case(COMMAND_SPIN_GREEN):
        SendCommand(SPIN_GREEN);
	break;
      case(COMMAND_SPIN_WHITE):
        SendCommand(SPIN_WHITE);
	break;
      case(COMMAND_SPIN_BLUE):
        SendCommand(SPIN_BLUE);
	break;
      case(COMMAND_SPIN_RED):
        SendCommand(SPIN_RED);
	break;
      case(COMMAND_SPIN_YELLOW):
        SendCommand(SPIN_YELLOW);
	break;
    }
    std::this_thread::sleep_for(std::chrono::milliseconds(SLEEP_TIME));
  }
}

//----------------------------------------------------------------------------
//  Purpose:
//   Send a command to the LED ring
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
//    Init the I2C port
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

