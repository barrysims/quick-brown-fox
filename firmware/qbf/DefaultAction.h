#ifndef DefaultAction_h
#define DefaultAction_h

#include "Action.h"

/*
* Models a regular action
*/
class DefaultAction: public Action {

  public:
    DefaultAction(signed short _val) {
        val = _val;
    }
    void activate(elapsedMillis time);
    void deactivate(elapsedMillis time);

  private:
    signed short val;
};

#endif
