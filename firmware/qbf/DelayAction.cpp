#include "DelayAction.h"

#include "Arduino.h"

void DelayAction::activate(elapsedMillis time) {
    delay(time);
    Action::activate(time);
}
