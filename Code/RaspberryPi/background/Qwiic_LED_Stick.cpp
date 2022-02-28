/*
  This is a library written for the Qwiic LED Stick.
  By Ciara Jekel @ SparkFun Electronics, June 11th, 2018

  The Qwiic LED Stick features ten addressable APA102 LEDs, 
  making it easy to add full color LED control using I2C. 
  Write to individual LEDs to display a count in binary,
  or write to the whole strip for cool lighting effects
  
  https://github.com/sparkfun/SparkFun_Qwiic_LED_Stick_Arduino_Library

  Development environment specifics:
  Arduino IDE 1.8.5

  SparkFun labored with love to create this code. Feel like supporting open
  source hardware? Buy a board from SparkFun!
  https://www.sparkfun.com/products/14783
*/


#include "Qwiic_LED_Stick.h"

//Constructor
LED::LED() {
  _LEDAddress = 0;
}

//Start I2C communication
bool LED::begin(uint8_t address) {
  mI2CDev = new Jims_I2CDevice(address);

  if (address < 0x08 || address > 0x77) return false; //invalid I2C addresses
  _LEDAddress = address; //store the address in a private global variable
  return true;
}
//Change the color of a specific LED
//each color must be a value between 0-255
//LEDS indexed starting at 1
bool LED::setLEDColor(uint8_t number, uint8_t red, uint8_t green, uint8_t blue) {

  // First, boundary check
  if (red > 255)
    red = 255;
  if (red < 0)
    red = 0;
  if (green > 255)
    green = 255;
  if (green < 0)
    green = 0;
  if (blue > 255)
    blue = 255;
  if (blue < 0)
    blue = 0;
  
  uint8_t buffer[5];
  buffer[0] = COMMAND_WRITE_SINGLE_LED_COLOR; //command to change single LED's color
  buffer[1] = number; //choose which LED
  buffer[2] = red; //update red value
  buffer[3] = green; //update green value
  buffer[4] = blue; //update blue value
  return (mI2CDev->writeBuffer(buffer, 5));
}
//Change the color of all LEDs
//each color must be a value between 0-255
bool LED::setLEDColor(uint8_t red, uint8_t green, uint8_t blue) {
  // First, boundary check
  if (red > 255)
    red = 255;
  if (red < 0)
    red = 0;
  if (green > 255)
    green = 255;
  if (green < 0)
    green = 0;
  if (blue > 255)
    blue = 255;
  if (blue < 0)
    blue = 0;

  uint8_t buffer[5];
  buffer[0] = COMMAND_WRITE_ALL_LED_COLOR; //command to change single LED's color
  buffer[1] = red; //update red value
  buffer[2] = green; //update green value
  buffer[3] = blue; //update blue value
  return (mI2CDev->writeBuffer(buffer, 4));
}

//Change the brightness of all LEDs, while keeping their current color
//brightness must be a value between 0-31
//To turn all LEDs off but remember their previous color, set brightness to 0
bool LED::setLEDBrightness(uint8_t brightness) {
  // First, boundary check
  if (brightness > 31)
    brightness = 31;
  if (brightness < 0)
    brightness = 0;

  uint8_t buffer[5];
  buffer[0] = COMMAND_WRITE_ALL_LED_BRIGHTNESS; //command to change single LED's color
  buffer[1] = brightness; //update red value
  return (mI2CDev->writeBuffer(buffer, 2));
}

//Turn all LEDS off by setting color to 0
bool LED::LEDOff(void) {
  uint8_t buffer[5];
  buffer[0] = COMMAND_WRITE_ALL_LED_OFF; //command to change single LED's color
  return (mI2CDev->writeBuffer(buffer, 1));

  return (true);
}

//Change the I2C address from one address to another
bool LED::changeAddress(uint8_t newAddress)
{
    _LEDAddress = newAddress;
  uint8_t buffer[5];
  buffer[0] = COMMAND_CHANGE_ADDRESS; //command to change single LED's color
  buffer[1] = newAddress; //update red value
  bool write = mI2CDev->writeBuffer(buffer, 2);
  if(true == write)
  {
    mI2CDev = new Jims_I2CDevice(newAddress);
  }
  return (write);

}
//Change the length of LEDs
bool LED::changeLength(uint8_t newLength)
{
  uint8_t buffer[5];
  buffer[0] = COMMAND_CHANGE_LED_LENGTH; //command to change single LED's color
  buffer[1] = newLength; //update red value
  return (mI2CDev->writeBuffer(buffer, 2));
}
