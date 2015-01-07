#ifndef SwapCtrlSuperAction_h
#define SwapCtrlSuperAction_h

#include "Action.h"

extern void swapCtrlSuper();

/*
* Swaps ctrl and super (for switching between nix/win and mac
*/
class SwapCtrlSuperAction: public Action {

  public:
    SwapCtrlSuperAction() {};
    void activate(elapsedMillis time) {
        swapCtrlSuper();
    };
};

#endif

