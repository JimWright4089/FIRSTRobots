//----------------------------------------------------------------------------
//
//  $Workfile: config.cpp$
//
//  $Revision: X$
//
//  Project:    Ball Locator
//
//                            Copyright (c) 2020
//                               James A Wright
//                            All Rights Reserved
//
//  Modification History:
//  $Log:
//  $
//
//  Notes:
//     This is the class for configuring things
//
//----------------------------------------------------------------------------

//----------------------------------------------------------------------------
//  Includes
//----------------------------------------------------------------------------
#include <iostream>
#include <fstream>
#include "config.h"
#include "utils.h"

//----------------------------------------------------------------------------
//  Class Statics
//----------------------------------------------------------------------------
/* Null, because instance will be initialized on demand. */
Config* Config::instance = 0;

//----------------------------------------------------------------------------
//  Purpose:
//   Return the pointer to the singleton
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
Config* Config::getInstance()
{
  if (instance == 0)
  {
    instance = new Config();
  }

  return instance;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Constructor
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
Config::Config()
{
  ifstream ifs("config.json");
  Json::Reader reader;

  reader.parse(ifs, mConfigData);
  
  mOutputPath     = mConfigData["outputPath"].asString();
  mOutputPrefix   = mConfigData["outputPrefix"].asString();
  mSaveOutput     = (1 == mConfigData["saveOutput"].asUInt()) ? true : false;
  mSaveOutputTime = mConfigData["saveOutputTime"].asUInt();
  
  mColorName      = mConfigData["colorName"].asString();
  mHueLow         = mConfigData["colorCode"]["h"][0].asUInt();
  mHueHigh        = mConfigData["colorCode"]["h"][1].asUInt();
  mSatLow         = mConfigData["colorCode"]["s"][0].asUInt();
  mSatHigh        = mConfigData["colorCode"]["s"][1].asUInt();
  mValLow         = mConfigData["colorCode"]["v"][0].asUInt();
  mValHigh        = mConfigData["colorCode"]["v"][1].asUInt();
  mRGBG           = mConfigData["colorRgb"][1].asUInt();
  mRGBR           = mConfigData["colorRgb"][0].asUInt();
  mRGBB           = mConfigData["colorRgb"][2].asUInt();
  mMinArea        = mConfigData["minArea"].asUInt();
}

//----------------------------------------------------------------------------
//  Purpose:
//   Return the output path
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
string Config::getOutputPath()
{
  return mOutputPath;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Return the bool to say if we need to save the output
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
bool Config::getSaveOutput()
{
  return mSaveOutput;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Return the output prefix
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
string Config::getOutputPrefix()
{
  return mOutputPrefix;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Return the current output path
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
string Config::getCurrentOutputPath()
{
  return mCurrentOutputPath;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Return the current output path
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
bool Config::setOutputPath(string currentOutputPath)
{
  mCurrentOutputPath = currentOutputPath;
  if(false == createDirectory(mCurrentOutputPath))
  {
    cout << "Can't create root directory \n";
    return false;
  }

  if(false == createDirectory(getOutputOriginalPath()))
  {
    cout << "Can't create original directory \n";
    return false;
  }

  if(false == createDirectory(getOutputDistancePath()))
  {
    cout << "Can't create root distance directory \n";
    return false;
  }

  if(false == createDirectory(getOutputThresholdPath()))
  {
    cout << "Can't create root threshold directory \n";
    return false;
  }
  return true;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Return the current original path
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
string Config::getOutputOriginalPath()
{
  return mCurrentOutputPath + ORIGINAL_FOLDER;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Return the current distance path
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
string Config::getOutputDistancePath()
{
  return mCurrentOutputPath + DISTANCE_FOLDER;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Return the current threshold path
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
string Config::getOutputThresholdPath()
{
  return mCurrentOutputPath + TRESHOLD_FOLDER;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Return the amount of time to wait for saving
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
long Config::getSaveOutputTime()
{
  return mSaveOutputTime;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Return the color name
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
string Config::getColorName()
{
  return mColorName;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Return the hue low
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
long Config::getHueLow()
{
  return mHueLow;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Return the hue high
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
long Config::getHueHigh()
{
  return mHueHigh;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Return the saturation low
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
long Config::getSatLow()
{
  return mSatLow;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Return the Saturation high
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
long Config::getSatHigh()
{
  return mSatHigh;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Return the val low
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
long Config::getValLow()
{
  return mValLow;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Return the val high
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
long Config::getValHigh()
{
  return mValHigh;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Return the green value of the RGB
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
long Config::getRGBG()
{
  return mRGBG;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Return the red value of the RGB
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
long Config::getRGBR()
{
  return mRGBR;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Return the blue value of the RGB
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
long Config::getRGBB()
{
  return mRGBB;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Return the minumum area
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
long Config::getMinArea()
{
  return mMinArea;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Return the string of all settigns
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
string Config::toString()
{
  string returnString  = "Output Path  :" + mOutputPath  + "\n";
         returnString += "Save Output  :" + boolToString(mSaveOutput)  + "\n";
         returnString += "Output Prefix:" + mOutputPrefix  + "\n";
         returnString += "Current Path :" + mCurrentOutputPath  + "\n";
  return returnString;
}

