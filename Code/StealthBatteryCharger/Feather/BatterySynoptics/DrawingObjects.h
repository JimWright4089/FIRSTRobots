/*****************************************************************************
 **
 **  Project:                      Stealth Battery Charger
 **
 **                             Copyright (c) 2023
 **                                James A Wright
 **                            All Rights Reserved
 **
 **  File Description:
 **      This the super class of all the drawing items
 **
 *****************************************************************************/
#ifndef DRAWING_OBJECTS
#define DRAWING_OBJECTS

#include "Adafruit_HX8357.h"
#include "Adafruit_GFX.h"
#include <cstdint>

typedef uint32_t Color;

//--------------------------------------------------------------------
//  Class Define
//--------------------------------------------------------------------
//  Name:
//     DrawingObjects
//  
//  Purpose:
//     The super class for drawing
//  
//--------------------------------------------------------------------
class DrawingObjects
{
  //-------------------------------------------------------------
  //  Public
  //-------------------------------------------------------------
  public:
    static const int MAX_STANDARD_COLORS = 16;

    static const int TYPE_BOX = 0;
    static const int TYPE_LINE = 1;
    static const int TYPE_TEXT = 2;

    static enum STANDARD_COLOR
    {
      BLACK = 0,
      WHITE,
      GRAY,
      RED,
      ORANGE,
      YELLOW,
      GREEN,
      BLUE,
      PURPLE,
      MAGENTA,
      CYAN,
      LT_GRAY,
      LT_BLUE,
      DK_GREEN,
      PINK,
      GOLD
    } STANDARD_COLOR_ENUM;

    Color mStandardColors[MAX_STANDARD_COLORS];

    DrawingObjects();
    void Draw();
    int GetType(void);
    virtual void SetColor(Color color);
    Color GetColor();
    void SetStandardColor(STANDARD_COLOR color);
    STANDARD_COLOR GetStandardColor();

  //-------------------------------------------------------------
  //  Protected
  //-------------------------------------------------------------
  protected:
      Color mColor;
      int mType;
};
#endif