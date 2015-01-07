#include "ShiftAction.h"
#include "Arduino.h"

extern void sendModifier(short modCode);

extern short modifierCode;

void ShiftAction::activate(elapsedMillis time) {
    if (modifierCode & MODIFIERKEY_SHIFT) {
        sendModifier(modifierCode ^ MODIFIERKEY_SHIFT);
        shiftVal->activate(time);
        pressedVal = shiftVal;
    } else {
        val->activate(time);
        pressedVal = val;
    }
    Action::activate(time);
}

void ShiftAction::deactivate(elapsedMillis time) {
    pressedVal->deactivate(time);
    if (pressedVal == shiftVal) sendModifier(modifierCode);
    Action::deactivate(time);
}
