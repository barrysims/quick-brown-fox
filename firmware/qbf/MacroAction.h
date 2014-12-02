#ifndef MacroAction_h
#define MacroAction_h

#include "Action.h"

/*
* Models a key that performs a macro action
*/
class MacroAction: public Action {

  public:
    MacroAction(Action ** _keys, int _n, bool _clear);
    void activate(elapsedMillis time);
    void deactivate(elapsedMillis time);

  private:
    Action ** keys;
    int n;
    bool clear;
};

#endif