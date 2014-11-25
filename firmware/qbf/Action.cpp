#include "Action.h"

#include "Arduino.h"

extern Action * lastPressed;
extern void updateModifierCode();

Action::Action() {
    active = false;
}

bool Action::isActive() {
    return active;
}

void Action::activate(elapsedMillis time) {
    active = true;
    lastPressed = this;
};

void Action::deactivate(elapsedMillis time) {
    active = false;

    // todo: is this needed here, or just when a modifier action is pressed?
    updateModifierCode();
};

