#include "ModifierAction.h"

extern void updateModifierCode();

void ModifierAction::activate(elapsedMillis time) {
    updateModifierCode();
    Action::activate(time);
};

void ModifierAction::deactivate(elapsedMillis time) {
    updateModifierCode();
    Action::deactivate(time);
};
