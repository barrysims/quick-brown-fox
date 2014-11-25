#ifndef Action_h
#define Action_h

#include <Arduino.h>

/*
* base key layer
*
* A key layer object represents a specific layer of a specific key,
* so read as key-layer. Responsible for registering the press in the lastPressed global.
*/
class Action {

  public:
    Action();
    bool isActive();
    virtual signed short modCode() { return 0; }
    virtual bool shouldBuffer() { return false; }
    virtual bool timerReady(elapsedMillis time) { return false; }
    virtual void activateOnTimer(elapsedMillis time) {}
    virtual void activateMomentarily(elapsedMillis time) {
      activate(time);
      deactivate(time);
    }
    virtual void activate(elapsedMillis time);
    virtual void deactivate(elapsedMillis time);

  private:
    bool active;
};

#endif
