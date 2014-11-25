#include "MacroAction.h"

extern void sendModifier(short modCode);
extern bool clearMod;
extern short modifierCode;

MacroAction::MacroAction(Action ** _keys, int _n, bool _clear) {
    keys = _keys;
    n = _n;
    clear = _clear;
};

void MacroAction::activate(elapsedMillis time) {
    short modCode = modifierCode;
    for(int i = 0; i < n; i++) {
        Action * k = *(keys + i);
        modCode = modCode | k->modCode();
        sendModifier(modCode);
        k->activate(time);
    }
    Action::activate(time);
    clearMod = !clear;
};

void MacroAction::deactivate(elapsedMillis time) {
    for(int i = n - 1; i >= 0; i--) {
        Action * k = *(keys + i);
        k->deactivate(time);
    }
    if (clear) sendModifier(0);
    Action::deactivate(time);
};
