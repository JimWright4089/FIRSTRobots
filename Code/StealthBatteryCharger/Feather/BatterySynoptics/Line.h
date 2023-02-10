/*****************************************************************************
 **
 **  Project:                      Stealth Battery Charger
 **
 **                             Copyright (c) 2023
 **                                James A Wright
 **                            All Rights Reserved
 **
 **  File Description:
 **      This displays a Line
 **
 *****************************************************************************/
#ifndef LINE
#define LINE

#include <Adafruit_GFX.h>
#include "DrawingObjects.h"

//--------------------------------------------------------------------
//  Class Define
//--------------------------------------------------------------------
//  Name:
//     Line
//  
//  Purpose:
//     The line item
//  
//--------------------------------------------------------------------
class Line : public DrawingObjects
{
  //-------------------------------------------------------------
  //  Public
  //-------------------------------------------------------------
  public:
        Line(int16_t oneX, int16_t oneY, int16_t twoX, int16_t twoY, int16_t size, Color color);
        void Draw();
        void SetColor(Color color);

  //-------------------------------------------------------------
  //  Protected
  //-------------------------------------------------------------
  protected:
    int16_t mOneX;
    int16_t mOneY;
    int16_t mTwoX;
    int16_t mTwoY;
    int16_t mSize;
};
#endif
