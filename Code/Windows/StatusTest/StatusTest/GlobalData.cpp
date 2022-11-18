#include "GlobalData.h"
#include <stdio.h>
#include <string.h>

uint8_t gStatus = 0;
uint32_t  gIP = 0;
uint8_t gDocked = 0;
int16_t gXMeters = 0;
int16_t gYMeters = 0;
int16_t gTheta = 0;
float gLat = 0;
float gLong = 0;
uint8_t gCharge = 0;
int16_t gVolt = 0;
int16_t gCurrent = 0;
int16_t gTemp = 0;
uint16_t gRSCaution = 0;
uint16_t gRSWarning = 0;
uint16_t gRSStatus = 0;
uint16_t gRSInfo = 0;
uint8_t gMapName[LEN_MAP_NAME];
uint8_t gTotal[LEN_TOTAL];
uint8_t gStatusFlag[INDEX_MAX];
uint8_t gDisplayFlag[INDEX_MAX];
  
int getIndex(int command)
{
    switch(command)
    {
        case(LOC_STATUS):
            return INDEX_STATUS;
        case(LOC_IP):
            return INDEX_IP;
        case(LOC_DOCKED):
            return INDEX_DOCKED;
        case(LOC_X_METERS):
            return INDEX_X_METERS;
        case(LOC_Y_METERS):
            return INDEX_Y_METERS;
        case(LOC_THETA):
            return INDEX_THETA;
        case(LOC_LAT):
            return INDEX_LAT;
        case(LOC_LONG):
            return INDEX_LONG;
        case(LOC_CHARGE):
            return INDEX_CHARGE;
        case(LOC_VOLT):
            return INDEX_VOLT;
        case(LOC_CURRENT):
            return INDEX_CURRENT;
        case(LOC_TEMP):
            return INDEX_TEMP;
        case(LOC_RS_CAUTION):
            return INDEX_RS_CAUTION;
        case(LOC_RS_WARNING):
            return INDEX_RS_WARNING;
        case(LOC_RS_STATUS):
            return INDEX_RS_STATUS;
        case(LOC_RS_INFO):
            return INDEX_RS_INFO;
        case(LOC_MAP_NAME):
            return INDEX_MAP_NAME;
    }
}

int getCommand(int index)
{
    switch(index)
    {
        case(INDEX_STATUS):
            return LOC_STATUS;
        case(INDEX_IP):
            return LOC_IP;
        case(INDEX_DOCKED):
            return LOC_DOCKED;
        case(INDEX_X_METERS):
            return LOC_X_METERS;
        case(INDEX_Y_METERS):
            return LOC_Y_METERS;
        case(INDEX_THETA):
            return LOC_THETA;
        case(INDEX_LAT):
            return LOC_LAT;
        case(INDEX_LONG):
            return LOC_LONG;
        case(INDEX_CHARGE):
            return LOC_CHARGE;
        case(INDEX_VOLT):
            return LOC_VOLT;
        case(INDEX_CURRENT):
            return LOC_CURRENT;
        case(INDEX_TEMP):
            return LOC_TEMP;
        case(INDEX_RS_CAUTION):
            return LOC_RS_CAUTION;
        case(INDEX_RS_WARNING):
            return LOC_RS_WARNING;
        case(INDEX_RS_STATUS):
            return LOC_RS_STATUS;
        case(INDEX_RS_INFO):
            return LOC_RS_INFO;
        case(INDEX_MAP_NAME):
            return LOC_MAP_NAME;
    }
}

void* getMemory(int index)
{
    switch(index)
    {
        case(INDEX_STATUS):
            return (void*)&gStatus;
        case(INDEX_IP):
            return (void*)&gIP;
        case(INDEX_DOCKED):
            return (void*)&gDocked;
        case(INDEX_X_METERS):
            return (void*)&gXMeters;
        case(INDEX_Y_METERS):
            return (void*)&gYMeters;
        case(INDEX_THETA):
            return (void*)&gTheta;
        case(INDEX_LAT):
            return (void*)&gLat;
        case(INDEX_LONG):
            return (void*)&gLong;
        case(INDEX_CHARGE):
            return (void*)&gCharge;
        case(INDEX_VOLT):
            return (void*)&gVolt;
        case(INDEX_CURRENT):
            return (void*)&gCurrent;
        case(INDEX_TEMP):
            return (void*)&gTemp;
        case(INDEX_RS_CAUTION):
            return (void*)&gRSCaution;
        case(INDEX_RS_WARNING):
            return (void*)&gRSWarning;
        case(INDEX_RS_STATUS):
            return (void*)&gRSStatus;
        case(INDEX_RS_INFO):
            return (void*)&gRSInfo;
        case(INDEX_MAP_NAME):
            return (void*)&gMapName;
    }
}

int getGlobalSize(int index)
{
    switch(index)
    {
        case(INDEX_STATUS):
            return LEN_STATUS;
        case(INDEX_IP):
            return LEN_IP;
        case(INDEX_DOCKED):
            return LEN_DOCKED;
        case(INDEX_X_METERS):
            return LEN_X_METERS;
        case(INDEX_Y_METERS):
            return LEN_Y_METERS;
        case(INDEX_THETA):
            return LEN_THETA;
        case(INDEX_LAT):
            return LEN_LAT;
        case(INDEX_LONG):
            return LEN_LONG;
        case(INDEX_CHARGE):
            return LEN_CHARGE;
        case(INDEX_VOLT):
            return LEN_VOLT;
        case(INDEX_CURRENT):
            return LEN_CURRENT;
        case(INDEX_TEMP):
            return LEN_TEMP;
        case(INDEX_RS_CAUTION):
            return LEN_RS_CAUTION;
        case(INDEX_RS_WARNING):
            return LEN_RS_WARNING;
        case(INDEX_RS_STATUS):
            return LEN_RS_STATUS;
        case(INDEX_RS_INFO):
            return LEN_RS_INFO;
        case(INDEX_MAP_NAME):
            return LEN_MAP_NAME;
    }
}
 
void checkStatus(void)
{
  for(int i=0;i<INDEX_MAX;i++)
  {
    if(1 == gStatusFlag[i])
    {
      int command = getCommand(i);
      int theLoc = command - LOC_START;
      memcpy(getMemory(i),&gTotal[theLoc],getGlobalSize(i));
      gStatusFlag[i] = 0;
    }
  }  
}
