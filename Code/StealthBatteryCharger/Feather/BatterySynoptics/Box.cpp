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

//--------------------------------------------------------------------
//  Included Files
//--------------------------------------------------------------------
#include "Box.h"

/*****************************************************************************
 **
 **  Purpose:
 **      Constructor
 **
 **  Notes: none
 **
 *****************************************************************************/
Box::Box(int16_t oneX, int16_t oneY, int16_t twoX, int16_t twoY, int16_t size, Color color) : DrawingObjects(),
  mOne(oneX, oneY, twoX, oneY, size, color),
  mTwo(twoX, oneY, twoX, twoY, size, color),
  mThree(twoX, twoY, oneX, twoY, size, color),
  mFour(oneX, oneY, oneX, twoY, size, color)
{
    mType = DrawingObjects::TYPE_BOX;
}

/*****************************************************************************
 **
 **  Purpose:
 **      Seet the colors of the lines
 **
 **  Notes: 
 **      None
 **
 *****************************************************************************/
void Box::SetColor(Color color)
{
  mOne.SetColor(color);
  mTwo.SetColor(color);
  mThree.SetColor(color);
  mFour.SetColor(color);
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
void Box::Draw()
{
  mOne.Draw();
  mTwo.Draw();
  mThree.Draw();
  mFour.Draw();
}

