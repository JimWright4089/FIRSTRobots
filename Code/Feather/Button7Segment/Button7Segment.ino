#include <Wire.h> // Enable this line if using Arduino Uno, Mega, etc.
#include <Adafruit_GFX.h>
#include "Adafruit_LEDBackpack.h"

const int buttonPin = 32;

int segmentNumber = 0;
int buttonState;            // the current reading from the input pin
int lastButtonState = LOW;
unsigned long lastDebounceTime = 0;  // the last time the output pin was toggled
unsigned long debounceDelay = 50;
int debugCount = 0;
Adafruit_7segment matrix = Adafruit_7segment();


void setup() 
{
  Serial.begin(11520);
  while (!Serial) 
  {
    ;  // wait for serial port to connect. Needed for native USB port only
  }
  matrix.begin(0x70);
  pinMode(buttonPin, INPUT);
}

void loop() 
{
  // read the state of the switch into a local variable:
  int reading = digitalRead(buttonPin);

  // check to see if you just pressed the button
  // (i.e. the input went from LOW to HIGH), and you've waited long enough
  // since the last press to ignore any noise:

  // If the switch changed, due to noise or pressing:
  if (reading != lastButtonState) {
    // reset the debouncing timer
    lastDebounceTime = millis();
  }

  if ((millis() - lastDebounceTime) > debounceDelay) {
    // whatever the reading is at, it's been there for longer than the debounce
    // delay, so take it as the actual current state:

    // if the button state has changed:
    if (reading != buttonState) {
      buttonState = reading;

      // only toggle the LED if the new button state is HIGH
      if (buttonState == HIGH) {
        segmentNumber++;
        segmentNumber = (segmentNumber%10);
        matrix.println(segmentNumber);
        matrix.writeDisplay();
      }
    }
  }

  debugCount++;
  if(0 == (debugCount%2000))
  {
    Serial.println(segmentNumber);
  }
  lastButtonState = reading;
}
