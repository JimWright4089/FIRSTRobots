//----------------------------------------------------------------------------
//
//  $Workfile: SliderPanel
//
//  $Revision: X$
//
//  Project:    Steath Robotics
//
//                            Copyright (c) 2023
//                               James A Wright
//                            All Rights Reserved
//
//  Modification History:
//  $Log:
//  $
//
//  Notes:
//     The Slider panel for the 2023 driverstation
//
//----------------------------------------------------------------------------

//----------------------------------------------------------------------------
//  Includes
//----------------------------------------------------------------------------
#include <Wire.h>
#include <Adafruit_GFX.h>
#include "Adafruit_LEDBackpack.h"
#include "Adafruit_seesaw.h"
#include <seesaw_neopixel.h>
#include "HID-Project.h"
#include <Adafruit_SleepyDog.h>

//----------------------------------------------------------------------------
//  Constants
//----------------------------------------------------------------------------
#define  ANALOGIN   18
const int MAX_ITEMS = 6;

//----------------------------------------------------------------------------
//  LED Matrix (Use the global Array not these)
//----------------------------------------------------------------------------
Adafruit_7segment matrix0 = Adafruit_7segment();
Adafruit_7segment matrix1 = Adafruit_7segment();
Adafruit_7segment matrix2 = Adafruit_7segment();
Adafruit_7segment matrix3 = Adafruit_7segment();
Adafruit_7segment matrix4 = Adafruit_7segment();
Adafruit_7segment matrix5 = Adafruit_7segment();

//----------------------------------------------------------------------------
//  Slider Sensors (Use the global Array not these)
//----------------------------------------------------------------------------
Adafruit_seesaw seesaw0;
Adafruit_seesaw seesaw1;
Adafruit_seesaw seesaw2;
Adafruit_seesaw seesaw3;
Adafruit_seesaw seesaw4;
Adafruit_seesaw seesaw5;

//----------------------------------------------------------------------------
//  Globals
//----------------------------------------------------------------------------
Adafruit_7segment* gMatrixArray[MAX_ITEMS];
Adafruit_seesaw*   gSeesawArray[MAX_ITEMS];
int                gValue[MAX_ITEMS];

//--------------------------------------------------------------------
// Purpose:
//     Set up the board
//
// Notes:
//     
//--------------------------------------------------------------------
void setup() {
#ifndef __AVR_ATtiny85__
  Serial.begin(115200);
  Serial.println("7 Segment Backpack Test");
#endif

  // Setup the matrixs
  matrix0.begin(0x70);
  matrix1.begin(0x71);
  matrix2.begin(0x72);
  matrix3.begin(0x73);
  matrix4.begin(0x74);
  matrix5.begin(0x75);

  // Set up the sliders
  seesaw0.begin(0x30);
  seesaw1.begin(0x31);
  seesaw2.begin(0x32);
  seesaw3.begin(0x33);
  seesaw4.begin(0x34);
  seesaw5.begin(0x35);

  // Build the matrix
  gMatrixArray[0] = &matrix0;
  gMatrixArray[1] = &matrix1;
  gMatrixArray[2] = &matrix2;
  gMatrixArray[3] = &matrix3;
  gMatrixArray[4] = &matrix4;
  gMatrixArray[5] = &matrix5;

  gSeesawArray[0] = &seesaw0;
  gSeesawArray[1] = &seesaw1;
  gSeesawArray[2] = &seesaw2;
  gSeesawArray[3] = &seesaw3;
  gSeesawArray[4] = &seesaw4;
  gSeesawArray[5] = &seesaw5;

  // Start gamepad and watchdog
  Gamepad.begin();
  // Watch dog, I think there is a memory leak in the display library
  Watchdog.enable(200);
}

//--------------------------------------------------------------------
// Purpose:
//     Idle Loop
//
// Notes:
//     
//--------------------------------------------------------------------
void loop() {
  int value = 0;
  Watchdog.reset();  // Pet the dog

  for(int i=0;i<MAX_ITEMS;i++)
  {
    gValue[i] = gSeesawArray[i]->analogRead(ANALOGIN);
    setGamePadValue(i, gValue[i]);

    // Convert value to the display value
    value = (gValue[i]/4) - 0x80;
    gMatrixArray[i]->print(value, DEC);
    gMatrixArray[i]->writeDisplay();
  }

  Gamepad.write();
  delay(100);
}

//--------------------------------------------------------------------
// Purpose:
//     Set Game Pad
//
// Notes:
//     Buttons begin at index 1.
//--------------------------------------------------------------------
void setGamePadValue(int encNumber, int encValue) {
  switch(encNumber)
  {
    case(1): Gamepad.yAxis(bigValue(encValue));
            break;
    case(2): Gamepad.zAxis(littleValue(encValue));
            break;
    case(3): Gamepad.rxAxis(bigValue(encValue));
            break;
    case(4): Gamepad.ryAxis(bigValue(encValue));
            break;
    case(5): Gamepad.rzAxis(littleValue(encValue));
            break;
    default: Gamepad.xAxis(bigValue(encValue));
            Serial.print(encValue);
            Serial.print(" ");
            Serial.println(bigValue(encValue));
            break;
  }
}

//--------------------------------------------------------------------
// Purpose:
//     Convert the value to the value for the Xs and Ys
//
// Notes:
//     
//--------------------------------------------------------------------
int bigValue(int value)
{
  int newValue = value - 0x200;
  newValue *= 64;
  return newValue;
}

//--------------------------------------------------------------------
// Purpose:
//     Convert the value to the value for the Zs
//
// Notes:
//     
//--------------------------------------------------------------------
int littleValue(int value)
{
  int newValue = value - 0x200;
  newValue /= 4;
  return newValue;
}

