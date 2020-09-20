//----------------------------------------------------------------------------
//
//  $Workfile: config.h$
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
#pragma once

//----------------------------------------------------------------------------
//  Includes
//----------------------------------------------------------------------------
#include <string>
#include <jsoncpp/json/json.h>
using namespace std;

//----------------------------------------------------------------------------
//Class Declarations
//----------------------------------------------------------------------------
//
//Class Name: Config
//
//Purpose:
//  the singleton that holds the config
//
//----------------------------------------------------------------------------
class Config
{
  //----------------------------------------------------------------------------
  //  Private
  //----------------------------------------------------------------------------    
  private:
    //----------------------------------------------------------------------------
    //  Consts
    //----------------------------------------------------------------------------    
    const string ORIGINAL_FOLDER = "/original";
    const string DISTANCE_FOLDER = "/distance";
    const string TRESHOLD_FOLDER = "/treshold";

    //----------------------------------------------------------------------------
    //  Attributes
    //----------------------------------------------------------------------------    
    static Config* instance;
    string mOutputPath = "/media/jim/DATA";
    string mOutputPrefix = "pref-";
    string mCurrentOutputPath = "";
    bool   mSaveOutput = false;
    long   mSaveOutputTime = 1000;
    string mColorName = "All";
    long   mHueLow = 0;
    long   mHueHigh = 255;
    long   mSatLow = 0;
    long   mSatHigh = 255;
    long   mValLow = 0;
    long   mValHigh = 255;
    long   mRGBG = 255;
    long   mRGBR = 255;
    long   mRGBB = 255;
    long   mMinArea = 1000;

    Json::Value mConfigData;
    
    Config();

  //----------------------------------------------------------------------------
  //  Private
  //----------------------------------------------------------------------------    
  public:
    static Config* getInstance();
    string getOutputPath();
    long   getSaveOutputTime();
    bool   getSaveOutput();
    string getOutputPrefix();
    string getOutputOriginalPath();
    string getOutputDistancePath();
    string getOutputThresholdPath();
    string getCurrentOutputPath();
    string getColorName();
    long   getHueLow();
    long   getHueHigh();
    long   getSatLow();
    long   getSatHigh();
    long   getValLow();
    long   getValHigh();
    long   getRGBG();
    long   getRGBR();
    long   getRGBB();
    long   getMinArea();
    bool   setOutputPath(string currentOutputPath);
    string toString();
};






