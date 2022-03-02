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
#include <SparkFun_Qwiic_Twist_Arduino_Library.h>
#include <SparkFun_Qwiic_Button.h>
#include <Adafruit_NeoPixel.h>
#ifdef __AVR__
 #include <avr/power.h> // Required for 16 MHz Adafruit Trinket
#endif

//----------------------------------------------------------------------------
//  Const
//----------------------------------------------------------------------------
#define LED_PIN    12
#define LED_COUNT 72

const int ledArray[] = {
  11,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10,
  23, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22,
  59, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 
  65, 66, 67, 68, 69, 70, 71, 60, 61, 62, 63, 64, 
  29, 30, 31, 32, 33, 34, 35, 24, 25, 26, 27, 28,
  41, 42, 43, 44, 45, 46, 47, 36, 37, 38, 39, 40,   
};

const int redColors[]   = { 0xff, 0x00, 0x00, 0xff, 0xff, 0x00};
const int blueColors[]  = { 0x00, 0xff, 0x00, 0x00, 0xff, 0xff};
const int greenColors[] = { 0x00, 0x00, 0xff, 0xff, 0x00, 0xff};


const int MAX_ENC =      6;
const int STATE_POT =    0;
const int STATE_ENC =    1;
const int MAX_VALUE =  127;
const int MIN_VALUE = -128;

//----------------------------------------------------------------------------
//  Globals
//----------------------------------------------------------------------------
QwiicButton buttonRed;
QwiicButton buttonGreen;
TWIST twist1;
TWIST twist2;
TWIST twist3;
TWIST twist4;
TWIST twist5;
TWIST twist6;
Adafruit_NeoPixel strip(LED_COUNT, LED_PIN, NEO_GRB + NEO_KHZ800);
int16_t encValue[] = {0, 0, 0, 0, 0, 0 };
int16_t encState[] = {STATE_POT, STATE_POT, STATE_POT, STATE_POT, STATE_POT, STATE_POT };
bool    buttonOld[] = {false, false, false, false, false, false};
//--------------------------------------------------------------------
// Purpose:
//     Enable pullups on all pins
//
// Notes:
//     None.
//--------------------------------------------------------------------
void setup() {
  Gamepad.begin();
  Serial.begin(115200);
  Serial.println("Qwiic button examples");
  Wire.begin(); //Join I2C bus

  if (buttonRed.begin(0x67) == false) {
    Serial.println("Device did not acknowledge! Freezing.");
    while (1);
  }
  if (buttonGreen.begin() == false) {
    Serial.println("Device did not acknowledge! Freezing.");
    while (1);
  }

  if(twist1.begin(Wire, 0x43) == false)
  {
    Serial.println("Twist1 does not appear to be connected. Please check wiring. Freezing...");
    while(1);
  }
  if(twist2.begin(Wire, 0x46) == false)
  {
    Serial.println("Twist2 does not appear to be connected. Please check wiring. Freezing...");
    while(1);
  }
  if(twist3.begin(Wire, 0x47) == false)
  {
    Serial.println("Twist3 does not appear to be connected. Please check wiring. Freezing...");
    while(1);
  }
  if(twist4.begin(Wire, 0x48) == false)
  {
    Serial.println("Twist4 does not appear to be connected. Please check wiring. Freezing...");
    while(1);
  }
  if(twist5.begin(Wire, 0x45) == false)
  {
    Serial.println("Twist5 does not appear to be connected. Please check wiring. Freezing...");
    while(1);
  }
  if(twist6.begin(Wire, 0x44) == false)
  {
    Serial.println("Twist6 does not appear to be connected. Please check wiring. Freezing...");
    while(1);
  }

#if defined(__AVR_ATtiny85__) && (F_CPU == 16000000)
  clock_prescale_set(clock_div_1);
#endif
  // END of Trinket-specific code.

  strip.begin();           // INITIALIZE NeoPixel strip object (REQUIRED)
  strip.show();            // Turn OFF all pixels ASAP
  strip.setBrightness(50); // Set BRIGHTNESS to about 1/5 (max = 255)
  delay(500);
}

//--------------------------------------------------------------------
// Purpose:
//     Read the pins and send then out as buttons.
//
// Notes:
//     Buttons begin at index 1.
//--------------------------------------------------------------------
void loop() {
  setButton(twist1.isPressed(),1);
  setButton(twist2.isPressed(),2);
  setButton(twist3.isPressed(),3);
  setButton(twist4.isPressed(),4);
  setButton(twist5.isPressed(),5);
  setButton(twist6.isPressed(),6);
  setButton(buttonRed.isPressed(),7);
  setButton(buttonGreen.isPressed(),8);

  strip.clear();
  for(int i=0;i<6;i++)
  {
    encValue[i] = getEncValue(i);

    if(STATE_POT == encState[i])
    {
      if(encValue[i] > MAX_VALUE)
      {
        encValue[i] = MAX_VALUE;
        setEncValue(i,encValue[i]);
      }

      if(encValue[i] < MIN_VALUE)
      {
        encValue[i] = MIN_VALUE;
        setEncValue(i,encValue[i]);
      }
      setLED(i, encValue[i]);

      if((true == buttonOld[i])&&(false == getButtonValue(i)))
      {
        encState[i] = STATE_ENC;
      }
      
    }
    else
    {
      if(encValue[i] > MAX_VALUE)
      {
        encValue[i] -= 256;
        setEncValue(i,encValue[i]);
      }

      if(encValue[i] < MIN_VALUE)
      {
        encValue[i] += 256;
        setEncValue(i,encValue[i]);
      }
      setEncLED(i, encValue[i]);
     
      if((true == buttonOld[i])&&(false == getButtonValue(i)))
      {
        encState[i] = STATE_POT;
      }
    }
    setGamePadValue(i,encValue[i]);
    buttonOld[i] = getButtonValue(i);
  }

  strip.show();
  Gamepad.write();
  delay(40);
}

//--------------------------------------------------------------------
// Purpose:
//     Read and set button
//
// Notes:
//     Buttons begin at index 1.
//--------------------------------------------------------------------
void setButton(bool theButton, int buttonNumber) {
  if(true == theButton)
  {
    Gamepad.press(buttonNumber);
  }
  else
  {
    Gamepad.release(buttonNumber);
  }
}

//--------------------------------------------------------------------
// Purpose:
//     Get Button Value
//
// Notes:
//     Buttons begin at index 1.
//--------------------------------------------------------------------
bool getButtonValue(int encNumber) {
  switch(encNumber)
  {
    case(1): return twist2.isPressed();
    case(2): return twist3.isPressed();
    case(3): return twist4.isPressed();
    case(4): return twist5.isPressed();
    case(5): return twist6.isPressed();
    default: return twist1.isPressed();
  }
}

//--------------------------------------------------------------------
// Purpose:
//     Get Enc Value
//
// Notes:
//     Buttons begin at index 1.
//--------------------------------------------------------------------
int16_t getEncValue(int encNumber) {
  switch(encNumber)
  {
    case(1): return twist2.getCount();
    case(2): return twist3.getCount();
    case(3): return twist4.getCount();
    case(4): return twist5.getCount();
    case(5): return twist6.getCount();
    default: return twist1.getCount();
  }
}

//--------------------------------------------------------------------
// Purpose:
//     Set Enc Value
//
// Notes:
//     Buttons begin at index 1.
//--------------------------------------------------------------------
void setEncValue(int encNumber, int16_t encValue) {
  switch(encNumber)
  {
    case(1): twist2.setCount(encValue);
            break;
    case(2): twist3.setCount(encValue);
            break;
    case(3): twist4.setCount(encValue);
            break;
    case(4): twist5.setCount(encValue);
            break;
    case(5): twist6.setCount(encValue);
            break;
    default: twist1.setCount(encValue);
            break;
  }
}

//--------------------------------------------------------------------
// Purpose:
//     Set Game Pad
//
// Notes:
//     Buttons begin at index 1.
//--------------------------------------------------------------------
void setGamePadValue(int encNumber, int8_t encValue) {
  switch(encNumber)
  {
    case(1): Gamepad.yAxis(encValue*255);
            break;
    case(2): Gamepad.zAxis(encValue);
            break;
    case(3): Gamepad.rxAxis(encValue*255);
            break;
    case(4): Gamepad.ryAxis(encValue*255);
            break;
    case(5): Gamepad.rzAxis(encValue);
            break;
    default: Gamepad.xAxis(encValue*255);
            break;
  }
}

//--------------------------------------------------------------------
// Purpose:
//     Set LED
//
// Notes:
//     Buttons begin at index 1.
//--------------------------------------------------------------------
void setLED(int encNumber, int8_t encValue) {
  int index = encNumber*12;
  int encTotalValue = encValue+128;
  int leds  = (encTotalValue) / 21;
  if(leds>11)
  {
    leds = 11;
  }
  int bright = (encTotalValue - (leds*21))*12;
  if(bright>255)
  {
    bright = 255;
  }
  int ind = 0;
  
  for(ind=0;ind<leds;ind++)
  {
    strip.setPixelColor(ledArray[index+ind], redColors[encNumber], blueColors[encNumber], greenColors[encNumber]);
  }

  switch(encNumber)
  {
    case 1:
      strip.setPixelColor(ledArray[index+leds], 0x00, bright, 0x00);
      break;
    case 2:
      strip.setPixelColor(ledArray[index+leds], 0x00, 0x00, bright);
      break;
    case 3:
      strip.setPixelColor(ledArray[index+leds], bright, 0x00, bright);
      break;
    case 4:
      strip.setPixelColor(ledArray[index+leds], bright, bright, 0x00);
      break;
    case 5:
      strip.setPixelColor(ledArray[index+leds], 0x00, bright, bright);
      break;
    default:
      strip.setPixelColor(ledArray[index+leds], bright, 0x00, 0x00);
      break;
  }
}

//--------------------------------------------------------------------
// Purpose:
//     Set LED
//
// Notes:
//     Buttons begin at index 1.
//--------------------------------------------------------------------
void setEncLED(int encNumber, int8_t encValue) {
  int index = encNumber*12;
  int encTotalValue = encValue+128;
  int leds  = (encTotalValue) / 21;
  if(leds>11)
  {
    leds = 11;
  }
  int prev = leds - 1;

  if(prev < 0)
  {
    prev = 11;
  }
  
  int bright = (encTotalValue - (leds*21))*12;
  if(bright>255)
  {
    bright = 255;
  }
  int invBright = 255 - bright;
 
  switch(encNumber)
  {
    case 1:
      strip.setPixelColor(ledArray[index+leds], 0x00, bright, 0x00);
      strip.setPixelColor(ledArray[index+prev], 0x00, invBright, 0x00);
      break;
    case 2:
      strip.setPixelColor(ledArray[index+leds], 0x00, 0x00, bright);
      strip.setPixelColor(ledArray[index+prev], 0x00, 0x00, invBright);
      break;
    case 3:
      strip.setPixelColor(ledArray[index+leds], bright, 0x00, bright);
      strip.setPixelColor(ledArray[index+prev], invBright, 0x00, invBright);
      break;
    case 4:
      strip.setPixelColor(ledArray[index+leds], bright, bright, 0x00);
      strip.setPixelColor(ledArray[index+prev], invBright, invBright, 0x00);
      break;
    case 5:
      strip.setPixelColor(ledArray[index+leds], 0x00, bright, bright);
      strip.setPixelColor(ledArray[index+prev], 0x00, invBright, invBright);
      break;
    default:
      strip.setPixelColor(ledArray[index+leds], bright, 0x00, 0x00);
      strip.setPixelColor(ledArray[index+prev], invBright, 0x00, 0x00);
      break;
  }
}
