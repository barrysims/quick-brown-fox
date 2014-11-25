package com.barrysims.qbf.model

/**
 * Maps the switch matrix coordinates into the physical layout coordinates
 *
 * Mappings allow the physical layout to differ from the switch matrix layout.
 * This allows for an optimised switch matrix without compromising the readability
 * of the layout configuration file.
 *
 * e.g.
 * {{{
 * Mapping(
 *  Map(
 *  (0, 0) -> (1, 0),
 *  (0, 1) -> (1, 1),
 *  (1, 0) -> (0, 0),
 *  (1, 1) -> (0, 1)), 1, 1)
 * }}}
 * Applied to physical layout:
 * {{{
 *   a  b
 *   c  d
 * }}}
 * Produces a switch matrix:
 * {{{
 *   b  d
 *   a  c
 * }}}
 * TODO: Test the above assertion
 */
case class Mapping(map: Map[Point, Point], rs: Int, cs: Int)

/**
 * Factory functions for creating mappings
 */
object Mapping {

  /**
   * Creates a mapping from a parsed mapping file
   *
   * Expects a two layer layout, the first layer maps the columns,
   * the second layer maps the rows.Is
   */
  val fromLayers: List[Layer[Int]] => Mapping = (layers: List[Layer[Int]]) => {

    require(layers.size == 2, s"Expected 2 mapping layers, got ${layers.size}")

    val max = (layer: Layer[Int]) => layer.values.max

    val map: Map[Point, Point] = {
      for {
        px <- 0 to layers(0).cols
        py <- 0 to layers(0).rows
        sx <- layers(0).lift(px, py)
        sy <- layers(1).lift(px, py)
      } yield ((sx, sy), (px, py))
    }.toMap

    new Mapping(map, max(layers(1)), max(layers(0)))
  }

  /**
   * Creates a no-op mapping from a list of rows
   */
  val identityMapping = (rows: List[Row[_]]) => {
    val rs = rows.size - 1
    val cs = rows.head.size - 1
    new Mapping (
      (for {
        x <- 0 to cs
        y <- 0 to rs
      } yield ((x, y), (x, y))).toMap,
      rs,
      cs
    )
  }
}
