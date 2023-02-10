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

//--------------------------------------------------------------------
//  Included Files
//--------------------------------------------------------------------
#include "DrawingObjects.h"
#include "Arduino.h"

/*****************************************************************************
 **
 **  Purpose:
 **      Constructor
 **
 **  Notes: none
 **
 *****************************************************************************/
DrawingObjects::DrawingObjects()
{
  mStandardColors[0] = 0x0000;              // BLACK
  mStandardColors[1] = 0xFFFF;              // WHITE
  mStandardColors[2] = 31727;               // GRAY
  mStandardColors[3] = 0xF800;              // RED
  mStandardColors[4] = 64192;               // ORANGE
  mStandardColors[5] = 0xFFE0;              // YELLOW
  mStandardColors[6] = 0x07E0;              // GREEN
  mStandardColors[7] = 0x001F;              // BLUE
  mStandardColors[8] = 37272;               // PURPLE
  mStandardColors[9] = 0xF81F;              // MAGENTA
  mStandardColors[10] = 0x07FF;             // CYAN
  mStandardColors[11] = 10857;              // DK_GRAY
  mStandardColors[12] = 7295;               // LT_BLUE
  mStandardColors[13] = 31712;              // DK_GREEN
  mStandardColors[14] = 64309;              // PINK
  mStandardColors[15] = 46113;              // GOLD
}

/*****************************************************************************
 **
 **  Purpose:
 **      Return the colors
 **
 **  Notes: 
 **      None
 **
 *****************************************************************************/
Color DrawingObjects::GetColor()
{
  return mColor;
}

/*****************************************************************************
 **
 **  Purpose:
 **      Set one of the standard colors
 **
 **  Notes: 
 **      None
 **
 *****************************************************************************/
void DrawingObjects::SetStandardColor(STANDARD_COLOR color)
{
  SetColor(mStandardColors[(int)color]);
}

/*****************************************************************************
 **
 **  Purpose:
 **      Get the standard color
 **
 **  Notes: 
 **      None
 **
 *****************************************************************************/
DrawingObjects::STANDARD_COLOR DrawingObjects::GetStandardColor()
{
  for(int i=0;i<MAX_STANDARD_COLORS;i++)
  {
    if(mColor == mStandardColors[i])
    {
      return (STANDARD_COLOR)i;
    }
  }
  return DrawingObjects::STANDARD_COLOR::BLACK;
}

/*****************************************************************************
 **
 **  Purpose:
 **      Sets the color
 **
 **  Notes: 
 **      None
 **
 *****************************************************************************/
void DrawingObjects::SetColor(Color color)
{
  mColor = color;
}

/*****************************************************************************
 **
 **  Purpose:
 **      Draw the item
 **
 **  Notes: 
 **      None
 **
 *****************************************************************************/
void DrawingObjects::Draw()
{

}

/*****************************************************************************
 **
 **  Purpose:
 **      Return the type of item
 **
 **  Notes: 
 **      None
 **
 *****************************************************************************/
int DrawingObjects::GetType(void)
{
  return mType;
}
