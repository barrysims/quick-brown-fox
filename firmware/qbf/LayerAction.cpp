#include "LayerAction.h"

extern byte activeLayer;
extern byte defaultLayer;
extern void flash();

// todo: last/active layer
void LayerAction::deactivate(elapsedMillis time) {
    activeLayer = defaultLayer; // Should be lastLayer;
    Action::deactivate(time);
}

void LayerAction::activate(elapsedMillis time) {
    //lastLayer = activeLayer;
    activeLayer = val;
    Action::activate(time);
}
