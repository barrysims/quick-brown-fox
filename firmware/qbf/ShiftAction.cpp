#include "ShiftAction.h"

#include "Arduino.h"

extern void keyOn(short int val);
extern void keyOff(short int val);
extern short modifierCode;

void ShiftAction::activate(elapsedMillis time) {
    if (modifierCode & MODIFIERKEY_SHIFT) {
        keyOff(MODIFIERKEY_SHIFT);
        shiftVal->activate(time);
        shiftVal->deactivate(time);
        keyOn(MODIFIERKEY_SHIFT);
    } else {
        val->activate(time);
        val->deactivate(time);
    }
    Action::activate(time);
}

void ShiftAction::deactivate(elapsedMillis time) {
    shiftVal->deactivate(time);
    val->deactivate(time);
}