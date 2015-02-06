#include "KeyBuffer.h"
#include "Key.h"
#include "Action.h"
#include "DefaultAction.h"

/*
* Add a key to the buffer
*
* Buffered keys are not activated immediately,
* instead, they are activated on release,
* or when a previously buffered key is released
*/
void KeyBuffer::push(Key * key) {
    buffer.push(key);
}

bool KeyBuffer::isEmpty() {
    return buffer.isEmpty();
}

// loop through buffer activating the hold action of
// any holdAndRelease actions that have been buffered for n millis
void KeyBuffer::updateOnLoop() {

    if (!buffer.isEmpty()) {
        StackList<Key *>bufferCpy;
        while (!buffer.isEmpty()) {
            if (buffer.peek()->timerReady()) {
                buffer.pop()->activateOnTimer();
            }
            else bufferCpy.push(buffer.pop());
        }
        while (!bufferCpy.isEmpty()) buffer.push(bufferCpy.pop());
    }
}

// Happens on key released
void KeyBuffer::updateOnRelease(Key * key) {

    if (contains(key)) {

        StackList<Key *>after;
        while (!buffer.isEmpty() && buffer.peek() != key) after.push(buffer.pop());

        if (!buffer.isEmpty()) buffer.pop();

        StackList<Key *>before;
        while (!buffer.isEmpty()) before.push(buffer.pop());

        while (!before.isEmpty()) {
            updateKey(before.pop());
            delay(1);
        }

        activateMomentarily(key);

        while (!after.isEmpty()) buffer.push(after.pop());
    }
}

void KeyBuffer::activateMomentarily(Key * key) {
    if (!key->isActive()) key->activateMomentarily();
    else key->deactivate();
}

void KeyBuffer::updateKey(Key * key) {
    if (key->pressedAction()->shouldBuffer()) {
        buffer.push(key);
        if (!key->isActive()) key->activate();
    } else activateMomentarily(key);
}

// Non-destructively tests for inclusion
bool KeyBuffer::contains(Key * key) {
    bool in = false;
    if (!buffer.isEmpty()) {
        StackList<Key *>bufferCpy;
        while (!buffer.isEmpty()) {
            if (key == buffer.peek()) in = true;
            bufferCpy.push(buffer.pop());
        }
        while (!bufferCpy.isEmpty()) buffer.push(bufferCpy.pop());
    }
    return in;
}
