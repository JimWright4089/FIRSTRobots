#include "JORSUtils.h"

//----------------------------------------------------------------------------
//  Purpose:
//      convert a double to a long with a 10 bit decimal
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
long doubleToLong(double x)
{
  return (long)(x*1024.0);
}

//----------------------------------------------------------------------------
//  Purpose:
//      Chop a long up and put it into a buffer
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
void putU32IntoU8Array(char* data, int location, long value)
{
    data[location] = (char)((value >> 24) & 0xFF);
    data[location + 1] = (char)((value >> 16) & 0xFF);
    data[location + 2] = (char)((value >> 8) & 0xFF);
    data[location + 3] = (char)(value & 0xFF);
}

//----------------------------------------------------------------------------
//  Purpose:
//      Chop a int up and put it into a buffer
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
void putU16IntoU8Array(char* data, int location, int value)
{
    data[location + 1] = (char)((value >> 8) & 0xFF);
    data[location + 0] = (char)(value & 0xFF);
}

