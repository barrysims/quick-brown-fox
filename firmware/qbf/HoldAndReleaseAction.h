#ifndef HoldAndReleaseAction_h
#define HoldAndReleaseAction_h

#include "Arduino.h"
#include "Action.h"

/*
* Models a key that has a dual function, with a press and release characteristic.
* e.g. A key that acts as a shift key whilst it's being held down, and as
* a space key as it is released. The release behaviour is cancelled if another
* key has been pressed before releasing the holdAndReleaseKey, or if a timeout is reached.
*/
class HoldAndReleaseAction: public Action {

  public:
    HoldAndReleaseAction(
        Action * _holdAction,
        Action * _releaseAction,
        int _timeout,
        bool _buffer);
    void activate(elapsedMillis time);
    void deactivate(elapsedMillis time);
    int modCode();
    bool shouldBuffer() {
        return buffer;
    }
    bool timerReady(elapsedMillis time);
    void activateOnTimer(elapsedMillis time);
    void activateMomentarily(elapsedMillis time);

  private:
    int timeout;
    bool buffer;
    Action * holdAction;
    Action * releaseAction;
};

#endif
