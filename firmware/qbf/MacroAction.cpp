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
    Action * active[n];
    for(int i = 0; i < n; i++) {
        Action * k = *(actions + i);
        modCode = modCode | k->modCode();
        sendModifier(modCode);
        if (k->modCode() == 0 && in(active, k, n)) k->deactivate(time);
        k->activate(time);
        active[i] = k;
    }
    Action::activate(time);
    clearMod = !clear;
};

void MacroAction::deactivate(elapsedMillis time) {
    for(int i = n - 1; i >= 0; i--) {
        Action * k = *(actions + i);
        k->deactivate(time);
    }
    if (clear) sendModifier(0); // Not strictly correct - mod code handling needs cleaning up
    Action::deactivate(time);
};

bool MacroAction::in(Action * actions[], Action * action, int n) {
    for(int i = 0; i < n; i++) {
        if (actions[i] == action) return true;
    }
    return false;
}
