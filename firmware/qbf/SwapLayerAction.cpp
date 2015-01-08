#include "SwapLayerAction.h"

extern void swapLayers(int * layerIds, int index);

SwapLayerAction::SwapLayerAction(int * _layerIds, int _layerIdsSize) {
    layerIds = _layerIds;
    layerIdsSize = _layerIdsSize;
    index = 0;
};

void SwapLayerAction::swap() {
    index++;
    if (index >= layerIdsSize) index = 0;
    swapLayers(layerIds, index);
}
