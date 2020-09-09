//----------------------------------------------------------------------------
//
//  $Workfile: utils.h$
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
//     This is the util functions
//
//----------------------------------------------------------------------------

//----------------------------------------------------------------------------
//  Includes
//----------------------------------------------------------------------------
#include <string>
using namespace std;

//----------------------------------------------------------------------------
//  Public utils
//----------------------------------------------------------------------------    
string boolToString(bool value);
string findNewPath(string outputPath, string outputPrefix);
bool   createDirectory(string path);

