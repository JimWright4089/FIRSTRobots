/*****************************************************************************
 **
 **  Project:                      Stealth Battery Charger
 **
 **                             Copyright (c) 2023
 **                                James A Wright
 **                            All Rights Reserved
 **
 **  File Description:
 **      This displays A Box
 **
 *****************************************************************************/
#ifndef BOX
#define BOX

//--------------------------------------------------------------------
//  Included Files
//--------------------------------------------------------------------
#include <Adafruit_GFX.h>
#include "DrawingObjects.h"
#include "Line.h"

//--------------------------------------------------------------------
//  Class Define
//--------------------------------------------------------------------
//  Name:
//     Box
//  
//  Purpose:
//     The Box Item
//  
//--------------------------------------------------------------------
class Box : public DrawingObjects
{
  //-------------------------------------------------------------
  //  Public
  //-------------------------------------------------------------
  public:
    Box(int16_t oneX, int16_t oneY, int16_t twoX, int16_t twoY, int16_t size, Color color);
    void Draw();
    void SetColor(Color color);

  //-------------------------------------------------------------
  //  Protected
  //-------------------------------------------------------------
  protected:
    Line mOne;
    Line mTwo;
    Line mThree;
    Line mFour;
};
#endif
