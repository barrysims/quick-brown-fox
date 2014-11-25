#include "StringAction.h"

extern void printKey(String val);

void StringAction::activate(elapsedMillis time) {
    printKey(val);
    Action::activate(time);
}
