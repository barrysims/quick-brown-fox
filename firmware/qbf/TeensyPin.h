#ifndef TeensyPin_h
#define TeensyPin_h

#include "Pin.h"

/*
* Models a pin
*/
class TeensyPin: public Pin {
  public:
    TeensyPin(int _number) : Pin(_number) {};
    bool read();
    void write(bool level);
};

#endif
