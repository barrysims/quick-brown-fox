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
        val->activate(time);
    }
    Action::activate(time);
}

void ShiftAction::deactivate(elapsedMillis time) {
    val->deactivate(time);
    Action::deactivate(time);
}
