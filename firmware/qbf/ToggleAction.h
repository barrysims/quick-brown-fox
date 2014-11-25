#ifndef ToggleAction_h
#define ToggleAction_h

#include "Action.h"
#include <Arduino.h>

/*
* Models an action that toggles between a active and unpressed state
*/
class ToggleAction: public Action {

  public:
    ToggleAction(Action * _action);
    void activate(elapsedMillis time);
    void deactivate(elapsedMillis time);
    short modCode() {
        return action->modCode();
    }

  private:
    Action * action;
    bool isPressed;
};

#endif
