#ifndef ClickAction_h
#define ClickAction_h

#include <WString.h>
#include "Action.h"

#define left 0
#define right 1
#define middle 2

/*
* Models a key that has a definable shift state
*/
class ClickAction: public Action {

  public:
    ClickAction(int _button) {
        button = _button;
    }
    void deactivate();

  private:
    int button;
};

#endif
