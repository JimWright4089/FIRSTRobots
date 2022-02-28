//----------------------------------------------------------------------------
//
//  $Workfile: ButtonPanel
//
//  $Revision: X$
//
//  Project:    Steath Robotics
//
//                            Copyright (c) 2022
//                               James A Wright
//                            All Rights Reserved
//
//  Modification History:
//  $Log:
//  $
//
//  Notes:
//     The Button panel for the 2022 driverstation
//
//----------------------------------------------------------------------------

//----------------------------------------------------------------------------
//  Includes
//----------------------------------------------------------------------------
#include "HID-Project.h"

//----------------------------------------------------------------------------
//  File Constants
//----------------------------------------------------------------------------
const int MAX_PINS = 12;

//----------------------------------------------------------------------------
//  Globals
//----------------------------------------------------------------------------
int thepins[] {5, 4, 3, 2, 6, 7, 8, 9, 15, 14, 16, 10};

//--------------------------------------------------------------------
// Purpose:
//     Enable pullups on all pins
//
// Notes:
//     None.
//--------------------------------------------------------------------
void setup() {
  // Sends a clean report to the host. This is important on any Arduino type.
  Gamepad.begin();

  for(int i=0;i<MAX_PINS;i++)
  {
    pinMode(thepins[i], INPUT_PULLUP);
  }
}

//--------------------------------------------------------------------
// Purpose:
//     Read the pins and send then out as buttons.
//
// Notes:
//     Buttons begin at index 1.
//--------------------------------------------------------------------
void loop() {
    for(int i=0;i<MAX_PINS;i++)
    {
      int sensorVal = digitalRead(thepins[i]);
      if(0 == sensorVal)
      {
        Gamepad.press(i+1);
      }
      else
      {
        Gamepad.release(i+1);
      }
    }
   
    // This writes the report to the host.
    Gamepad.write();

    delay(30);
}
