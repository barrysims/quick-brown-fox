#ifndef SwapLayerAction_h
#define SwapLayerAction_h

#include "Action.h"

/*
* Swaps ctrl and super (for switching between nix/win and mac
*/
class SwapLayerAction: public Action {

  public:
    SwapLayerAction(int * layerIds, int _layerIdsSize);
    void activate(elapsedMillis time) {
        swap();
    };

  private:
    int * layerIds;
    int index;
    int layerIdsSize;
    void swap();
};

#endif

