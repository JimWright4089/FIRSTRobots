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

