//----------------------------------------------------------------------------
//
//  $Workfile: Robot.cpp$
//
//  $Revision: X$
//
//  Project:    SOTA bot Workshop
//
//                            Copyright (c) 2023
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
#include <numbers>
#include <frc/Encoder.h>
#include <frc/TimedRobot.h>
#include <frc/smartdashboard/SmartDashboard.h>
#include <frc/CAN.h>

//----------------------------------------------------------------------------
//Class Declarations
//----------------------------------------------------------------------------
//
//Class Name: Robot
//
//Purpose:
//  The class that run the robot
//
//----------------------------------------------------------------------------
class Robot : public frc::TimedRobot {
 public:

  //----------------------------------------------------------------------------
  //  Purpose:
  //   Constructor
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  Robot() : 
    mCanDevice(0xA080040)
  {
  }

  //----------------------------------------------------------------------------
  //  Purpose:
  //   The Tele Loop
  //
  //  Notes:
  //      None
  //
  //----------------------------------------------------------------------------
  void TeleopPeriodic() override {
    frc::CANData data;
    mCanDevice.ReadPacketLatest(1,&data);
    frc::SmartDashboard::PutNumber("CAN Data", data.data[7]);

    mCount++;
    if(0 == (mCount%10))
    {
      mColor+=10;
      data.data[0] = (mColor>>8)&0xFF;
      data.data[1] = mColor&0xFF;
      data.data[2] = 101;
      data.data[3] = 102;
      data.data[4] = 103;
      data.data[5] = 104;
      data.data[6] = 105;
      data.data[7] = 106;
      mCanDevice.WritePacket(data.data,8,2);
    }
  }

 private:
  //----------------------------------------------------------------------------
  //  Private Attributes
  //----------------------------------------------------------------------------
  frc::CAN mCanDevice;
  uint8_t mCount = 1;
  uint16_t mColor  = 0;
};

#ifndef RUNNING_FRC_TESTS
//----------------------------------------------------------------------------
//  Purpose:
//   Run the robot!
//
//  Notes:
//      None
//
//----------------------------------------------------------------------------
int main() {
  return frc::StartRobot<Robot>();
}
#endif
