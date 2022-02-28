// RGB Panel GFX Demo example for 16x32 panel
// By Marc MERLIN <marc_soft@merlins.org>
// Contains code (c) Adafruit, license BSD

// WILL NOT FIT on ARDUINO UNO -- requires a Mega, M0 or M4 board

#include <RGBmatrixPanel.h>
#include "smileytongue24.h"

// Most of the signal pins are configurable, but the CLK pin has some
// special constraints.  On 8-bit AVR boards it must be on PORTB...
// Pin 8 works on the Arduino Uno & compatibles (e.g. Adafruit Metro),
// Pin 11 works on the Arduino Mega.  On 32-bit SAMD boards it must be
// on the same PORT as the RGB data pins (D2-D7)...
// Pin 8 works on the Adafruit Metro M0 or Arduino Zero,
// Pin A4 works on the Adafruit Metro M4 (if using the Adafruit RGB
// Matrix Shield, cut trace between CLK pads and run a wire to A4).


// FeatherWing pinouts for M0 and M4
#define CLK  13
#define OE   1  // TX
#define LAT  0  // RX
#define A   A5
#define B   A4
#define C   A3
#define D   A2

#define WAIT_TIME 15000
// the RGB data pins on featherwing, must be on same PORT as CLK
uint8_t rgbpins[] = { 6,5,9,11,10,12 };
 
// Create a 32-pixel tall matrix with the defined pins
RGBmatrixPanel matrix(A, B, C, D, CLK, LAT, OE, false, 32, rgbpins);

// Panel Matrix doesn't fully work like Neomatrix (which I originally
// wrote this demo for), so map a few calls to be compatible. The rest
// comes from Adafruit_GFX and works the same on both backends.
#define setBrightness(x) fillScreen(0) // no-op, no brightness on this board
#define clear()          fillScreen(0)
#define show()           swapBuffers(true)
#define Color(x,y,z)     Color444(x/16,y/16,z/16)

// Define matrix width and height.
#define mw 256
#define mh 32

// This could also be defined as matrix.color(255,0,0) but those defines
// are meant to work for Adafruit::GFX backends that are lacking color()
#define LED_BLACK           0

// Convert a BGR 4/4/4 bitmap to RGB 5/6/5 used by Adafruit_GFX
void fixdrawRGBBitmap(const uint16_t *bitmap) 
{
  uint16_t w = 256;
  uint16_t h = 32;
    uint16_t RGB_bmp_fixed[w * h];
    for (uint16_t pixel=0; pixel<w*h; pixel++) {
        uint8_t r,g,b;
        uint16_t color = pgm_read_word(bitmap + pixel);

        //Serial.print(color, HEX);
        b = (color & 0xF00) >> 8;
        g = (color & 0x0F0) >> 4;
        r = color & 0x00F;
        //Serial.print(" ");
        //Serial.print(b);
        //Serial.print("/");
        //Serial.print(g);
        //Serial.print("/");
        //Serial.print(r);
        //Serial.print(" -> ");
        // expand from 4/4/4 bits per color to 5/6/5
        b = map(b, 0, 15, 0, 31);
        g = map(g, 0, 15, 0, 63);
        r = map(r, 0, 15, 0, 31);
        //Serial.print(r);
        //Serial.print("/");
        //Serial.print(g);
        //Serial.print("/");
        //Serial.print(b);
        RGB_bmp_fixed[pixel] = (r << 11) + (g << 5) + b;
        //Serial.print(" -> ");
        //Serial.println(RGB_bmp_fixed[pixel], HEX);
    }
    matrix.drawRGBBitmap(0, 0, RGB_bmp_fixed, w, h);
}

void loop() {
  fixdrawRGBBitmap(tableauLogo);
  delay(WAIT_TIME);
  fixdrawRGBBitmap(stealthLogo);
  delay(WAIT_TIME);
  fixdrawRGBBitmap(speeaLogo);
  delay(WAIT_TIME);
  fixdrawRGBBitmap(ospiLogo);
  delay(WAIT_TIME);
  fixdrawRGBBitmap(microsoftLogo);
  delay(WAIT_TIME);
  fixdrawRGBBitmap(firstWashingtonLogo);
  delay(WAIT_TIME);
  fixdrawRGBBitmap(firstLogo);
  delay(WAIT_TIME);
  fixdrawRGBBitmap(fullFirstLogo);
  delay(WAIT_TIME);
  fixdrawRGBBitmap(cedarcrestLogo);
  delay(WAIT_TIME);
  fixdrawRGBBitmap(boeingLogo);
  delay(WAIT_TIME);
  fixdrawRGBBitmap(bitmap24);
  delay(WAIT_TIME);
}

void setup() {
    Serial.begin(115200);
    matrix.begin();
    matrix.setTextWrap(false);
    matrix.setBrightness(BRIGHTNESS);
    // Test full bright of all LEDs. If brightness is too high
    // for your current limit (i.e. USB), decrease it.
    matrix.clear();
}
