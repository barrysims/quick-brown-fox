Quick Brown Fox
=========
![alt text](/fox.jpg "Quick Brown Fox")

Create DIY keyboards without writing a line of code!

* [Introduction](#Introduction)
* [Downloads](#Downloads)
* [Getting Started](#Getting Started)
    * [Sample Config](#Sample Config)
    * [Generating C++ Code](#Generating C++ Code)
    * [Adding a Second Layer](#Adding a Second Layer)
    * [Defining New Actions](#Defining New Actions)
    * [A Simplified One-Handed Keyboard](#A Simplified One-Handed Keyboard)
    * [Configuring a Switch Matrix](#Configuring a Switch Matrix)

#<a name = "Introduction"/>
###Introduction
Quick Brown Fox is a configurable Teensyduino project that can be used to power DIY keyboards.

The project consists of two modules:
* The firmware, written in C++, ready to be uploaded to a Teensy
* A layout generator, which allows customised layouts to be created without writing a single line of code

This project is released under Creative Commons Attribution-NonCommercial 4.0

#<a name = "Downloads"/>
###Downloads

* [Firmware](https://github.com/barrysims/quickbrownfox-firmware/archive/master.zip "Firmware")
* [Layout Generator](https://github.com/barrysims/quickbrownfox-codegen/raw/master/bin/QuickBrownFox.jar "Generator")

#<a name = "Getting Started"/>
###Getting Started

Let's assume that you want to hook up a simple keypad to your computer and use it as a numberpad keyboard.
Spark Fun sell a suitable unit, https://www.sparkfun.com/products/8653, and we'll base much of this guide on that unit.

![alt text](/keypad.jpg "Keypad")

In order to attach the keypad to your computer, you're going to need a microcontroller with a USB interface, some wire, solder and a soldering iron. You won't need to write any code, just a couple of simple configuration files.

This simple number pad has seven output pins arranged in the following manner:

![alt text](/matrix.png "Keypad")

The Teensy Microcontroller:

![alt text](/teensy.jpg "Teensy 2")

The seven keypad pins need to be soldered to seven digital input pins on the Teensy. The following picture shows the keypad soldered to pins 14 - 20.

![alt text](/keypad+teensy.jpg "Teensy & Keypad")

Let's configure the software so that we can read the keypad using the microcontroller.

We need to provide two configurations files, one to describe the layout of the keys, another to select which pins we've chosen on the microcontroller.

#<a name="Sample Config"/>
####Sample Config

#####layout.txt
```
1  2  3
4  5  6
7  8  9
*  0  #
```
#####pins.txt
```
row 1 19
row 2 14
row 3 15
row 4 17

col 1 18
col 2 20
col 3 16

kill 21
```

The kill pin is a safety mechanism. Grounding this pin will disable the keyboard, which will get you out of trouble if you make changes to the firmware and the keyboard starts spewing keypresses over USB.

#<a name="Generating C++ Code"/>
####Generating C++ Code

Start the layout generator application by double-clicking the jar file. You should be presented with a GUI application. Select the directories containing your configurations files and the firmware as source and destination, respectively. Select "Pull Up" as the input mode, and "Row" as the input axis. Clicking on the 'generate' button will generate C++ code from your configurations files, and write it to the firmware directory.

Open the firmware using the Teensyduino program by clicking on the qbf.ino file. The firmware uses an external library [Stacklist](http://playground.arduino.cc/Code/StackList), follow the instructions on the stacklist page to install the library. Set the board type in the Tools menu to whichever Teensy variant you are using. Also set the USB type to "Keyboard + Mouse + Joystick". Upload the firmware using the arduino application with the Teensy loader. See https://www.pjrc.com/teensy/teensyduino.html for detailed instructions on uploading to the Teensy.

You should now find that the keypad operates as a number pad connected to your computer.

#<a name="Adding a Second Layer"/>
####Adding a Second Layer

Let's alter the layout to add function keys on a second layer. Extra layers are a common feature of ergonomic keyboards, but not usually found on standard keyboards. In order to access a second layer, we'll substitute a layer action for the * action on the lower left key.

#####layout.txt
```
## Number pad layer
1   2   3
4   5   6
7   8   9
>1  0   #

## Function key layer
f1  f2  f3
f4  f5  f6
f7  f8  f9
>1  f10  #
```

The layer switching action is indicated by ```>1``` Regenerating the C++ code and uploading it will produce a number pad that can send function key presses whilst the * key is held down.

You'll notice that it's not possible to get f10 to register. This is because of a phenomenon known as 'masking', the * key is masking the 0 key's press. The keypad unit is only designed for single keypresses. More sophisticated keypads use diodes in the matrix to prevent masking and allow for multiple keypresses (shifted keypresses for example).

#<a name="Defining New Actions"/>
####Defining New Actions

The full list of built-in actions can be found in the following file: [Default Keys](https://raw.githubusercontent.com/barrysims/quickbrownfox-codegen/master/src/main/resources/layouts/default-keys.txt "Default Keys")

The default actions cover all of the normal keys you'd find on a keyboard, but it's possible to create other, custom actions to perform other tasks. We can define new actions in a file called actions.txt, alongside the layout and pins.

Here are a few example custom actions:
```
## print "Hello"
string hi Hello

## copy (ctrl-c)
macro cp ct c

## select word (ctrl-left, ctrl-shift-right)
macro sw ct ← sh →

## copy word (select word, copy)
macro cw sw cp
 
```
Once you've defined a new action in the actions file, it can be referenced in the layouts file and added to your keyboard.

Let's leave the number pad, and look at some other possibilities...

#<a name="A Simplified One-Handed Keyboard"/>
####A Simplified One-Handed Keyboard

A special kind of action is available, which we'll call a 'hold-and-release' action. These allow two actions to be mapped to a single keypress. The first action is activated when the key is pressed and held, the second is activated when the key is released, but only if no other keys have been pressed in the meantime, and only a short time has passed.

For example, some one-handed keyboards exist that assign a dual function to the spacebar. When the spacebar is pressed, the entire contents of the keyboard are shifted to allow access to the other hand's keys. We can create a key that allows both a shift action and a normal spacebar action on the same key by using a hold-and-release action with shift and space.

#####layout.txt
```
##Layer 1: normal left-hand
q  w  e  r  t
a  s  d  f  g
z  x  c  v  b
s_

##Layer 2: mirrored right-hand, activated when spacebar held down
p  o  i  u  y
;  l  k  j  h
/  .  ,  m  n
s_
```
#####actions.txt
```
## A combined spacebar and layer shift action
holdAndRelease s_ >2 sp
```

#<a name="Configuring a Switch Matrix"/>
####Configuring a Switch Matrix
Keyboards such as the number pad we've already seen use switch matrices to minimise the number of output pins that are needed to capture keypresses.
For a great introduction to switch matrices, take a look at http://www.dribin.org/dave/keyboard/one_html/

In the case of the number pad, the layout of the switch matrix followed the layout of the keys, that is, three columns by four rows. Often, the switch matrix will not use the same layout as the keys, to simplify the circuitry, or to reduce the number of pins needed to read the matrix. In these cases, we need to add an extra configuration file to describe the mapping between the matrix and the physical layout.

Here's a real example, this is one layer of the awesome Kinesis Advantage keyboard, along with the actual mapping file you would need to produce the correct code to drive the keyboard.

#####Kinesis Advantage
```
es  f1  f2  f3  f4  f5  f6  f7  f8  f9  f10 f11 f12 ps  sl  pb  kp  pg
=   1   2   3   4   5                           6   7   8   9   0   -
tb  q   w   e   r   t                           y   u   i   o   p   ?
cl  a   s   d   f   g                           h   j   k   l   s   \
ls  z   x   c   v   b                           n   m   ,   .   /   rs
    ~   \   ←   →       lc  al          wn  rc      ↑   ↓   [   ]
                    bs  dl  hm          pu  en  sp
                            en          pd
```
The following is the matrix mapping for the Kinesis Advantage
```
## Rows
2   2   2   1   1   1   0   0   0   3   3   3   4   4   4   5   5   5
0   0   0   0   0   0                           4   4   4   4   4   4
1   1   1   1   1   1                           5   5   5   5   5   5
2   2   2   2   2   2                           6   6   6   6   6   6
3   3   3   3   3   3                           7   7   7   7   7   7
    4   4   4   4       6   5           7   6       3   3   3   3
                    6   6   5           7   6   6
                            5           7

## Cols
12  13  14  12  13  14  12  13  14  12  13  14  12  13  14  12  13  14
0   1   2   3   4   5                           6   7   8   9   10  11
0   1   2   3   4   5                           6   7   8   9   10  11
0   1   2   3   4   5                           6   7   8   9   10  11
0   1   2   3   4   5                           6   7   8   9   10  11
    1   2   3   4       5   5           4   2       6   8   9   10
                    3   4   3           2   1   0
                            1           1
```
The mapping file must have two layers, the first layer gives the col numbers that each key is attached to, the second gives the row.
