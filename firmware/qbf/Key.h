#ifndef Key_h
#define Key_h

#include <Arduino.h>

class KeyBuffer;
class Action;

/*
* Models a physical key, that is, a switch on the matrix.
*
* Each key has a number of layers.  Key is responsible for registering the pressed state
* of the physical key and delegating the keypress action to the correct Action so that
* the results of pressing the key can be executed.
*/
class Key {

  public:
    Key(Action ** _actions, byte numLayers);
    void press();
    void release();
    void activate();
    void deactivate();
    void activateOnTimer();
    void activateMomentarily();
    Action * pressedAction();
    short modCode();
    bool isPressed() {
        return pressed;
    }
    bool isActive() {
        return active;
    }
    bool timerReady();

  private:
    int bounceMillis;
    byte pressedLayer;
    bool pressed;
    bool active;
    elapsedMillis millisSincePress;
    elapsedMillis millisSinceRelease;
    byte numLayers;
    Action * actions[10];
};

#endif
