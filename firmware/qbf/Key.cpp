#include "Key.h"
#include "Action.h"
#include "KeyBuffer.h"

Key::Key(Action ** _actions, byte _numLayers) {
    numLayers = _numLayers;
    for (byte ly = 0; ly < numLayers; ly++) {
        actions[ly] = _actions[ly];
    }
    pressed = 0;
    bounceMillis = 50;
}

extern byte activeLayer;
extern KeyBuffer keyBuffer;

void Key::press() {
    if (pressed || millisSincePress < bounceMillis || millisSinceRelease < bounceMillis) return;

    pressed = 1;
    millisSincePress = 0;
    pressedLayer = activeLayer;

    if (!keyBuffer.contains(this) && (actions[pressedLayer]->shouldBuffer() || !keyBuffer.isEmpty())) keyBuffer.push(this);
    else activate();
}

void Key::release() {
    if (!pressed || millisSincePress < bounceMillis || millisSinceRelease < bounceMillis) return;

    if (keyBuffer.contains(this)) keyBuffer.updateOnRelease(this);
    else deactivate();

    pressed = 0;
    millisSinceRelease = 0;
}

void Key::activate() {
    active = 1;
    if (!actions[activeLayer]->isActive()) actions[activeLayer]->activate(millisSincePress);
}

void Key::deactivate() {
    active = 0;
    pressed = 0;
    for (byte ly = 0; ly < numLayers; ly++) if (actions[ly]->isActive()) actions[ly]->deactivate(millisSincePress);
}

Action * Key::pressedAction() {
    return actions[pressedLayer];
}

short Key::modCode() {
    return actions[pressedLayer]->modCode();
}

bool Key::timerReady() {
    return actions[pressedLayer]->timerReady(millisSincePress);
}

void Key::activateOnTimer() {
    active = 1;
    actions[pressedLayer]->activateOnTimer(millisSincePress);
}

void Key::activateMomentarily() {
    actions[activeLayer]->activateMomentarily(millisSincePress);
}
