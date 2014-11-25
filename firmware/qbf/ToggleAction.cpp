#include "ToggleAction.h"

ToggleAction::ToggleAction(Action * _action) {
    action = _action;
    isPressed = false;
}

extern void printKey(String val);
extern int keysPressed();

void ToggleAction::activate(elapsedMillis time) {
    if (isPressed) {
        action->deactivate(time);
        isPressed = false;
    } else {
        action->activate(time);
        isPressed = true;
    }
}

void ToggleAction::deactivate(elapsedMillis time) {}