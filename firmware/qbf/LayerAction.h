#ifndef LayerAction_h
#define LayerAction_h

#include "Action.h"

/*
* Models a key that svitches the active layer of the board
*/
class LayerAction: public Action {

  public:
    LayerAction(byte _val): val(_val) {}
    void activate(elapsedMillis time);
    void deactivate(elapsedMillis time);

  private:
    int val;
    byte lastLayer;
};

#endif
