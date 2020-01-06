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

const uint8 ALL_OFF      = 0x20;
const uint8 SOLID_GREEN  = 0x30;
const uint8 SOLID_WHITE  = 0x32;
const uint8 SOLID_BLUE   = 0x34;
const uint8 SOLID_YELLOW = 0x36;
const uint8 SOLID_RED    = 0x38;
const uint8 SPIN_GREEN   = 0x40;
const uint8 SPIN_WHITE   = 0x42;
const uint8 SPIN_BLUE    = 0x44;
const uint8 SPIN_YELLOW  = 0x46;
const uint8 SPIN_RED     = 0x48;

//----------------------------------------------------------------------------
//  Global Variables
//----------------------------------------------------------------------------
Adafruit_NeoPixel mStrip    = Adafruit_NeoPixel(MAX_LEDS, LEDPin, NEO_GRB + NEO_KHZ800);
uint8 mSpinColor = 0;
uint8 mColor = ALL_OFF;

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
  Wire.begin(0x30);             // join i2c bus with address #0x30
  Wire.onReceive(receiveEvent); // register event
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
  switch(mColor)
  {
    case(ALL_OFF):
      ColorSet(COLOR_BLACK);
      break;
    case(SOLID_GREEN):
      ColorSet(COLOR_BRIGHT_GREEN);
      break;
    case(SOLID_WHITE):
      ColorSet(COLOR_WHITE);
      break;
    case(SOLID_BLUE):
      ColorSet(COLOR_BLUE);
      break;
    case(SOLID_RED):
      ColorSet(COLOR_RED);
      break;
    case(SOLID_YELLOW):
      ColorSet(COLOR_YELLOW);
      break;
    case(SPIN_GREEN):
      SpinColor(COLOR_BRIGHT_GREEN);
      break;
    case(SPIN_WHITE):
      SpinColor(COLOR_WHITE);
      break;
    case(SPIN_BLUE):
      SpinColor(COLOR_BLUE);
      break;
    case(SPIN_RED):
      SpinColor(COLOR_RED);
      break;
    case(SPIN_YELLOW):
      SpinColor(COLOR_YELLOW);
      break;
  }
  
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

//----------------------------------------------------------------------------
//  Purpose:
//      Read from the I2C
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
void receiveEvent(int howMany) 
{
  while (0 < Wire.available()) 
  { 
    mColor = Wire.read(); 
  }
}
