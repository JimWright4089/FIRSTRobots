#include <Adafruit_Protomatter.h>
#include <map>
#include "frame.hpp"
#include "clip.hpp"

const int MAX_HEIGHT = 32;
const int MAX_WIDTH = 32;

const int STATE_FORWARD     =  1;
const int STATE_BACKWARD    =  2;
const int STATE_SLEEP       =  0;
const int STATE_IDLE        =  1;
const int STATE_LOOK_LEFT   =  2;
const int STATE_LOOK_RIGHT  =  3;
const int STATE_WEDGE_LEFT  =  4;
const int STATE_WEDGE_RIGHT =  5;
const int STATE_WINK_LEFT   =  6;
const int STATE_WINK_RIGHT  =  7;
const int STATE_BLINK       =  8;
const int STATE_HAPPY       =  9;
const int STATE_ANGRY       = 10;
const int STATE_ROLL_LEFT   = 11;
const int STATE_SHIFT_LEFT  = 12;
const int STATE_SHIFT_RIGHT = 13;
const int STATE_SAD         = 14;
const int STATE_FAST        = 15;
const int STATE_ROCKED      = 16;
const int STATE_MERGE_LEFT  = 17;
const int STATE_MERGE_RIGHT = 18;
const int STATE_CYLON_START = 19;
const int STATE_CYLON       = 20;
const int STATE_CYLON_END   = 21;

uint8_t rgbPins[]  = {6, 5, 9, 11, 10, 12};
uint8_t addrPins[] = {A5, A4, A3, A2};
uint8_t clockPin   = 13;
uint8_t latchPin   = 0;
uint8_t oePin      = 1;

uint32_t  faces[] = {
0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00FFFF00, 0x00000000, 0x00000000, 0x00000000,
0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00FFFF00, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00FFFF00, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00FFFF00, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00FFFF00, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00FFFF00, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x030000C0, 0x02000040, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x030000C0, 0x030000C0, 0x02000040, 0x02000040, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x030000C0, 0x030000C0, 0x02000040, 0x02000040, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x030000C0, 0x030000C0, 0x02000040, 0x02000040, 0x02000040, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x030000C0, 0x030000C0, 0x030000C0, 0x02000040, 0x02000040, 0x02000040, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x030000C0, 0x030000C0, 0x02000040, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
0x7FFFFFFE, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0x7FFFFFFE, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
0x3FFFFFFC, 0x7FFFFFFE, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0x7FFFFFFE, 0x3FFFFFFC, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
0x1FFFFFF8, 0x3FFFFFFC, 0x7FFFFFFE, 0x7FFFFFFE, 0x7FFFFFFE, 0x7FFFFFFE, 0x7FFFFFFE, 0x7FFFFFFE, 0x7FFFFFFE, 0x7FFFFFFE, 0x3FFFFFFC, 0x1FFFFFF8, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
0x0FFFFFF0, 0x1FFFFFF8, 0x3FFFFFFC, 0x3FFFFFFC, 0x3FFFFFFC, 0x3FFFFFFC, 0x3FFFFFFC, 0x3FFFFFFC, 0x3FFFFFFC, 0x3FFFFFFC, 0x3FFFFFFC, 0x3FFFFFFC, 0x3FFFFFFC, 0x1FFFFFF8, 0x0FFFFFF0, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
0x07FFFFE0, 0x0FFFFFF0, 0x1FFFFFF8, 0x1FFFFFF8, 0x1FFFFFF8, 0x1FFFFFF8, 0x1FFFFFF8, 0x1FFFFFF8, 0x1FFFFFF8, 0x1FFFFFF8, 0x1FFFFFF8, 0x1FFFFFF8, 0x1FFFFFF8, 0x1FFFFFF8, 0x1FFFFFF8, 0x1FFFFFF8, 0x0FFFFFF0, 0x07FFFFE0, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x03000000, 0x03E00000, 0x03FC0000, 0x03FF8000, 0x03FFF000, 0x03FFFE00, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00FFFF00, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x02000000, 0x03800000, 0x03E00000, 0x03F80000, 0x03FE0000, 0x03FF8000, 0x03FFE000, 0x03FFF800, 0x03FFFE00, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00FFFF00, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x03000000, 0x03E00000, 0x03FC0000, 0x03FF8000, 0x03FFF000, 0x03FFFE00, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00FFFF00, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x000001C0, 0x00000FC0, 0x00007FC0, 0x0003FFC0, 0x001FFFC0, 0x00FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00FFFF00, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x000000C0, 0x000007C0, 0x00001FC0, 0x00007FC0, 0x0001FFC0, 0x0007FFC0, 0x001FFFC0, 0x007FFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00FFFF00, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x000000C0, 0x000007C0, 0x00003FC0, 0x0001FFC0, 0x000FFFC0, 0x007FFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00FFFF00, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000
};

Adafruit_Protomatter matrix(
  64,          // Width of matrix (or matrix chain) in pixels
  4,           // Bit depth, 1-6
  1, rgbPins,  // # of matrix chains, array of 6 RGB pins for each
  4, addrPins, // # of address pins (height is inferred), array of pins
  clockPin, latchPin, oePin, // Other matrix control pins
  false);      // No double-buffering here (see "doublebuffer" example)

Frame mCurFrame;
Clip  mCurClip;
Clip  mSleep;
Clip  mLookLeft;
Clip  mLookRight;
Clip  mWedgeLeft;
Clip  mWedgeRight;
Clip  mWinkLeft;
Clip  mWinkRight;
Clip  mHappy;
Clip  mAngry;
Clip  mRollLeft;
Clip  mShiftLeft;
Clip  mShiftRight;
Clip  mSad;
Clip  mFast;
Clip  mRocked;
Clip  mMerge;
Clip  mMergeCylon;
Clip  mCylon;
int   mState = STATE_SLEEP;
int   mDir = STATE_FORWARD;

void setup(void) {
  Serial.begin(9600);
  randomSeed(analogRead(0));
  
  // Initialize matrix...
  ProtomatterStatus status = matrix.begin();
  Serial.print("Protomatter begin() status: ");
  Serial.println((int)status);
  if(status != PROTOMATTER_OK) {
    // DO NOT CONTINUE if matrix setup encountered an error.
    Serial.print("Matrix setup encountered an error.");
    delay(1000);
  }
  buildClips();
  mCurClip = mSleep;
  mCurClip.reset(true);
}

void setNextClip(void)
{
  int randNum = 0;
  switch(mState)
  {
    case(STATE_SLEEP):
      mState = STATE_IDLE;
      break;
    case(STATE_IDLE):
      delay(1000+random(5000));

      randNum = random(18);

      mDir = STATE_FORWARD;
      switch(randNum)
      {
        case 0:
          mState = STATE_LOOK_LEFT;
          mCurClip = mLookLeft;
          mCurClip.reset(false);
          break;
        case 1:
          mState = STATE_LOOK_RIGHT;
          mCurClip = mLookRight;
          mCurClip.reset(false);
          break;
        case 2:
          mState = STATE_WEDGE_LEFT;
          mCurClip = mWedgeLeft;
          mCurClip.reset(false);
          break;
        case 3:
          mState = STATE_WEDGE_RIGHT;
          mCurClip = mWedgeRight;
          mCurClip.reset(false);
          break;
        case 4:
          mState = STATE_WINK_LEFT;
          mCurClip = mWinkLeft;
          mCurClip.reset(false);
          break;
        case 5:
          mState = STATE_WINK_RIGHT;
          mCurClip = mWinkRight;
          mCurClip.reset(false);
          break;
        case 6:
          mState = STATE_BLINK;
          mCurClip = mSleep;
          mCurClip.reset(false);
          break;
        case 7:
          mState = STATE_HAPPY;
          mCurClip = mHappy;
          mCurClip.reset(false);
          break;
        case 8:
          mState = STATE_ANGRY;
          mCurClip = mAngry;
          mCurClip.reset(false);
          break;
        case 9:
          mState = STATE_ROLL_LEFT;
          mCurClip = mRollLeft;
          mCurClip.reset(false);
          break;
        case 10:
          mState = STATE_SHIFT_LEFT;
          mCurClip = mShiftLeft;
          break;
        case 11:
          mState = STATE_SHIFT_RIGHT;
          mCurClip = mShiftRight;
          break;
        case 12:
          mState = STATE_SAD;
          mCurClip = mSad;
          break;
        case 13:
          mState = STATE_FAST;
          mCurClip = mFast;
          break;
        case 14:
          mState = STATE_ROCKED;
          mCurClip = mRocked;
          break;
        case 15:
          mState = STATE_MERGE_LEFT;
          mCurClip = mMerge;
          break;
        case 16:
          mState = STATE_MERGE_RIGHT;
          mCurClip = mMerge;
          break;
        case 17:
          mState = STATE_CYLON_START;
          mCurClip = mMergeCylon;
          break;
      }

      mCurClip.reset(false);
      switch(mState)
      {
        case(STATE_MERGE_RIGHT):
          mCurClip.reset(true);
          break;
      }
     
      break;
    case(STATE_BLINK):
    case(STATE_WINK_LEFT):
    case(STATE_WINK_RIGHT):
      if(STATE_FORWARD == mDir)
      {
        delay(100+random(200));
        mDir = STATE_BACKWARD;
        mCurClip.reset(true);
      }
      else
      {
        mState = STATE_IDLE;
        mDir = STATE_FORWARD;
      }
      break;
    case(STATE_LOOK_LEFT):
    case(STATE_LOOK_RIGHT):
    case(STATE_SHIFT_RIGHT):
    case(STATE_SHIFT_LEFT):
      if(STATE_FORWARD == mDir)
      {
        delay(100+random(1000));
        mDir = STATE_BACKWARD;
        mCurClip.reset(true);
      }
      else
      {
        mState = STATE_IDLE;
        mDir = STATE_FORWARD;
      }
      break;
    case(STATE_WEDGE_LEFT):
    case(STATE_WEDGE_RIGHT):
    case(STATE_ANGRY):
    case(STATE_HAPPY):
    case(STATE_SAD):
    case(STATE_FAST):
      if(STATE_FORWARD == mDir)
      {
        delay(1000+random(1000));
        mDir = STATE_BACKWARD;
        mCurClip.reset(true);
      }
      else
      {
        mState = STATE_IDLE;
        mDir = STATE_FORWARD;
      }
      break;
    case(STATE_ROLL_LEFT):
    case(STATE_ROCKED):
    case(STATE_MERGE_LEFT):
    case(STATE_MERGE_RIGHT):
    case(STATE_CYLON_END):
      mState = STATE_IDLE;
      mDir = STATE_FORWARD;
      break;
    case(STATE_CYLON_START):
    case(STATE_CYLON):
      if(random(4)>=3)
      {
        mState = STATE_CYLON_END;
        mCurClip = mMergeCylon;
        mCurClip.reset(true);
      }
      else
      {
        mState = STATE_CYLON;
        mCurClip.reset(false);
        mCurClip = mCylon;
      }
      break;
  }
}

void loop(void) 
{
  if(false == mCurClip.finished())
  {
    mCurFrame = mCurClip.getNextFrame();
    clearFullFace();
    drawFullFace(mCurFrame);
  }
  else
  {
    setNextClip();
  }
  delay(75);
}

void printFrame(Frame frame)
{
  Serial.print("LE:");
  Serial.print(frame.mLeftEye);
  Serial.print(" LEL:");
  Serial.print(frame.mLeftEyeLeft);
  Serial.print(" LET:");
  Serial.print(frame.mLeftEyeTop);
  Serial.print(" RE:");
  Serial.print(frame.mRightEye);
  Serial.print(" REL:");
  Serial.print(frame.mRightEyeLeft);
  Serial.print(" RET:");
  Serial.println(frame.mRightEyeTop);
}


void clearFullFace(void)
{
  for (int i = 0; i < MAX_HEIGHT; i++)
  {
    for (int j = 0; j < MAX_WIDTH + MAX_WIDTH; j++)
    {
      matrix.drawPixel(j, i, matrix.color565(0, 0, 0));
    }
  }
}

void drawFullFace(Frame frame)
{
  for (int i = 0; i < MAX_HEIGHT; i++)
  {
    int rowIndex = MAX_HEIGHT * frame.mLeftEye;
    int topIndex = (i + frame.mLeftEyeTop) % MAX_HEIGHT;
    int itemIndex = (rowIndex + topIndex);
    if (itemIndex > (rowIndex + MAX_HEIGHT))
    {
      itemIndex = MAX_HEIGHT - itemIndex;
    }

    uint32_t curValue = faces[itemIndex];

    for (int j = 0; j < MAX_WIDTH; j++)
    {
      if (0 != (curValue & (1 << MAX_WIDTH - (j + 1))))
      {
        int eyeLeft = frame.mLeftEyeLeft+j;

        if(eyeLeft < 0)
        {
          eyeLeft = MAX_WIDTH + MAX_WIDTH + eyeLeft;
        }
        else
        {
          if (eyeLeft >= MAX_WIDTH + MAX_WIDTH)
          {
            eyeLeft -= MAX_WIDTH + MAX_WIDTH;
          }
        }
        matrix.drawPixel(eyeLeft, i, matrix.color565(50, 50, 100));
      }
    }

    rowIndex = MAX_HEIGHT * frame.mRightEye;
    topIndex = (i + frame.mRightEyeTop) % MAX_HEIGHT;
    itemIndex = (rowIndex + topIndex);

    if (itemIndex > (rowIndex + MAX_HEIGHT))
    {
      itemIndex = MAX_HEIGHT - itemIndex;
    }
    curValue = faces[itemIndex];
    for (int j = 0; j < MAX_WIDTH; j++)
    {
      if (0 != (curValue & (1 << MAX_WIDTH - (j + 1))))
      {
        int eyeRight = MAX_WIDTH + frame.mRightEyeLeft + j;

        if (eyeRight < 0)
        {
          eyeRight = MAX_WIDTH + MAX_WIDTH + eyeRight;
        }
        else
        {
          if (eyeRight >= MAX_WIDTH + MAX_WIDTH)
          {
            eyeRight -= MAX_WIDTH + MAX_WIDTH;
          }
        }

        matrix.drawPixel(eyeRight, i, matrix.color565(50, 50, 100));
      }
    }
  }
  matrix.show(); // Copy data to matrix buffers
}


void buildClips(void)
{
  mSleep.add(Frame(1, 3, 0, 1, -3, 0));
  mSleep.add(Frame(2, 3, 0, 2, -3, 0));
  mSleep.add(Frame(3, 3, 0, 3, -3, 0));
  mSleep.add(Frame(4, 3, 0, 4, -3, 0));
  mSleep.add(Frame(5, 3, 0, 5, -3, 0));
  mSleep.add(Frame(6, 3, 0, 6, -3, 0));
  mSleep.add(Frame(25, 3, 0, 25, -3, 0));

  mLookLeft.add(Frame(1, 3, 0, 1, -3, 0));
  mLookLeft.add(Frame(0, 3, 0, 1, -4, -1));
  mLookLeft.add(Frame(0, 2, 0, 2, -6, -2));
  mLookLeft.add(Frame(0, 1, 0, 2, -8, -2));
  mLookLeft.add(Frame(0, 0, 0, 2, -10, -2));
  mLookLeft.add(Frame(0, -1, 0, 2, -12, -2));
  mLookLeft.add(Frame(0, -2, 0, 2, -14, -2));

  mLookRight.add(Frame(1, 3, 0, 1, -3, 0));
  mLookRight.add(Frame(1, 4, -1, 0, -3, 0));
  mLookRight.add(Frame(2, 6, -3, 0, -2, 0));
  mLookRight.add(Frame(2, 8, -3, 0, -1, 0));
  mLookRight.add(Frame(2, 10, -3, 0, 0, 0));
  mLookRight.add(Frame(2, 12, -3, 0, 1, 0));
  mLookRight.add(Frame(2, 14, -3, 0, 2, 0));

  mWedgeLeft.add(Frame(1, 3, 0, 1, -3, 0));
  mWedgeLeft.add(Frame(1, 3, 0, 21, -3, 0));
  mWedgeLeft.add(Frame(21, 3, 0, 20, -3, 0));
  mWedgeLeft.add(Frame(20, 3, 0, 19, -3, 0));

  mWedgeRight.add(Frame(1, 3, 0, 1, -3, 0));
  mWedgeRight.add(Frame(24, 3, 0, 1, -3, 0));
  mWedgeRight.add(Frame(23, 3, 0, 24, -3, 0));
  mWedgeRight.add(Frame(22, 3, 0, 23, -3, 0));

  mWinkLeft.add(Frame(1, 3, 0, 1, -3, 0));
  mWinkLeft.add(Frame(2, 3, 0, 1, -3, 0));
  mWinkLeft.add(Frame(3, 3, 0, 1, -3, 0));
  mWinkLeft.add(Frame(4, 3, 0, 1, -3, 0));
  mWinkLeft.add(Frame(5, 3, 0, 1, -3, 0));
  mWinkLeft.add(Frame(6, 3, 0, 1, -3, 0));
  mWinkLeft.add(Frame(25, 3, 0, 1, -3, 0));

  mWinkRight.add(Frame(1, 3, 0, 1, -3, 0));
  mWinkRight.add(Frame(1, 3, 0, 2, -3, 0));
  mWinkRight.add(Frame(1, 3, 0, 3, -3, 0));
  mWinkRight.add(Frame(1, 3, 0, 4, -3, 0));
  mWinkRight.add(Frame(1, 3, 0, 5, -3, 0));
  mWinkRight.add(Frame(1, 3, 0, 6, -3, 0));
  mWinkRight.add(Frame(1, 3, 0, 25, -3, 0));

  mHappy.add(Frame(1, 3, 0, 1, -3, 0));
  mHappy.add(Frame(13, 3, 0, 13, -3, 0));
  mHappy.add(Frame(12, 3, 0, 12, -3, 0));
  mHappy.add(Frame(11, 3, 0, 11, -3, 0));
  mHappy.add(Frame(10, 3, 0, 10, -3, 0));
  mHappy.add(Frame(9, 3, 0, 9, -3, 0));
  mHappy.add(Frame(8, 3, 0, 8, -3, 0));
  mHappy.add(Frame(7, 3, 0, 7, -3, 0));

  mAngry.add(Frame(1, 3, 0, 1, -3, 0));
  mAngry.add(Frame(21, 3, 0, 24, -3, 0));
  mAngry.add(Frame(20, 3, 0, 23, -3, 0));
  mAngry.add(Frame(19, 3, 0, 22, -3, 0));

  mRollLeft.add(Frame(1, 3, 0, 1, -3, 0));
  mRollLeft.add(Frame(1, 3, -2, 1, -3, 0));
  mRollLeft.add(Frame(1, 3, -4, 1, -3, 0));
  mRollLeft.add(Frame(1, 3, -6, 1, -3, 0));
  mRollLeft.add(Frame(1, 3, -8, 1, -3, 0));
  mRollLeft.add(Frame(1, 3, -10, 1, -3, 0));
  mRollLeft.add(Frame(1, 3, -12, 1, -3, 0));
  mRollLeft.add(Frame(1, 3, -14, 1, -3, 0));
  mRollLeft.add(Frame(1, 3, -16, 1, -3, 0));
  mRollLeft.add(Frame(1, 3, -18, 1, -3, 0));
  mRollLeft.add(Frame(1, 3, -20, 1, -3, 0));
  mRollLeft.add(Frame(1, 3, -22, 1, -3, 0));
  mRollLeft.add(Frame(1, 3, -24, 1, -3, 0));
  mRollLeft.add(Frame(1, 3, -26, 1, -3, 0));
  mRollLeft.add(Frame(1, 3, -28, 1, -3, 0));
  mRollLeft.add(Frame(1, 3, -30, 1, -3, 0));
  mRollLeft.add(Frame(1, 3, -32, 1, -3, 0));

  mShiftRight.add(Frame(1, 3, 0, 1, -3, 0));
  mShiftRight.add(Frame(1, 5, 0, 1, -1, 0));
  mShiftRight.add(Frame(1, 7, 0, 1, 1, 0));
  mShiftRight.add(Frame(1, 9, 0, 1, 3, 0));
  mShiftRight.add(Frame(1, 11, 0, 1, 5, 0));
  mShiftRight.add(Frame(1, 13, 0, 1, 6, 0));
  mShiftRight.add(Frame(1, 15, 0, 1, 6, 0));
  mShiftRight.add(Frame(1, 17, 0, 1, 6, 0));
  mShiftRight.add(Frame(1, 18, 0, 1, 6, 0));

  mShiftLeft.add(Frame(1, 3, 0, 1, -3, 0));
  mShiftLeft.add(Frame(1, 1, 0, 1, -5, 0));
  mShiftLeft.add(Frame(1, -1, 0, 1, -7, 0));
  mShiftLeft.add(Frame(1, -3, 0, 1, -9, 0));
  mShiftLeft.add(Frame(1, -5, 0, 1, -11, 0));
  mShiftLeft.add(Frame(1, -6, 0, 1, -13, 0));
  mShiftLeft.add(Frame(1, -6, 0, 1, -15, 0));
  mShiftLeft.add(Frame(1, -6, 0, 1, -17, 0));
  mShiftLeft.add(Frame(1, -6, 0, 1, -18, 0));

  mSad.add(Frame(1, 3, 0, 1, -3, 0));
  mSad.add(Frame(24, 3, 0, 21, -3, 0));
  mSad.add(Frame(23, 3, 0, 20, -3, 0));
  mSad.add(Frame(22, 3, 0, 19, -3, 0));

  mFast.add(Frame(1, 3, 0, 1, -3, 0));
  mFast.add(Frame(1, 3, 2, 1, -3, 2));
  mFast.add(Frame(1, 3, 4, 1, -3, 4));
  mFast.add(Frame(18, 3, 0, 18, -3, 0));
  mFast.add(Frame(17, 3, 0, 17, -3, 0));
  mFast.add(Frame(16, 3, 0, 16, -3, 0));
  mFast.add(Frame(15, 3, 0, 15, -3, 0));
  mFast.add(Frame(14, 3, 0, 14, -3, 0));

  mRocked.add(Frame(1, 3, 0, 1, -3, 0));
  mRocked.add(Frame(1, 3, 2, 1, -3, 2));
  mRocked.add(Frame(1, 3, 4, 1, -3, 4));
  mRocked.add(Frame(1, 3, 1, 1, -3, 1));
  mRocked.add(Frame(1, 3, -2, 1, -3, -2));
  mRocked.add(Frame(1, 3, -3, 1, -3, -3));
  mRocked.add(Frame(1, 3, -1, 1, -3, -1));
  mRocked.add(Frame(1, 3, 0, 1, -3, 0));

  mMerge.add(Frame(1, 3, 0, 1, -3, 0));
  mMerge.add(Frame(1, 6, 0, 1, -6, 0));
  mMerge.add(Frame(1, 9, 0, 1, -9, 0));
  mMerge.add(Frame(1, 12, 0, 1, -12, 0));
  mMerge.add(Frame(1, 15, 0, 1, -15, 0));
  mMerge.add(Frame(1, 18, 0, 1, -18, 0));
  mMerge.add(Frame(1, 21, 0, 1, -21, 0));
  mMerge.add(Frame(1, 24, 0, 1, -24, 0));
  mMerge.add(Frame(1, 27, 0, 1, -27, 0));
  mMerge.add(Frame(1, 30, 0, 1, -30, 0));
  mMerge.add(Frame(1, 33, 0, 1, -33, 0));
  mMerge.add(Frame(1, 36, 0, 1, -36, 0));
  mMerge.add(Frame(1, 39, 0, 1, -39, 0));
  mMerge.add(Frame(1, 42, 0, 1, -42, 0));
  mMerge.add(Frame(1, 45, 0, 1, -45, 0));
  mMerge.add(Frame(1, 48, 0, 1, -48, 0));
  mMerge.add(Frame(1, 51, 0, 1, -51, 0));
  mMerge.add(Frame(1, 54, 0, 1, -54, 0));
  mMerge.add(Frame(1, 57, 0, 1, -57, 0));
  mMerge.add(Frame(1, 60, 0, 1, -60, 0));
  mMerge.add(Frame(1, 63, 0, 1, -63, 0));
  mMerge.add(Frame(1, 67, 0, 1, -67, 0));

  mMergeCylon.add(Frame(1, 3, 0, 1, -3, 0));
  mMergeCylon.add(Frame(1, 4, 0, 1, -4, 0));
  mMergeCylon.add(Frame(1, 5, 0, 1, -5, 0));
  mMergeCylon.add(Frame(1, 6, 0, 1, -6, 0));
  mMergeCylon.add(Frame(1, 7, 0, 1, -7, 0));
  mMergeCylon.add(Frame(1, 8, 0, 1, -8, 0));
  mMergeCylon.add(Frame(1, 9, 0, 1, -9, 0));
  mMergeCylon.add(Frame(1, 10, 0, 1, -10, 0));
  mMergeCylon.add(Frame(1, 11, 0, 1, -11, 0));
  mMergeCylon.add(Frame(1, 12, 0, 1, -12, 0));
  mMergeCylon.add(Frame(1, 13, 0, 1, -13, 0));
  mMergeCylon.add(Frame(1, 14, 0, 1, -14, 0));
  mMergeCylon.add(Frame(1, 15, 0, 1, -15, 0));
  mMergeCylon.add(Frame(1, 16, 0, 1, -16, 0));

  mCylon.add(Frame(1, 14, 0, 1, -18, 0));
  mCylon.add(Frame(1, 12, 0, 1, -20, 0));
  mCylon.add(Frame(1, 10, 0, 1, -22, 0));
  mCylon.add(Frame(1, 8, 0, 1, -24, 0));
  mCylon.add(Frame(1, 6, 0, 1, -26, 0));
  mCylon.add(Frame(1, 4, 0, 1, -28, 0));
  mCylon.add(Frame(1, 2, 0, 1, -30, 0));
  mCylon.add(Frame(1, 0, 0, 1, -32, 0));
  mCylon.add(Frame(1, -2, 0, 1, -34, 0));
  mCylon.add(Frame(1, -4, 0, 1, -36, 0));
  mCylon.add(Frame(1, -6, 0, 1, -38, 0));
  mCylon.add(Frame(1, -6, 0, 1, -38, 0));
  mCylon.add(Frame(1, -4, 0, 1, -36, 0));
  mCylon.add(Frame(1, -2, 0, 1, -34, 0));
  mCylon.add(Frame(1, 0, 0, 1, -32, 0));
  mCylon.add(Frame(1, 2, 0, 1, -30, 0));
  mCylon.add(Frame(1, 4, 0, 1, -28, 0));
  mCylon.add(Frame(1, 6, 0, 1, -26, 0));
  mCylon.add(Frame(1, 8, 0, 1, -24, 0));
  mCylon.add(Frame(1, 10, 0, 1, -22, 0));
  mCylon.add(Frame(1, 12, 0, 1, -20, 0));
  mCylon.add(Frame(1, 14, 0, 1, -18, 0));
  mCylon.add(Frame(1, 16, 0, 1, -16, 0));
  mCylon.add(Frame(1, 18, 0, 1, -14, 0));
  mCylon.add(Frame(1, 20, 0, 1, -12, 0));
  mCylon.add(Frame(1, 22, 0, 1, -10, 0));
  mCylon.add(Frame(1, 24, 0, 1, -8, 0));
  mCylon.add(Frame(1, 26, 0, 1, -6, 0));
  mCylon.add(Frame(1, 28, 0, 1, -4, 0));
  mCylon.add(Frame(1, 30, 0, 1, -2, 0));
  mCylon.add(Frame(1, 32, 0, 1, 0, 0));
  mCylon.add(Frame(1, 34, 0, 1, 2, 0));
  mCylon.add(Frame(1, 36, 0, 1, 4, 0));
  mCylon.add(Frame(1, 38, 0, 1, 6, 0));
  mCylon.add(Frame(1, 36, 0, 1, 4, 0));
  mCylon.add(Frame(1, 34, 0, 1, 2, 0));
  mCylon.add(Frame(1, 32, 0, 1, 0, 0));
  mCylon.add(Frame(1, 30, 0, 1, -2, 0));
  mCylon.add(Frame(1, 28, 0, 1, -4, 0));
  mCylon.add(Frame(1, 26, 0, 1, -6, 0));
  mCylon.add(Frame(1, 24, 0, 1, -8, 0));
  mCylon.add(Frame(1, 22, 0, 1, -10, 0));
  mCylon.add(Frame(1, 20, 0, 1, -12, 0));
  mCylon.add(Frame(1, 18, 0, 1, -14, 0));
  mCylon.add(Frame(1, 16, 0, 1, -16, 0));
}
