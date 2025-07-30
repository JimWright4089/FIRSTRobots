#include <Wire.h> // Enable this line if using Arduino Uno, Mega, etc.
#include <Adafruit_GFX.h>
#include "Adafruit_LEDBackpack.h"

const int analogInPin = A2;  // Analog input pin that the potentiometer is attached to
const int LEDPin = 32;
Adafruit_7segment matrix = Adafruit_7segment();
const double target_ohm = 5.26;
const int target_upper =  2340;
const int target_lower = 2300;
bool target_met = false;
bool old_target_met = false;
long countingTime = 0;


void setup() {
  Serial.begin(115200);
  // initialize digital pin LED_BUILTIN as an output.
  pinMode(LEDPin, OUTPUT);
  matrix.begin();

}

void loop() {
//  digitalWrite(LEDPin, HIGH);  // turn the LED on (HIGH is the voltage level)

  int sensorValue = analogRead(analogInPin);

  target_met = false;
  if((sensorValue>target_lower)&&(sensorValue<target_upper))
  {
    target_met = true;
  }

  if(false == target_met)
  {
    countingTime = millis();
    digitalWrite(LEDPin, LOW);   // turn the LED off by making the voltage LOW
  }
  else
  {
    if ((millis() - countingTime) > 3000) 
    {
      digitalWrite(LEDPin, HIGH);   // turn the LED off by making the voltage LOW
    }
    else
    {
      digitalWrite(LEDPin, LOW);   // turn the LED off by making the voltage LOW
    }
  }


  // print the results to the Serial Monitor:
  Serial.print("sensor = ");
  Serial.println(sensorValue);

  matrix.println(sensorValue);
  matrix.writeDisplay();

  delay(50);
}
