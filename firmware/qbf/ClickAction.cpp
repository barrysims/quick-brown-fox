#include "ClickAction.h"

#include "Arduino.h"
#include <usb_mouse.h>

void ClickAction::deactivate() {

    switch (button) {
        case left:
            usb_mouse_buttons(1, 0, 0);
        case right:
            usb_mouse_buttons(0, 0, 1);
        case middle:
            usb_mouse_buttons(0, 1, 0);
    }
    usb_mouse_buttons(0, 0, 0);
}