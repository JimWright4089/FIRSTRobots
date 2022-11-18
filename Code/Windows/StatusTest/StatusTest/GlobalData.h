//----------------------------------------------------------------------------
//
//  $Workfile: Display.h$
//
//  $Revision: X$
//
//  Project:    JimsFridge
//
//                            Copyright (c) 2017
//                                Jim Wright
//                            All Rights Reserved
//
//  Modification History:
//  $Log:
//  $
//
//----------------------------------------------------------------------------
#ifndef GLOBAL_DATA_H
#define GLOBAL_DATA_H
#include <stdint.h>

#define SCREEN_INFO     0
#define SCREEN_LOCATION 1
#define SCREEN_BATTERY  2
#define SCREEN_ROB_STAT 3

#define LOC_START     0x81
#define LOC_STATUS    0x81
#define LEN_STATUS    1
#define INDEX_STATUS  0
#define SCSET_STATUS  SCREEN_INFO

#define LOC_IP        0x82
#define LOC_IP1       (LOC_IP)
#define LOC_IP2       (LOC_IP + 1)
#define LOC_IP3       (LOC_IP + 2)
#define LOC_IP4       (LOC_IP + 3)
#define LEN_IP        4
#define INDEX_IP      1
#define SCSET_IP      SCREEN_INFO

#define LOC_DOCKED    0x86
#define LEN_DOCKED    1
#define INDEX_DOCKED  2
#define SCSET_DOCKED  SCREEN_INFO

#define LOC_X_METERS  0x87
#define LOC_X_METERS1 (LOC_X_METERS)
#define LOC_X_METERS2 (LOC_X_METERS + 1)
#define LEN_X_METERS  2
#define INDEX_X_METERS 3

#define LOC_Y_METERS  0x89
#define LOC_Y_METERS1 (LOC_Y_METERS)
#define LOC_Y_METERS2 (LOC_Y_METERS + 1)
#define LEN_Y_METERS  2
#define INDEX_Y_METERS 4

#define LOC_THETA     0x8b
#define LOC_THETA1    (LOC_THETA)
#define LOC_THETA2    (LOC_THETA + 1)
#define LEN_THETA     2
#define INDEX_THETA   5

#define LOC_LAT       0x8e
#define LOC_LAT1      (LOC_LAT)
#define LOC_LAT2      (LOC_LAT+1)
#define LOC_LAT3      (LOC_LAT+2)
#define LOC_LAT4      (LOC_LAT+3)
#define LEN_LAT       4
#define INDEX_LAT     6

#define LOC_LONG      0x92
#define LOC_LONG1     (LOC_LONG)
#define LOC_LONG2     (LOC_LONG+1)
#define LOC_LONG3     (LOC_LONG+2)
#define LOC_LONG4     (LOC_LONG+3)
#define LEN_LONG      4
#define INDEX_LONG    7

#define LOC_CHARGE    0x96
#define LEN_CHARGE    1
#define INDEX_CHARGE  8

#define LOC_VOLT      0x97
#define LOC_VOLT1     (LOC_VOLT)
#define LOC_VOLT2     (LOC_VOLT + 1)
#define LEN_VOLT      2
#define INDEX_VOLT    9

#define LOC_CURRENT   0x99
#define LOC_CURRENT1  (LOC_CURRENT)
#define LOC_CURRENT2  (LOC_CURRENT + 1)
#define LEN_CURRENT   2
#define INDEX_CURRENT 10

#define LOC_TEMP      0x9b
#define LOC_TEMP1     (LOC_TEMP)
#define LOC_TEMP2     (LOC_TEMP + 1)
#define LEN_TEMP      2
#define INDEX_TEMP    11

#define LOC_RS_CAUTION  0xa0
#define LOC_RS_CAUTION1 (LOC_RS_CAUTION)
#define LOC_RS_CAUTION2 (LOC_RS_CAUTION+1)
#define LEN_RS_CAUTION  2
#define INDEX_RS_CAUTION 12

#define LOC_RS_WARNING  0xa2
#define LOC_RS_WARNING1 (LOC_RS_WARNING)
#define LOC_RS_WARNING2 (LOC_RS_WARNING+1)
#define LEN_RS_WARNING  2
#define INDEX_RS_WARNING 13

#define LOC_RS_STATUS   0xa4
#define LOC_RS_STATUS1  (LOC_RS_STATUS)
#define LOC_RS_STATUS2  (LOC_RS_STATUS+1)
#define LEN_RS_STATUS   2
#define INDEX_RS_STATUS 14

#define LOC_RS_INFO     0xa6
#define LOC_RS_INFO1    (LOC_RS_INFO)
#define LOC_RS_INFO2    (LOC_RS_INFO+1)
#define LEN_RS_INFO     2
#define INDEX_RS_INFO   15

#define LOC_MAP_NAME    0xb0
#define LEN_MAP_NAME    32
#define INDEX_MAP_NAME  16
#define INDEX_MAX       17

#define LEN_TOTAL      (LEN_STATUS+LEN_IP+LEN_DOCKED+LEN_X_METERS+LEN_Y_METERS+LEN_THETA+LEN_LAT+LEN_LONG+LEN_CHARGE+LEN_VOLT+LEN_CURRENT+LEN_TEMP+LEN_RS_CAUTION+LEN_RS_WARNING+LEN_RS_STATUS+LEN_RS_INFO+LEN_MAP_NAME)

extern uint8_t gStatus;
extern uint32_t  gIP;
extern uint8_t gDocked;
extern int16_t gXMeters;
extern int16_t gYMeters;
extern int16_t gTheta;
extern float gLat;
extern float gLong;
extern uint8_t gCharge;
extern int16_t gVolt;
extern int16_t gCurrent;
extern int16_t gTemp;
extern uint16_t gRSCaution;
extern uint16_t gRSWarning;
extern uint16_t gRSStatus;
extern uint16_t gRSInfo;
extern uint8_t gMapName[LEN_MAP_NAME];
extern uint8_t gTotal[LEN_TOTAL];
extern uint8_t gStatusFlag[INDEX_MAX];
extern uint8_t gDisplayFlag[INDEX_MAX];



int getIndex(int command);
void checkStatus(void);
int getCommand(int index);
void* getMemory(int index);
int getGlobalSize(int index);

#endif
