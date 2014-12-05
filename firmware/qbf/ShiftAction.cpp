#include "ShiftAction.h"
#include "Arduino.h"

extern void sendModifier(short modCode);

extern short modifierCode;

void ShiftAction::activate(elapsedMillis time) {
    if (modifierCode & MODIFIERKEY_SHIFT) {
        sendModifier(modifierCode ^ MODIFIERKEY_SHIFT);
        shiftVal->activateMomentarily(time);
        sendModifier(modifierCode);
    } else {
        val->activateMomentarily(time);
    }
    Action::activate(time);
}

void ShiftAction::deactivate(elapsedMillis time) {
    Action::deactivate(time);
}
