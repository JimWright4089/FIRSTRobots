//----------------------------------------------------------------------------
//
//  $Workfile: M4CanCode
//
//  $Revision: X$
//
//  Project:    SOTA Workshop
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
//     Sends and receives CAN data
//
//----------------------------------------------------------------------------

//----------------------------------------------------------------------------
//  Includes
//----------------------------------------------------------------------------
#include <CANSAME5x.h>
#include <Adafruit_NeoPixel.h>

//----------------------------------------------------------------------------
//  Globals
//----------------------------------------------------------------------------
Adafruit_NeoPixel gStrip(1, PIN_NEOPIXEL, NEO_GRB + NEO_KHZ800);
CANSAME5x gCAN;
uint32_t gTimestamp;
uint8_t  gCount = 0;

//--------------------------------------------------------------------
// Purpose:
//     Setup the microprocessor
//
// Notes:
//     None.
//--------------------------------------------------------------------
void setup() {
  Serial.begin(115200);
  Serial.println("CAN NeoPixel demo");

  pinMode(PIN_CAN_STANDBY, OUTPUT);
  digitalWrite(PIN_CAN_STANDBY, false); // turn off STANDBY
  pinMode(PIN_CAN_BOOSTEN, OUTPUT);
  digitalWrite(PIN_CAN_BOOSTEN, true); // turn on booster

  gStrip.begin();
  gStrip.setBrightness(50);

  // start the CAN bus at 1 mbps
  if (!gCAN.begin(1000000)) {
    Serial.println("Starting CAN failed!");
    while (1) delay(10);
  }

  gTimestamp = millis();
}

//--------------------------------------------------------------------
// Purpose:
//     The Idle loop
//
// Notes:
//     None.
//--------------------------------------------------------------------
void loop() {
  if ((millis() - gTimestamp) > 50) {
    gCAN.beginExtendedPacket(0xA080040, 8);
    gCAN.write(11);
    gCAN.write(22);
    gCAN.write(33);
    gCAN.write(44);
    gCAN.write(55);
    gCAN.write(66);
    gCAN.write(77);
    gCAN.write(gCount);
    gCount++;
    gCAN.endPacket();
    gTimestamp = millis();
  }

  // try to parse any incoming packet
  int packetSize = gCAN.parsePacket();

  if(0xA080080 == gCAN.packetId())
  {
    if (packetSize) {
      if (gCAN.packetExtended()) {
        Serial.print("ext ");
      }
  
      if (gCAN.packetRtr()) {
        // Remote transmission request, packet contains no data
        Serial.print("RTR ");
      }
  
      Serial.print("0x");
      Serial.print(gCAN.packetId(), HEX);
  
      if (gCAN.packetRtr()) {
        Serial.print(" len:");
        Serial.print(gCAN.packetDlc());
        Serial.print(" ");
      } else {
        Serial.print(" len:");
        Serial.print(packetSize);
        Serial.print(" ");
  
        uint8_t receivedData[packetSize];
        for (int i=0; i<packetSize; i++) {
          receivedData[i] = gCAN.read();
          Serial.print("0x");
          Serial.print(receivedData[i], HEX);
          Serial.print(", ");
        }
        Serial.println();
  
        uint16_t value = (uint16_t)receivedData[0] << 8 | receivedData[1];
        gStrip.setPixelColor(0, Wheel(value / 4));
        gStrip.show();
      }
    }
  }
}

//--------------------------------------------------------------------
// Purpose:
//     Set the color of the pixel
//
// Notes:
//     None.
//--------------------------------------------------------------------
uint32_t Wheel(byte WheelPos) {
  WheelPos = 255 - WheelPos;
  if(WheelPos < 85) {
    return gStrip.Color(255 - WheelPos * 3, 0, WheelPos * 3);
  }
  if(WheelPos < 170) {
    WheelPos -= 85;
    return gStrip.Color(0, WheelPos * 3, 255 - WheelPos * 3);
  }
  WheelPos -= 170;
  return gStrip.Color(WheelPos * 3, 255 - WheelPos * 3, 0);
}
