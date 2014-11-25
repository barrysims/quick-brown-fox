#ifndef ModifierAction_h
#define ModifierAction_h

#include "Action.h"

class ModifierAction: public Action {

  public:
    ModifierAction(short _val): val(_val) {}
    short modCode() {
        return val;
    }
    void activate(elapsedMillis time);
    void deactivate(elapsedMillis time);
    bool buffered() {
        return false;
    }

  private:
    short val;
};

#endif
