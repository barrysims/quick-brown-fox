#include "DefaultAction.h"

#include "Arduino.h"

extern void keyOn(short int val);
extern void keyOff(short int val);

void DefaultAction::activate(elapsedMillis time) {
    keyOn(val);
    Action::activate(time);
}

void DefaultAction::deactivate(elapsedMillis time) {
    keyOff(val);
    Action::deactivate(time);
}
