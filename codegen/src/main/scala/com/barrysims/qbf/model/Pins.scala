package com.barrysims.qbf.model

case class Pins private (pins: List[Pin],
                inputAxis: String,
                activeMode: String,
                expander0: Boolean,
                expander1: Boolean) {
  require(List("ROW", "COL").contains(inputAxis),
    s"Input String expected to be 'ROW' or 'COL', received '$inputAxis'")
  require(List("INPUT", "INPUT_PULLUP").contains(activeMode),
    s"Active mode expected to be 'INPUT' or 'INPUT_PULLUP', received '$activeMode'")
}

object Pins {
  def apply(pins: List[Pin],
            inputAxis: String,
            activeMode: String): Pins = {
    val expander = (chip: Int) => pins exists {case p: ExpanderPin if p.chip == chip => true; case _ => false}
    Pins(pins, inputAxis, activeMode, expander(0), expander(1))
  }
}

/**
 * Models a microcontroller pin mapping
 */
abstract class Pin(val axis: String, val number: Int) {
  def name: String
}

/**
 * Models a pin on the teensy, used when attaching the matrix directly to the teensy
 */
case class TeensyPin(override val axis: String, index: Int, override val number: Int) extends Pin(axis, number) {
  val name = s"${axis}_$index"
}

/**
 * Models a pin on a MPC23017, used when attaching the matrix to a pin expander chip
 *
 * @param chip The address of the chip owning the pin
 */
case class ExpanderPin(override val axis: String, index: Int, override val number: Int, chip: Int) extends Pin(axis, number) {
  val name = s"${axis}_$index"
}

/**
 *
 * @param number
 */
case class KillPin(override val number: Int) extends Pin("kill", number) {
  val name = "kill"
}
