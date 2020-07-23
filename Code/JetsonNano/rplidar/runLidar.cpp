//----------------------------------------------------------------------------
//
//  $Workfile: runLidar.cpp$
//
//  $Revision: X$
//
//  Project:    Team Redacted
//
//                            Copyright (c) 2020
//                                Jim Wright
//                            All Rights Reserved
//
//  Modification History:
//  $Log:
//  $
//
//----------------------------------------------------------------------------

//----------------------------------------------------------------------------
//  Includes
//----------------------------------------------------------------------------
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <pthread.h> 
#include "ntcore.h"
#include "rplidar.h" //RPLIDAR standard sdk, all-in-one header

using namespace rp::standalone::rplidar;

//----------------------------------------------------------------------------
//  File constants
//----------------------------------------------------------------------------

//#define DEBUG

#ifndef _countof
#define _countof(_Array) (int)(sizeof(_Array) / sizeof(_Array[0]))
#endif

#define MAX_POINTS_TO_REPORT 37

const uint16_t SLEEP_TIME = 20;

const int gLabels[] = {
    -90, -85, -80, -75, -70, -65, -60, -55, -50, -45,
    -40, -35, -30, -25, -20, -15, -10,  -5,   0,   5,
     10,  15,  20,  25,  30,  35,  40,  45,  50,  55,
     60,  65,  70,  75,  80,  85,  90 };

const char* gLabelStr[] = {
    "/Lidar/-90",
    "/Lidar/-85",
    "/Lidar/-80",
    "/Lidar/-75",
    "/Lidar/-70",
    "/Lidar/-65",
    "/Lidar/-60",
    "/Lidar/-55",
    "/Lidar/-50",
    "/Lidar/-45",
    "/Lidar/-40",
    "/Lidar/-35",
    "/Lidar/-30",
    "/Lidar/-25",
    "/Lidar/-20",
    "/Lidar/-15",
    "/Lidar/-10",
    "/Lidar/-5",
    "/Lidar/0",
    "/Lidar/5",
    "/Lidar/10",
    "/Lidar/15",
    "/Lidar/20",
    "/Lidar/25",
    "/Lidar/30",
    "/Lidar/35",
    "/Lidar/40",
    "/Lidar/45",
    "/Lidar/50",
    "/Lidar/55",
    "/Lidar/60",
    "/Lidar/65",
    "/Lidar/70",
    "/Lidar/75",
    "/Lidar/80",
    "/Lidar/85",
    "/Lidar/90"
};

const int gRawToPoints[] = {
18, -1, -1, -1, -1,	19, -1, -1, -1, -1, 20, -1, -1, -1, -1,	21, -1, -1, -1, -1,
22, -1, -1, -1, -1,	23, -1, -1, -1, -1, 24, -1, -1, -1, -1,	25, -1, -1, -1, -1,
26, -1, -1, -1, -1,	27, -1, -1, -1, -1, 28, -1, -1, -1, -1,	29, -1, -1, -1,-1,
30, -1, -1, -1, -1,	31, -1, -1, -1, -1, 32, -1, -1, -1, -1,	33, -1, -1, -1, -1,
34, -1, -1, -1, -1,	35, -1, -1, -1, -1, 36, -1, -1, -1, -1, -1, -1, -1, -1, -1,
-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,	 0, -1, -1, -1, -1,	 1, -1, -1, -1, -1,
 2, -1, -1, -1, -1,	 3, -1, -1, -1, -1,	 4, -1, -1, -1, -1,	 5, -1, -1, -1, -1,
 6, -1, -1, -1, -1,	 7, -1, -1, -1, -1,	 8, -1, -1, -1, -1,	 9, -1, -1, -1, -1,
10, -1, -1, -1, -1,	11, -1, -1, -1, -1,	12, -1, -1, -1, -1,	13, -1, -1, -1, -1,
14, -1, -1, -1, -1,	15, -1, -1, -1, -1,	16, -1, -1, -1, -1,	17, -1, -1, -1, -1
};

//----------------------------------------------------------------------------
//  Global Variable
//----------------------------------------------------------------------------
int gReportPoints[MAX_POINTS_TO_REPORT];
double gRawPoints[360];
bool gCtrlCPressed = false;
NT_Entry gNetTable[MAX_POINTS_TO_REPORT];

//----------------------------------------------------------------------------
//  Local Functions
//----------------------------------------------------------------------------
void* runThread(void* vargp);

//----------------------------------------------------------------------------
//  Purpose:
//   Delay miliseconds
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
static inline void delay(_word_size_t ms) 
{
    while (ms >= 1000) 
    {
        usleep(1000 * 1000);
        ms -= 1000;
    };

    if (ms != 0)
    { 
        usleep(ms * 1000);
    }
}

//----------------------------------------------------------------------------
//  Purpose:
//   Check if the Lidar is present
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
bool checkRPLIDARHealth(RPlidarDriver* drv)
{
    u_result     op_result;
    rplidar_response_device_health_t healthinfo;

    op_result = drv->getHealth(healthinfo);
    if (IS_OK(op_result)) { // the macro IS_OK is the preperred way to judge whether the operation is succeed.
        printf("RPLidar health status : %d\n", healthinfo.status);
        if (healthinfo.status == RPLIDAR_STATUS_ERROR) 
        {
            fprintf(stderr, "Error, rplidar internal error detected. Please reboot the device to retry.\n");
            // enable the following code if you want rplidar to be reboot by software
            // drv->reset();
            return false;
        }
        else 
        {
            return true;
        }

    }
    else 
    {
        fprintf(stderr, "Error, cannot retrieve the lidar health code: %x\n", op_result);
        return false;
    }
}

//----------------------------------------------------------------------------
//  Purpose:
//   Callback if user hits C, to clean up
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
void ctrlc(int)
{
    gCtrlCPressed = true;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Main loop for lidar
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
int main(int argc, const char* argv[])
{
    const char* opt_com_path = NULL;
    _u32         baudrateArray[2] = { 115200, 256000 };
    _u32         opt_com_baudrate = 0;
    u_result     op_result;
    pthread_t    thread_id;

    std::this_thread::sleep_for(std::chrono::seconds(20));

    bool useArgcBaudrate = false;

    printf("Ultra simple LIDAR data grabber for RPLIDAR.\n"
        "Version: " RPLIDAR_SDK_VERSION "\n");

    // read serial port from the command line...
    if (argc > 1)
    {
        opt_com_path = argv[1]; // or set to a fixed value: e.g. "com3"
    } 

    // read baud rate from the command line if specified...
    if (argc > 2)
    {
        opt_com_baudrate = strtoul(argv[2], NULL, 10);
        useArgcBaudrate = true;
    }

    if (!opt_com_path) 
    {
        opt_com_path = "/dev/ttyUSB0";
    }

    pthread_create(&thread_id, NULL, runThread, NULL);

    // create the driver instance
    RPlidarDriver* drv = RPlidarDriver::CreateDriver(DRIVER_TYPE_SERIALPORT);
    if (!drv) 
    {
        fprintf(stderr, "insufficent memory, exit\n");
        exit(-2);
    }

    rplidar_response_device_info_t devinfo;
    bool connectSuccess = false;
    // make connection...
    if (useArgcBaudrate)
    {
        if (!drv)
        { 
            drv = RPlidarDriver::CreateDriver(DRIVER_TYPE_SERIALPORT);
        }

        if (IS_OK(drv->connect(opt_com_path, opt_com_baudrate)))
        {
            op_result = drv->getDeviceInfo(devinfo);

            if (IS_OK(op_result))
            {
                connectSuccess = true;
            }
            else
            {
                delete drv;
                drv = NULL;
            }
        }
    }
    else
    {
        size_t baudRateArraySize = (sizeof(baudrateArray)) / (sizeof(baudrateArray[0]));
        for (size_t i = 0; i < baudRateArraySize; ++i)
        {
            if (!drv)
            { 
                drv = RPlidarDriver::CreateDriver(DRIVER_TYPE_SERIALPORT);
            }

            if (IS_OK(drv->connect(opt_com_path, baudrateArray[i])))
            {
                op_result = drv->getDeviceInfo(devinfo);

                if (IS_OK(op_result))
                {
                    connectSuccess = true;
                    break;
                }
                else
                {
                    delete drv;
                    drv = NULL;
                }
            }
        }
    }
    if (!connectSuccess) {

        fprintf(stderr, "Error, cannot bind to the specified serial port %s.\n", opt_com_path);
        goto on_finished;
    }

    // print out the device serial number, firmware and hardware version number..
    printf("RPLIDAR S/N: ");
    for (int pos = 0; pos < 16; ++pos) 
    {
        printf("%02X", devinfo.serialnum[pos]);
    }

    printf("\n"
        "Firmware Ver: %d.%02d\n"
        "Hardware Rev: %d\n"
        , devinfo.firmware_version >> 8
        , devinfo.firmware_version & 0xFF
        , (int)devinfo.hardware_version);

    // check health...
    if (!checkRPLIDARHealth(drv)) 
    {
        goto on_finished;
    }

    signal(SIGINT, ctrlc);

    drv->startMotor();
    // start scan...
    drv->startScan(0, 1);

    // fetech result and print it out...
    while (1) 
    {
        rplidar_response_measurement_node_hq_t nodes[8192];
        size_t   count = _countof(nodes);

        op_result = drv->grabScanDataHq(nodes, count);
        if (IS_OK(op_result)) 
        {
            drv->ascendScanData(nodes, count);
            for (int pos = 0; pos < (int)count; ++pos)
            {
                if (nodes[pos].quality > 0)
                {
                    double angle = (nodes[pos].angle_z_q14 * 90.f / (1 << 14));
                    double dist = nodes[pos].dist_mm_q2 / 4.0f;
                    int iAngle = (int)(angle + 0.5);

                    if (iAngle > 359)
                    {
                        iAngle = 0;
                    }

                    //                    points[iAngle] = ((points[iAngle]*7.0)/8.0) + (dist/8.0);
                    gRawPoints[iAngle] = dist;
                    if (-1 != gRawToPoints[iAngle])
                    {
                        gReportPoints[gRawToPoints[iAngle]] = dist;
                    }

#ifdef DEBUG
                    if (iAngle == 0)
                    {
                        printf("Dist: %08.2f %08.2f %4d\n", gRawPoints[iAngle], angle, iAngle);
                    }
#endif
                }
            }
        }

        if (gCtrlCPressed)
        {
            break;
        }
    }

    printf("Clean up\n");
    drv->stop();
    drv->stopMotor();
    // done!
on_finished:
    RPlidarDriver::DisposeDriver(drv);
    pthread_join(thread_id, NULL);
    drv = NULL;
    return 0;
}

//----------------------------------------------------------------------------
//  Purpose:
//   Send the readings to the Rio
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
void* runThread(void* vargp)
{
    auto inst = nt::GetDefaultInstance();

    nt::StartClient(inst, "10.40.89.2", 1735);
    std::this_thread::sleep_for(std::chrono::seconds(2));

    for (int i = 0;i< MAX_POINTS_TO_REPORT;i++)
    {
        gNetTable[i] = nt::GetEntry(inst, gLabelStr[i]);
    }

    while (false == gCtrlCPressed)
    {
        for (int i = 0; i < MAX_POINTS_TO_REPORT; i++)
        {
            nt::SetEntryValue(gNetTable[i], nt::Value::MakeDouble(gReportPoints[i]));
        }
        std::this_thread::sleep_for(std::chrono::milliseconds(SLEEP_TIME));
    }
    return NULL;
}


