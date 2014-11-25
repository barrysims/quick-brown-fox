#ifndef DelayAction_h
#define DelayAction_h

#include "Action.h"

/*
* delays when key is pressed (useful for macros)
*/
class DelayAction: public Action {

  public:
    DelayAction(short _time) {
        time = _time;
    }
    void activate(elapsedMillis time);

  private:
    short time;
};

#endif

