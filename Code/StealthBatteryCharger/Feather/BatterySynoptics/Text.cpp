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

//--------------------------------------------------------------------
//  Included Files
//--------------------------------------------------------------------
#include <string.h>
#include "Text.h"
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
Text::Text(int16_t x, int16_t y, int16_t size, Color color, char *text) : DrawingObjects()
{
  mX = x;
  mY = y;
  mColor = color;
  mSize = size;
  strncpy(mText,text,MAX_TEXT);
  mType = DrawingObjects::TYPE_TEXT;
}

/*****************************************************************************
 **
 **  Purpose:
 **      Set the color of the text
 **
 **  Notes: 
 **      first erase the old text before drawing the new text
 **
 *****************************************************************************/
void Text::SetColor(Color color)
{
  Draw(0x0000);
  mColor = color;
  Draw();
}

/*****************************************************************************
 **
 **  Purpose:
 **      Draw the Text the saved color
 **
 **  Notes: 
 **      None
 **
 *****************************************************************************/
void Text::Draw()
{
  Draw(mColor);
}

/*****************************************************************************
 **
 **  Purpose:
 **      Draw the Text with any color
 **
 **  Notes: 
 **      None
 **
 *****************************************************************************/
void Text::Draw(Color color)
{
  gTft.setTextColor(color);
  gTft.setTextSize(mSize);
  gTft.setCursor(mX, mY);
  gTft.println(mText);
}

/*****************************************************************************
 **
 **  Purpose:
 **      Sets new text
 **
 **  Notes: 
 **      first erase the old text before drawing the new text
 **
 *****************************************************************************/
void Text::SetText(char *text)
{
  Draw(0x0000);
  strncpy(mText,text,MAX_TEXT);
  Draw();
}

/*****************************************************************************
 **
 **  Purpose:
 **      Sets text to an amperage
 **
 **  Notes: 
 **      first erase the old text before drawing the new text
 **
 *****************************************************************************/
void Text::SetAmps(int16_t amps)
{
  Draw(0x0000);
  snprintf(mText,MAX_TEXT,"%dA",amps);
  Draw();
}

