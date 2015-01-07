#include <Arduino.h>
#include <StackList.h>
#include <Wire.h>
#include <Adafruit_MCP23017.h>

#include "ExpanderPin.h"
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
#include "Actions.h"
#include "Layout.h"

byte activeLayer = 0;
short modifierCode = 0;
bool clearMod = false;
Action * lastPressed;
KeyBuffer keyBuffer;
bool looped = false;

/*
* Runs once
*/
void setup() {
    pinMode(13, OUTPUT);
    //Serial.begin(9600);
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
    short modCode = 0;
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

void writeToPin(byte pin, bool state) {
    byte chip = (pin > 15)? 1 : 0;
    if (chip == 1) {
        pins1.digitalWrite(pin - 16, state);
    } else {
        pins0.digitalWrite(pin, state);
    }
}

byte readFromPin(byte pin) {
    byte chip = (pin > 15)? 1 : 0;
    if (chip == 1) {
        return pins1.digitalRead(pin - 16);
    } else {
        return pins0.digitalRead(pin);
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

void swapCtrlSuper() {
    ModifierAction swap = action_c_t;
    action_c_t = action_o_s;
    action_o_s = swap;
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
