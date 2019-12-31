/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

#include <chrono>
#include <climits>
#include <cstdio>
#include <thread>
#include <math.h>

#include "ntcore.h"
#include "stdio.h"

int main() {
  double count = 0.00;
  auto inst = nt::GetDefaultInstance();
/*
  nt::AddLogger(inst,
                [](const nt::LogMessage& msg) {
                  std::fputs(msg.message.c_str(), stderr);
                  std::fputc('\n', stderr);
                },
                0, UINT_MAX);
*/
  nt::StartClient(inst, "10.40.89.2",1735);
  std::this_thread::sleep_for(std::chrono::seconds(2));
  auto sinEntry = nt::GetEntry(inst, "/sin");

  while(true)
  {
    double sinValue = sin(count);
    count += 0.001;
    printf("%f\n",sinValue);
    nt::SetEntryValue(sinEntry, nt::Value::MakeDouble(sinValue));
    std::this_thread::sleep_for(std::chrono::milliseconds(50));
  }
}
