#ifndef ShiftAction_h
#define ShiftAction_h

#include <WString.h>
#include "Action.h"

/*
* Models a key that has a definable shift state
*/
class ShiftAction: public Action {

  public:
    ShiftAction(Action * _val, Action * _shiftVal) {
        val = _val;
        shiftVal = _shiftVal;
    }
    void activate(elapsedMillis time);
    void deactivate(elapsedMillis times);

  private:
    Action * val;
    Action * shiftVal;
};

#endif
