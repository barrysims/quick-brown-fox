package com.barrysims.qbf.model

import scala.language.implicitConversions

/**
 * Factory methods for Row
 */
object Row {

  /**
   * Creates a [[Row]] from repeated items
   *
   * @param xs Items
   * @tparam A Item type
   * @return A Row of items
   */
  def apply[A](xs: A*): Row[A] = xs.toList
}

/**
 * Factory methods for creating Layers
 */
object Layer {

  /**
   * Creates a layer from repeated row arguments
   */
  def apply[A](rows: Row[A]*)(implicit default: A): Layer[A] =
    Layer[A](rows.toList, None)

  /**
   * Creates a layer from a list of rows
   */
  def apply[A](rows: List[Row[A]], map: Option[Mapping] = None)(implicit default: A): Layer[A] = {

    val mapping = map.getOrElse(Mapping.identityMapping(rows))

    val getItem = (p: Point) => for {
      r <- rows.lift(p._2)
      i <- r.lift(p._1)
    } yield i

    for {
      x <- 0 to mapping.cs
      y <- 0 to mapping.rs
      items <- mapping.map.get((x, y)) match {
        case Some(p) => Some((x, y) -> getItem(mapping.map((x, y))).getOrElse(default))
        case None => None
      }
    } yield items
  }.toMap
}

case class LayerOps[A](layer: Layer[A]) {
  val rows = layer.keys.map(_._2).max
  val cols = layer.keys.map(_._1).max
}

case class LayoutOps[A](layout: Layout[A]) {
  val rows = layout(0).rows
  val cols = layout(0).cols
}

object Layout {
  def apply[A](layer: Layer[A]): Layout[A] = List(layer)

  def apply[A](layers: Layer[A]*): Layout[A] = layers.toList
}

