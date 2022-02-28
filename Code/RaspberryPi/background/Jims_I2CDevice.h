#ifndef Jims_I2CDevice_h
#define Jims_I2CDevice_h

#include <stdint.h>
#include <cstddef>
#include <sys/ioctl.h>
#include <asm/ioctl.h>
#include <stdio.h>
#include <fcntl.h>
#include <linux/i2c-dev.h>
#include <chrono>
#include <thread>
#include <iostream>
#include <memory.h>
#include <unistd.h>
#include <errno.h>
#include <sys/ioctl.h>
#include <asm/ioctl.h>
#include <stdio.h>
#include <fcntl.h>
#include <linux/i2c-dev.h>


///< The class which defines how we will talk to this device over I2C
class Jims_I2CDevice {
public:
  Jims_I2CDevice(uint8_t addr);
  uint8_t address(void);
  void end(void);

  bool readBuffer(uint8_t *buffer, uint16_t len, bool stop = true);
  bool writeBuffer(const uint8_t *buffer, uint16_t len, bool stop = true,
             const uint8_t *prefix_buffer = NULL, uint16_t prefix_len = 0);
  bool write_then_read(const uint8_t *write_buffer, uint16_t write_len,
                       uint8_t *read_buffer, uint16_t read_len,
                       bool stop = false);
  bool setSpeed(uint32_t desiredclk);

  /*!   @brief  How many bytes we can read in a transaction
   *    @return The size of the Wire receive/transmit buffer */
  uint16_t maxBufferSize() { return mMaxBufferSize; }

private:
  uint8_t mAddr;
  int mWire = 0;
  bool mBegun;
  uint16_t mMaxBufferSize;
  bool _read(uint8_t *buffer, uint16_t len, bool stop);
};

#endif
