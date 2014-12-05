#ifndef StringAction_h
#define StringAction_h

#include <WString.h>
#include "Action.h"

/*
* Models a key that prints a String, used to print symbols normally accessed via a shifted-key
*/
class StringAction: public Action {

  public:
    StringAction(char * _val, int _n) {
        val = _val;
        n = _n;
    }
    void activate(elapsedMillis time);

  private:
    char * val;
    int n;
};

#endif
