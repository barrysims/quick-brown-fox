#include "TeensyPin.h"
#include "Arduino.h"

bool TeensyPin::read() {
    return digitalRead(number);
};

void TeensyPin::write(bool level) {
    digitalWrite(number, level);
};