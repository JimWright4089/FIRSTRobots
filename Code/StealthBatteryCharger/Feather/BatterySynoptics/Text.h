/*****************************************************************************
 **
 **  Project:                      Stealth Battery Charger
 **
 **                             Copyright (c) 2023
 **                                James A Wright
 **                            All Rights Reserved
 **
 **  File Description:
 **      This displays Text
 **
 *****************************************************************************/
#ifndef TEXT
#define TEXT

//--------------------------------------------------------------------
//  Included Files
//--------------------------------------------------------------------
#include <Adafruit_GFX.h>
#include "DrawingObjects.h"

//--------------------------------------------------------------------
//  Class Define
//--------------------------------------------------------------------
//  Name:
//     Text
//  
//  Purpose:
//     The text item
//  
//--------------------------------------------------------------------
class Text : public DrawingObjects
{
  static const int MAX_TEXT = 10;

  //-------------------------------------------------------------
  //  Public
  //-------------------------------------------------------------
  public:
        Text(int16_t x, int16_t y, int16_t size, Color color, char *text);
        void Draw();
        void Draw(Color color);
        void SetText(char *text);
        void SetColor(Color color);
        void SetAmps(int16_t amps);

  //-------------------------------------------------------------
  //  Protected
  //-------------------------------------------------------------
  protected:
    int16_t mX;
    int16_t mY;
    char mText[MAX_TEXT];
    int16_t mSize;
};
#endif
