//----------------------------------------------------------------------------
//
//  $Workfile: LEDRing$
//
//  $Revision: X$
//
//  Project:    CHS217
//
//                            Copyright (c) 2019
//                              James A Wright
//                            All Rights Reserved
//
//  Modification History:
//  $Log:
//  $
//
//----------------------------------------------------------------------------

#include <Wire.h>
//#include <Adafruit_NeoPixel.h>
//#include "Common.h"
//#include "JORSUtils.h"

#include <SoftwareSerial.h>
#include <Adafruit_NeoPixel.h>

typedef unsigned char uint8;

//----------------------------------------------------------------------------
//  Constants
//----------------------------------------------------------------------------
const long MAX_TIME       = 1000;
const uint8 MAX_LEDS      =   16;
const uint8 LEDPin        =   20;
const int  COLORReduction =   27;

const uint8 COLOR_BLUE    =  0;
const uint8 COLOR_GREEN   =  1;
const uint8 COLOR_RED     =  2;
const uint8 COLOR_YELLOW  =  3;
const uint8 COLOR_CYAN    =  4;
const uint8 COLOR_MAGENTA =  5;
const uint8 COLOR_WHITE   =  6;
const uint8 COLOR_BLACK   =  7;
const uint8 COLOR_BRIGHT_GREEN = 8;
const uint8 COLOR_ORANGE  =  9;
const uint8 MAX_COLORS    = 10;

const uint8 colorArray[] = 
{
  0,   0, 255,
  0, 128,   0,
255,   0,   0,
255, 255,   0,
  0, 255, 255,
255,   0, 255,
255, 255, 255,
  0,   0,   0,
  0, 255,   0,
255, 165,   0};

const int delayTime = 2000;
//----------------------------------------------------------------------------
//  Global Variables
//----------------------------------------------------------------------------
Adafruit_NeoPixel mStrip    = Adafruit_NeoPixel(MAX_LEDS, LEDPin, NEO_GRB + NEO_KHZ800);
uint8 mSpinColor = 0;

//----------------------------------------------------------------------------
//  Purpose:
//      Setup
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
void setup() 
{
  Serial.begin(115200);
  pinMode(LEDPin, OUTPUT);
  mStrip.begin();
  mStrip.show();
  IdlePatternSet();
}

//----------------------------------------------------------------------------
//  Purpose:
//      Idle Loop
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
void loopGreen() 
{
  ColorSet(COLOR_BRIGHT_GREEN);
}

void loop() 
{ 
  ColorSet(COLOR_WHITE);
}

void loopz() 
{ 
  SpinColor(COLOR_WHITE);
  delay(delayTime);
  SpinColor(COLOR_YELLOW);
  delay(delayTime);
  SpinColor(COLOR_GREEN);
  delay(delayTime);
  SpinColor(COLOR_BLUE);
  delay(delayTime);
  SpinColor(COLOR_RED);
  delay(delayTime);
  ColorSet(COLOR_WHITE);
  delay(delayTime);
  ColorSet(COLOR_YELLOW);
  delay(delayTime);
  ColorSet(COLOR_GREEN);
  delay(delayTime);
  ColorSet(COLOR_BLUE);
  delay(delayTime);
  ColorSet(COLOR_RED);
  delay(delayTime);
  ColorSet(COLOR_BLACK);
  delay(delayTime);
  
  delay(10);
}

//----------------------------------------------------------------------------
//  Purpose:
//      Spin The Color Ring
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
void SpinColor(int color)
{ 
  int red   = colorArray[(color*3)];
  int blue  = colorArray[(color*3)+1];
  int green = colorArray[(color*3)+2];
  
  for(int i=0;i<16;i++)
  {
    uint8 realLoc = (mSpinColor+i)%16;
    mStrip.setPixelColor(realLoc, mStrip.Color((uint8)red, (uint8)blue, (uint8)green));

    if(0 > (red-COLORReduction))
    {
      red = 0;
    }
    else
    {
      red-=COLORReduction;
    }
    if(0 > (blue-COLORReduction))
    {
      blue = 0;
    }
    else
    {
      blue-=COLORReduction;
    }
    
    if(0 > (green-COLORReduction))
    {
      green = 0;
    }
    else
    {
      green-=COLORReduction;
    }
  }
  mStrip.show();
  delay(25);
  mSpinColor--;
}

//****************************************************************************
//****************************************************************************
//****************************************************************************
//****************************************************************************
//      
//  LED Handler
//
//****************************************************************************
//****************************************************************************
//****************************************************************************
//****************************************************************************

//----------------------------------------------------------------------------
//  Purpose:
//      Return the combined color
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
uint32_t GetLEDColor(uint8_t colorIndex)
{
  if(colorIndex>=MAX_COLORS)
  {
    colorIndex = COLOR_BLACK; 
  }

  uint8 red   = colorArray[(colorIndex*3)];
  uint8 blue  = colorArray[(colorIndex*3)+1];
  uint8 green = colorArray[(colorIndex*3)+2];

  return(mStrip.Color(red, blue, green));
}

//----------------------------------------------------------------------------
//  Purpose:
//      
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
void ColorSet(uint8 team) 
{
  uint8 theColor = team;
  
  uint32_t color = GetLEDColor(theColor);
  
  for(uint8 i=0; i<MAX_LEDS; i++) 
  {
      mStrip.setPixelColor(i, color);
  }
  mStrip.show();
}

//----------------------------------------------------------------------------
//  Purpose:
//      Set the LEDs to the idle pattern 
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
void IdlePatternSet() 
{
  uint32_t color = GetLEDColor(COLOR_BLACK);
  uint8 i;
  
  for(i=0; i<4; i++) 
  {
      mStrip.setPixelColor(i, color);
  }
 
  color = GetLEDColor(COLOR_RED);
  for(i=4; i<8; i++) 
  {
      mStrip.setPixelColor(i, color);
  }

  color = GetLEDColor(COLOR_YELLOW);
  for(i=8; i<12; i++) 
  {
      mStrip.setPixelColor(i, color);
  }

  color = GetLEDColor(COLOR_WHITE);
  for(i=12; i<16; i++) 
  {
      mStrip.setPixelColor(i, color);
  }
  
  mStrip.show();
}
