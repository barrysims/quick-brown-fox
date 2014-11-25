#ifndef KeyBuffer_h
#define KeyBuffer_h

#include <WString.h>
#include "StackList.h"

class Key;

/*
* todo: add doc
*/
class KeyBuffer {

  public:
    KeyBuffer() {};
    void push(Key * key);
    void updateOnRelease(Key * key);
    void updateOnLoop();
    bool contains(Key * key);
    bool isEmpty();

  private:
    String t;
    StackList<Key *> buffer;
    void updateKey(Key * key);
    void activateMomentarily(Key * key);
};

#endif
