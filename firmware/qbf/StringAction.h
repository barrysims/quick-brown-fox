#ifndef StringAction_h
#define StringAction_h

#include <WString.h>
#include "Action.h"

/*
* Models a key that prints a String, used to print symbols normally accessed via a shifted-key
*/
class StringAction: public Action {

  public:
    StringAction(String _val) {
        val = _val;
    }
    void activate(elapsedMillis time);

  private:
    String val;
};

#endif
