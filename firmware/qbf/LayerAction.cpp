#include "LayerAction.h"

extern byte activeLayer;
extern void flash();

// todo: last/active layer
void LayerAction::deactivate(elapsedMillis time) {
    activeLayer = 0; // Should be lastLayer;
    Action::deactivate(time);
}

void LayerAction::activate(elapsedMillis time) {
    //lastLayer = activeLayer;
    activeLayer = val;
    Action::activate(time);
}
