#include "StringAction.h"

extern void printKey(char val);

void StringAction::activate(elapsedMillis time) {
    //printKey(val);
    for(int i = 0; i < n; i++) {
        char c = *(val + i);
        printKey(c);
    }
    Action::activate(time);
}
