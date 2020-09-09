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
    bool   setOutputPath(string currentOutputPath);
    string toString();
};






