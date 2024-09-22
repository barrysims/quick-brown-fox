#include <Arduino.h>
#include <StackList.h>
#include <Wire.h>

#include "TeensyPin.h"
#include "Action.h"
#include "Key.h"
#include "KeyBuffer.h"
#include "DefaultAction.h"
#include "DelayAction.h"
#include "StringAction.h"
#include "ShiftAction.h"
#include "ModifierAction.h"
#include "LayerAction.h"
#include "HoldAndReleaseAction.h"
#include "MacroAction.h"
#include "ToggleAction.h"
#include "SwapCtrlSuperAction.h"
#include "SwapLayerAction.h"
#include "Actions.h"
#include "Layout.h"

byte defaultLayer = 0;
byte activeLayer = defaultLayer;
int modifierCode = 0;
bool clearMod = false;
Action * lastPressed;
KeyBuffer keyBuffer;
bool looped = false;

/*
* Runs once
*/
void setup() {
    pinMode(13, OUTPUT);
    Serial.begin(9600);
    pins();
}

/*
* Main loop
*/
void loop() {
    if (digitalRead(kill_pin) == HIGH) scanMatrix();
    if (!looped) {
        looped = true;
        alert(5);
    }
}

/*
* Scans the matrix, could do with some de-duplication
*/
void scanMatrix() {
    bool allOff = true;

    if (inputAxis == COL) {
        for (byte r = 0; r < rows; r++) {
            row[r]->write(onLevel);
            for (byte c = 0; c < cols; c++) {
                delayMicroseconds(1);
                bool p = col[c]->read() == onLevel;
                allOff = allOff && !(p);
                updateKey(r, c, p);
            }
            row[r]->write(offLevel);
        }
    } else if (inputAxis == ROW) {
        for (int c = cols - 1; c >= 0; c--) {
            col[c]->write(onLevel);
            for (byte r = 0; r < rows; r++) {
                delayMicroseconds(1);
                bool p = row[r]->read() == onLevel;
                allOff = allOff && !(p);
                updateKey(r, c, p);
            }
            col[c]->write(offLevel);
        }
    }

    if (allOff && clearMod) {
        clearMod = false;
        sendModifier(0);
    }

    keyBuffer.updateOnLoop();
}

/*
* Press and release keys as indicated by their state on the matrix.
*/
void updateKey(byte r, byte c, boolean p) {

    Key * key = layout[r][c];
    if (p && (!key->isPressed() && !keyBuffer.contains(key))) key->press();
    else if (!p && (key->isPressed() || keyBuffer.contains(key))) key->release();
}

/*
* Scan the matrix, aggregating the modifiers of each active Action.
*/
void updateModifierCode() {
    int modCode = 0;
    for (byte c = 0; c < cols; c++) {
        for (byte r = 0; r < rows; r++) {
            Key * key = layout[r][c];
            if (key->isActive()) modCode = modCode | key->modCode();
        }
    }
    if (modifierCode != modCode) {
        modifierCode = modCode;
        sendModifier(modCode);
    }
}

/*
* Send key-on event
*/
void keyOn(short val) {
    Keyboard.press(val);
}

/*
* Send key-off event
*/
void keyOff(short val) {
    Keyboard.release(val);
}

/*
* Prints a string
*/
void printKey(char val) {
    Keyboard.print(val);
}

/*
* Sets modifier (SHIFT, CTRL etc)
*/
void sendModifier(short modCode) {
    Keyboard.set_modifier(modCode);
    Keyboard.send_now();
}

/*
* This implementation is brittle and should be refactored to not include the names of the actions
*/
void swapCtrlSuper() {
    ModifierAction swap = action_c_t;
    action_c_t = action_o_s;
    action_o_s = swap;
}

/*
* This implementation is brittle and should be refactored to not include the names of the actions
*/
void swapLayers(int * layerIds, int index) {
    int newLayer = *(layerIds + index);
    Serial.println(newLayer);
    switch (* layerIds) {
        case 0:
            defaultLayer = newLayer;
            activeLayer = defaultLayer;
            break;
        case 1:
            action_62_1 = LayerAction(newLayer);
            break;
        case 2:
            action_62_2 = LayerAction(newLayer);
            break;
        case 3:
            action_62_3 = LayerAction(newLayer);
            break;
        case 4:
            action_62_4 = LayerAction(newLayer);
            break;
        case 5:
            action_62_5 = LayerAction(newLayer);
            break;
        case 6:
            action_62_6 = LayerAction(newLayer);
            break;
        case 7:
            action_62_7 = LayerAction(newLayer);
            break;
        case 8:
            action_62_8 = LayerAction(newLayer);
            break;
        case 9:
            action_62_9 = LayerAction(newLayer);
            break;
        default:
            Serial.println("no layer match");
    }
}

void flash(int t) {
    digitalWrite(13, HIGH);
    delay(t);
    digitalWrite(13, LOW);
}

void alert(int n) {
    for (int i = 1; i <= n; i++) {
        flash(50);
        delay(50);
    }
}
