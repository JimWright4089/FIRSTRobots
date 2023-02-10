/*****************************************************************************
 **
 **  Project:                      Stealth Battery Charger
 **
 **                             Copyright (c) 2023
 **                                James A Wright
 **                            All Rights Reserved
 **
 **  File Description:
 **      Runs the display
 **
 *****************************************************************************/

//--------------------------------------------------------------------
//  Included Files
//--------------------------------------------------------------------
#include <SPI.h>
#include "Adafruit_GFX.h"
#include "Adafruit_HX8357.h"
#include "DrawingObjects.h"
#include "Box.h"
#include "Text.h"

//--------------------------------------------------------------------
//  Pin Defines
//--------------------------------------------------------------------
#define TFT_CS    5 
#define TFT_DC    6 
#define TFT_RST   9 

//--------------------------------------------------------------------
//  Constants
//--------------------------------------------------------------------
const int MAX_ITEMS = 45;

//--------------------------------------------------------------------
//  Enum
//--------------------------------------------------------------------
enum SYNOPTICS
{
  C1_SINGLE_CHARGER = 0,
  C3_THREE_CHARGER,
  B_1ST_COL_TOP,
  B_1ST_COL_BOTTOM,
  B_2ND_COL_TOP,
  B_2ND_COL_BOTTOM,
  B_3RD_COL_TOP,
  B_3RD_COL_BOTTOM,
  B_HOT_SLOT,
  B_SPARE_SLOT,
  B_TEST_SLOT,
  L_HOT_CHARGE,
  L_1ST_COL_1ST_SEG,
  L_1ST_COL_2ND_SEG,
  L_1ST_COL_1ST_SLOT,
  L_1ST_COL_2ND_SLOT,
  L_2ND_COL_1ST_SEG,
  L_2ND_COL_2ND_SEG,
  L_2ND_COL_1ST_SLOT,
  L_2ND_COL_2ND_SLOT,
  L_3RD_COL_1ST_SEG,
  L_3RD_COL_2ND_SEG,
  L_3RD_COL_1ST_SLOT,
  L_3RD_COL_2ND_SLOT,
  A1_SINGLE_AMPS,
  A2_FIRST_AMPS,
  A3_SECOND_AMPS,
  A4_THIRD_AMPS,
  ID_HOT_SLOT,
  ID_TEST_SLOT,
  ID_SPARE_SLOT,
  ID_1ST_SLOT,
  ID_2ND_SLOT,
  ID_3RD_SLOT,
  ID_4TH_SLOT,
  ID_5TH_SLOT,
  ID_6TH_SLOT,
  M_HOT_SLOT,
  M_TEST_SLOT,
  M_1ST_SLOT,
  M_2ND_SLOT,
  M_3RD_SLOT,
  M_4TH_SLOT,
  M_5TH_SLOT,
  M_6TH_SLOT,
  END_DRAWING_LIST  //This must be the last item in the list
};

//--------------------------------------------------------------------
//  Globals
//--------------------------------------------------------------------
Adafruit_HX8357 gTft = Adafruit_HX8357(TFT_CS, TFT_DC, TFT_RST);

//--------------------------------------------------------------------
//  Locals
//--------------------------------------------------------------------
DrawingObjects* mItems[MAX_ITEMS];

/*****************************************************************************
 **
 **  Purpose:
 **      Setup the code
 **
 **  Notes: none
 **
 *****************************************************************************/
void setup() 
{
  Serial.begin(115200);

  //====================================================================
  // Setup the items
  //====================================================================
  mItems[C1_SINGLE_CHARGER] = new Box(13, 106, 79, 139, 4, 0xFFFF);
  mItems[C3_THREE_CHARGER] = new Box(79, 13, 413, 46, 4, 0xFFFF);
  mItems[B_1ST_COL_TOP] = new  Box(119, 106, 186, 139, 4, 0xFFFF);
  mItems[B_1ST_COL_BOTTOM] = new Box(119, 199, 186, 233, 4, 0xFFFF);

  mItems[B_2ND_COL_TOP] = new Box(226, 106, 293, 139, 4, 0xFFFF);
  mItems[B_2ND_COL_BOTTOM] = new Box(226, 199, 293, 233, 4, 0xFFFF);
  mItems[B_3RD_COL_TOP] = new Box(333, 106, 399, 139, 4, 0xFFFF);
  mItems[B_3RD_COL_BOTTOM] = new Box(333, 199, 399, 233, 4, 0xFFFF);

  mItems[B_HOT_SLOT] = new Box(13, 273, 79, 306, 4, 0xFFFF);
  mItems[B_SPARE_SLOT] = new Box(119, 273, 186, 306, 4, 0xFFFF);
  mItems[B_TEST_SLOT] = new Box(346, 273, 413, 306, 4, 0xFFFF);

  mItems[L_HOT_CHARGE] = new Line(46, 139, 46, 273, 4, 0xFFFF);

  mItems[L_1ST_COL_1ST_SEG] = new Line(99, 50, 99, 125, 4, 0xFFFF);
  mItems[L_1ST_COL_2ND_SEG] = new Line(99, 126, 99, 219, 4, 0xFFFF);
  mItems[L_1ST_COL_1ST_SLOT] = new Line(99, 123, 119, 123, 4, 0xFFFF);
  mItems[L_1ST_COL_2ND_SLOT] = new Line(99, 219, 119, 219, 4, 0xFFFF);

  mItems[L_2ND_COL_1ST_SEG] = new Line(206, 50, 206, 125, 4, 0xFFFF);
  mItems[L_2ND_COL_2ND_SEG] = new Line(206, 126, 206, 219, 4, 0xFFFF);
  mItems[L_2ND_COL_1ST_SLOT] = new Line(206, 123, 226, 123, 4, 0xFFFF);
  mItems[L_2ND_COL_2ND_SLOT] = new Line(206, 219, 226, 219, 4, 0xFFFF);

  mItems[L_3RD_COL_1ST_SEG] = new Line(313, 51, 313, 122, 4, 0xFFFF);
  mItems[L_3RD_COL_2ND_SEG] = new Line(313, 123, 313, 219, 4, 0xFFFF);
  mItems[L_3RD_COL_1ST_SLOT] = new Line(313, 123, 333, 123, 4, 0xFFFF);
  mItems[L_3RD_COL_2ND_SLOT] = new Line(313, 219, 333, 219, 4, 0xFFFF);

  mItems[A1_SINGLE_AMPS] = new Text(28, 118, 2, 0xFFFF, (char*)"20A");
  mItems[A2_FIRST_AMPS] = new Text(133, 27, 2, 0xFFFF, (char*)"20A");
  mItems[A3_SECOND_AMPS] = new Text(231, 27, 2, 0xFFFF, (char*)"20A");
  mItems[A4_THIRD_AMPS] = new Text(345, 27, 2, 0xFFFF, (char*)"20A");

  mItems[ID_HOT_SLOT] = new Text(36, 284, 2, 0xFFFF, (char*)" 1");
  mItems[ID_TEST_SLOT] = new Text(365, 284, 2, 0xFFFF, (char*)" 3");
  mItems[ID_SPARE_SLOT] = new Text(143, 284, 2, 0xFFFF, (char*)" 2");
  mItems[ID_1ST_SLOT] = new Text(143, 118, 2, 0xFFFF, (char*)" 4");
  mItems[ID_2ND_SLOT] = new Text(251, 118, 2, 0xFFFF, (char*)"10");
  mItems[ID_3RD_SLOT] = new Text(360, 118, 2, 0xFFFF, (char*)"11");
  mItems[ID_4TH_SLOT] = new Text(143, 212, 2, 0xFFFF, (char*)"12");
  mItems[ID_5TH_SLOT] = new Text(251, 212, 2, 0xFFFF, (char*)"14");
  mItems[ID_6TH_SLOT] = new Text(360, 212, 2, 0xFFFF, (char*)"67");

  mItems[M_HOT_SLOT] = new Text(18, 80, 2, 0xFFFF, (char*)"12.5V");
  mItems[M_TEST_SLOT] = new Text(272, 284, 2, 0xFFFF, (char*)"12.5V");
  mItems[M_1ST_SLOT] = new Text(117, 80, 2, 0xFFFF, (char*)" 22C");
  mItems[M_2ND_SLOT] = new Text(225, 80, 2, 0xFFFF, (char*)"12.5V");
  mItems[M_3RD_SLOT] = new Text(339, 80, 2, 0xFFFF, (char*)"13.5V");
  mItems[M_4TH_SLOT] = new Text(117, 175, 2, 0xFFFF, (char*)"12.5V");
  mItems[M_5TH_SLOT] = new Text(225, 175, 2, 0xFFFF, (char*)"14.5V");
  mItems[M_6TH_SLOT] = new Text(339, 175, 2, 0xFFFF, (char*)"67.8V");

  //====================================================================
  // Start the display
  //====================================================================
  gTft.begin();
  gTft.setRotation(1);
  gTft.fillScreen(HX8357_BLACK);
  delay(200);

  for(int i=0;i<MAX_ITEMS;i++)
  {
    mItems[i]->SetStandardColor(DrawingObjects::STANDARD_COLOR::WHITE);

    switch(mItems[i]->GetType())
    {
      case(DrawingObjects::TYPE_BOX):
        ((Box*)mItems[i])->Draw();
        break;
      case(DrawingObjects::TYPE_LINE):
        ((Line*)mItems[i])->Draw();
        break;
      case(DrawingObjects::TYPE_TEXT):
        ((Text*)mItems[i])->Draw();
        break;
    }
  }
}

Color gColor = 0;
int16_t gAmps=0;

/*****************************************************************************
 **
 **  Purpose:
 **      Idle Loop
 **
 **  Notes: none
 **
 *****************************************************************************/
void loop(void) 
{
  mItems[0]->SetStandardColor((DrawingObjects::STANDARD_COLOR)gColor);

  if(0 == (gAmps%20))
  {
    gColor = (gColor+1)%16;
  }
  gAmps--;
  if(gAmps<-100)
  {
    gAmps = 100;
  }
  ((Text*)mItems[A1_SINGLE_AMPS])->SetAmps(gAmps);

  delay(100);
}

