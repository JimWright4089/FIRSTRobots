//----------------------------------------------------------------------------
//
//  $Workfile: utils.cpp$
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
//     This is the main entry poiint
//
//----------------------------------------------------------------------------

//----------------------------------------------------------------------------
//  Includes
//----------------------------------------------------------------------------
#include "utils.h"
#include <iostream>
#include <experimental/filesystem>
namespace fs = std::experimental::filesystem;

//----------------------------------------------------------------------------
//  Purpose:
//   The convert a bool to string
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
string boolToString(bool value)
{
  if(true == value)
  {
    return "true";
  }
  
  return "false";
}

//----------------------------------------------------------------------------
//  Purpose:
//   The finds the next path in the list of paths
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
string findNewPath(string outputPath, string outputPrefix)
{
  int count = 0;
  string outputFolder = outputPrefix + to_string(count);
  string fullPath = outputPath + "/" + outputFolder;
  
  while(true == fs::exists(fullPath))
  {
    count++;
    outputFolder = outputPrefix + to_string(count);
    fullPath = outputPath + "/" + outputFolder;
  }
  
  return fullPath;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Create a directory
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
bool createDirectory(string path)
{
  if(true == fs::exists(path))
  {
    return false;
  }
  
  return fs::create_directory(path);
}

