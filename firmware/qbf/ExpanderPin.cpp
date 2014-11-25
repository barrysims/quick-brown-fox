#include "ExpanderPin.h"

bool ExpanderPin::read() {
    return chip->digitalRead(number);
};

void ExpanderPin::write(bool level) {
    chip->digitalWrite(number, level);
};