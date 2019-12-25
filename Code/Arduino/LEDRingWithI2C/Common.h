#ifndef COMMON_H
#define COMMON_H

typedef unsigned char uint8;
typedef unsigned short uint16;
typedef unsigned long uint32;

const uint8 SER_START = 0xA0;
const uint8 SER_END = 0xA1;

const uint8 LOC_START      = 0;
const uint8 LOC_PI_STATUS  = 1;
const uint8 LOC_LED_STATUS = 2;
const uint8 LOC_DATA_START = 3;
const uint8 LOC_END        = 1;
const uint8 LOC_CHECK_BYTE = 2;
const uint8 LOC_DATA_END   = 3;

const uint8  MAX_TEAM_COLOR      = 34;
const uint8  MAX_RECEIVE         = 22;
const uint8  MAX_SEND            = 30;
const uint8  MAX_LEDS            = 16;
const uint8  MAX_PACKET          = 80;

const uint8  BAD_VALUE           = 255;

const uint8 MAX_COLORS    = 37;
const uint8 COLOR_BLUE    =  0;
const uint8 COLOR_GREEN   =  1;
const uint8 COLOR_RED     =  2;
const uint8 COLOR_YELLOW  =  3;
const uint8 COLOR_CYAN    =  5;
const uint8 COLOR_MAGENTA =  6;
const uint8 COLOR_WHITE   = 34;
const uint8 COLOR_BLACK   = 35;
const uint8 COLOR_BRIGHT_GREEN   = 36;
const uint8 COLOR_TEAM    = 60;
const uint8 COLOR_NO_COMM = 61;
const uint8 COLOR_BUTTON_DOWN = 62; 

extern const uint8 colorArray[];
extern const uint8 colorArrayText[];

#endif

