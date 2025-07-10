#include <Adafruit_Protomatter.h>
#include <Fonts/FreeSansBold18pt7b.h>
#include <Fonts/FreeSansBold12pt7b.h>
#include <Fonts/FreeSans12pt7b.h>
#include <Fonts/FreeSans24pt7b.h>
#include <Fonts/FreeSerif18pt7b.h>

uint8_t rgbPins[]  = {6, 5, 9, 11, 10, 12};
uint8_t addrPins[] = {A5, A4, A3, A2, A1};
uint8_t clockPin   = 13;
uint8_t latchPin   = 0;
uint8_t oePin      = 1;

enum TheSigns {
  NONE,
  TEST,
  NERDS,
  SERIF_TEST,
  SERIF,
  NO_KINGS
};

TheSigns gCurSign = NONE;
TheSigns gWantedSign = NO_KINGS;

int SANS_LINE_X[] PROGMEM = { 0,  0,  0, 128, 128, 128, 128, 256, 256};
int SANS_LINE_Y[] PROGMEM = {19, 41, 63,  -1,  22,  45,  68,   3,  42};

int SERIF_LINE_X[] PROGMEM = { 0,  0, 128, 128, 256};
int SERIF_LINE_Y[] PROGMEM = {25, 56,  25,  56,  24};

int BIG_LINE_X[] PROGMEM = { 0, 128};
int BIG_LINE_Y[] PROGMEM = {40,  40};

const char* TEST_TEXT[] PROGMEM = {
  "Test Line 0",
  "Test Line 1",
  "Line 2",
  "Test Line 3",
  "Test 4",
  "Test Line 5",
  "Test Line 6"
};

const char* NERDS_TEXT[] PROGMEM = {
  " ",
  "Now even",
  "the nerds",
  "are building",
  "protest",
  "signs",
  " "
};

const char* SERIF_TEST_TEXT[] PROGMEM = {
  "Line 0",
  "Line 1",
  "Line 2",
  "Line 3",
  "Line 4",
};

const char* SERIF_TEXT[] PROGMEM = {
  "I'm so",
  "mad I'm",
  "protestn'",
  "in serif",
  " "
};

const char* NO_KINGS_TEXT[] PROGMEM = {
  "no",
  "kings"
};

Adafruit_Protomatter matrix(
  384,          // Matrix width in pixels
  4,           // Bit depth -- 6 here provides maximum color options
  1, rgbPins,  // # of matrix chains, array of 6 RGB pins for each
  5, addrPins, // # of address pins (height is inferred), array of pins
  clockPin, latchPin, oePin, // Other matrix control pins
  true);       // HERE IS THE MAGIC FOR DOUBLE-BUFFERING!

void setup(void) {
  Serial.begin(9600);

  // Initialize matrix...
  ProtomatterStatus status = matrix.begin();
  Serial.print("Protomatter begin() status: ");
  Serial.println((int)status);
  if(status != PROTOMATTER_OK) {
    // DO NOT CONTINUE if matrix setup encountered an error.
    for(;;);
  }

  matrix.setFont(&FreeSans12pt7b); // Use nice bitmap font
//  matrix.setFont(&FreeSansBold12pt7b); // Use nice bitmap font
  matrix.setTextWrap(true);           // Allow text off edge
  matrix.setTextColor(matrix.color565(35, 35, 35));         // White
}

// LOOP - RUNS REPEATEDLY AFTER SETUP --------------------------------------
int count = 0;
void loop(void) {

  if(gCurSign != gWantedSign)
  {
    gCurSign = gWantedSign;
    matrix.fillScreen(0); // Fill background black

    switch(gCurSign)
    {
      case(TEST):
        print_seven_lines_sans12pt7b(SANS_LINE_X, SANS_LINE_Y, TEST_TEXT);
        break;
      case(NERDS):
        print_seven_lines_sans12pt7b(SANS_LINE_X, SANS_LINE_Y, NERDS_TEXT);
        break;
      case(SERIF_TEST):
        print_six_lines_serif18pt7b(SERIF_LINE_X, SERIF_LINE_Y, SERIF_TEST_TEXT);
        break;
      case(SERIF):
        print_six_lines_serif18pt7b(SERIF_LINE_X, SERIF_LINE_Y, SERIF_TEXT);
        break;        
      case(NO_KINGS):
        print_lines_big(BIG_LINE_X, BIG_LINE_Y, NO_KINGS_TEXT);
        break;        
      default:
        break;
    }
    matrix.show();
  }

  delay(100);
}

void print_lines_big(int x[], int y[], const char* atext[])
{
  matrix.setFont(&FreeSans24pt7b);
  matrix.setTextWrap(false);

  print_centered_line(x[0], y[0], atext[0]);
  print_centered_line(x[1], y[1], atext[1]);
}

void print_six_lines_serif18pt7b(int x[], int y[], const char* atext[])
{
  matrix.setFont(&FreeSerif18pt7b);
  matrix.setTextWrap(false);

  print_centered_line(x[0], y[0], atext[0]);
  print_centered_line(x[1], y[1], atext[1]);
  print_centered_line(x[2], y[2], atext[2]);
  print_centered_line(x[3], y[3], atext[3]);
  print_centered_line(x[4], y[4], atext[4]);
}

void print_seven_lines_sans12pt7b(int x[], int y[], const char* atext[])
{
  matrix.setFont(&FreeSans12pt7b);
  matrix.setTextWrap(true);
  print_centered_line(x[0], y[0], atext[0]);
  print_centered_line(x[1], y[1], atext[1]);
  print_centered_line(x[2], y[2], atext[2]);
  print_centered_line(x[3], y[3], atext[2]);
  print_centered_line(x[4], y[4], atext[3]);
  print_centered_line(x[5], y[5], atext[4]);
  print_centered_line(x[6], y[6], atext[5]);
  print_centered_line(x[7], y[7], atext[5]);
  print_centered_line(x[8], y[8], atext[6]);
}

void print_centered_line(int x, int y, const char* text)
{
  int16_t x1;
  int16_t y1;
  uint16_t w;
  uint16_t h;
    
  matrix.getTextBounds(text, x, y, &x1, &y1, &w, &h);
  matrix.setCursor(x + ((128-w)/2), y);
  matrix.print(text);
  
}
