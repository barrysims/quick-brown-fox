#include "HoldAndReleaseAction.h"
#include "KeyBuffer.h"

#include "Arduino.h"

extern Action * lastPressed;
extern KeyBuffer keyBuffer;
extern int modifierCode;

HoldAndReleaseAction::HoldAndReleaseAction(Action * _holdAction, Action * _releaseAction, int _timeout, bool _buffer) {
    holdAction = _holdAction;
    releaseAction = _releaseAction;
    buffer = _buffer;
    timeout = _timeout;
};

int HoldAndReleaseAction::modCode() {
    return holdAction->modCode();
};

void HoldAndReleaseAction::activate(elapsedMillis time) {
    holdAction->activate(time);
    Action::activate(time);
};

void HoldAndReleaseAction::deactivate(elapsedMillis time) {
    holdAction->deactivate(time);
    Action::deactivate(time);
    if (lastPressed == this && time < timeout) {
        releaseAction->activate(time);
        releaseAction->deactivate(time);
    }
};

bool HoldAndReleaseAction::timerReady(elapsedMillis time) {
    return (time > timeout);
}

void HoldAndReleaseAction::activateOnTimer(elapsedMillis time) {
    holdAction->activate(time);
    Action::activate(time);
}

void HoldAndReleaseAction::activateMomentarily(elapsedMillis time) {
    releaseAction->activate(time);
    Action::activate(time);
    delay(1);
    releaseAction->deactivate(time);
    Action::deactivate(time);
}
