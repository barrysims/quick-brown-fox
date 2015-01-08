package com.barrysims.qbf.model

/**
 * Actions defining the behaviour of key presses/releases
 *
 * For detailed explanations of each action type, see the C++ code
 */
sealed trait Action

/** A standard keypress */
case class DefaultAction(name: String, value: String) extends Action

/** A delay keypress */
case class DelayAction(name: String, time: Int) extends Action

/** A dual-action keypress */
case class HoldAndReleaseAction(name: String, holdAction: String, releaseAction: String, buffered: Int) extends Action

/** A list of actions, executed in turn */
case class MacroAction(name: String, keys: List[String], clear: Int) extends Action

/** A modifier, SHIFT, ALT, CTRL etc */
case class ModifierAction(name: String, value: String) extends Action

/** Switches between layers */
case class LayerAction(name: String, layer: Int) extends Action

/** An action that has a user-defined shift state */
case class ShiftAction(name: String, action: String, shiftAction: String) extends Action

/** Prints a string literal */
case class StringAction(name: String, value: String) extends Action

/** Toggles an action on and off */
case class ToggleAction(name: String, action: String) extends Action

/** Mouse Click */
case class ClickAction(name: String, button: Int) extends Action

/** Swaps layers (ie, swaps out a QUERTY layer for a Dvorak layer) */
case class SwapLayerAction(name: String, layers: List[Int]) extends Action

/** Swaps ctrl and super */
case class SwapCtrlSuperAction(name: String) extends Action
