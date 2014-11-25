#ifndef ExpanderPin_h
#define ExpanderPin_h

#include <Arduino.h>
#include <Adafruit_MCP23017.h>
#include "Pin.h"

/*
* Models a pin
*/
class ExpanderPin: public Pin {

  public:
    ExpanderPin(byte _number, Adafruit_MCP23017 * _chip) : Pin(_number) {
        chip = _chip;
    }
    bool read();
    void write(bool level);

  private:
    Adafruit_MCP23017 * chip;
};

#endif