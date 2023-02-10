/*****************************************************************************
 **
 **  Project:                      Stealth Battery Charger
 **
 **                             Copyright (c) 2023
 **                                James A Wright
 **                            All Rights Reserved
 **
 **  File Description:
 **      This displays a line
 **
 *****************************************************************************/

//--------------------------------------------------------------------
//  Included Files
//--------------------------------------------------------------------
#include "Line.h"
#include "Arduino.h"

//--------------------------------------------------------------------
//  External Data
//--------------------------------------------------------------------
extern Adafruit_HX8357 gTft;

/*****************************************************************************
 **
 **  Purpose:
 **      Constructor
 **
 **  Notes: none
 **
 *****************************************************************************/
Line::Line(int16_t oneX, int16_t oneY, int16_t twoX, int16_t twoY, int16_t size, Color color) : DrawingObjects()
{
  mOneX = oneX;
  mOneY = oneY;
  mTwoX = twoX;
  mTwoY = twoY;
  mColor = color;
  mSize = size;
  mType = DrawingObjects::TYPE_LINE;
}

/*****************************************************************************
 **
 **  Purpose:
 **      Set the color of the text
 **
 **  Notes: 
 **      None
 **
 *****************************************************************************/
void Line::SetColor(Color color)
{
  mColor = color;
  Draw();
}

/*****************************************************************************
 **
 **  Purpose:
 **      Draw a Line in the saved color
 **
 **  Notes: 
 **      None
 **
 *****************************************************************************/
void Line::Draw()
{
  if(mOneX == mTwoX)
  {
    for(int i=0;i<mSize;i++)
    {
      gTft.drawLine(mOneX+i, mOneY, mTwoX+i, mTwoY, mColor);
    }
  }
  else
  {
    for(int i=0;i<mSize;i++)
    {
      gTft.drawLine(mOneX, mOneY+i, mTwoX, mTwoY+i, mColor);
    }
  }
}
