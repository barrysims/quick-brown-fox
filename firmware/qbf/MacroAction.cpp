#include "MacroAction.h"

extern void sendModifier(short modCode);
extern bool clearMod;
extern short modifierCode;

MacroAction::MacroAction(Action ** _actions, int _n, bool _clear) {
    actions = _actions;
    n = _n;
    clear = _clear;
};

void MacroAction::activate(elapsedMillis time) {
    short modCode = modifierCode;
    for(int i = 0; i < n; i++) {
        Action * k = *(actions + i);
        modCode = modCode | k->modCode();
        sendModifier(modCode);
        k->activate(time);
        if (k->modCode() == 0) k->deactivate(time);
    }
    Action::activate(time);
    clearMod = !clear;
};

void MacroAction::deactivate(elapsedMillis time) {
    for(int i = n - 1; i >= 0; i--) {
        Action * k = *(actions + i);
        if (k->modCode() != 0) k->deactivate(time);
    }
    if (clear) sendModifier(0); // Not strictly correct - mod code handling needs cleaning up
    Action::deactivate(time);
};
