#include "HID-Project.h"

const int pins[] = {3,2,4,5,6,7,8,9,10,14,15,16};
const int MAX_PINS = 12;

void setup() {
  for(int i=0;i<MAX_PINS;i++)
  {
    pinMode(pins[i], INPUT_PULLUP);
  }

  // Sends a clean report to the host. This is important on any Arduino type.
  Gamepad.begin();
}

void loop() {
  for(int i=0;i<MAX_PINS;i++)
  {
    int value = digitalRead(pins[i]);
    if(0 == value)
    {
      Gamepad.press(i+1);
    }
    else
    {
      Gamepad.release(i+1);
    }
  }
    Gamepad.write();
    delay(50);
}
