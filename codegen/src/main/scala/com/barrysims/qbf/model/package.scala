package com.barrysims.qbf

import scala.collection.immutable.List
import scala.language.implicitConversions

/**
 * Contains Model types
 */
package object model {

  /** A row of items */
  type Row[A] = List[A]

  /** A matrix coordinate */
  type Point = (Int, Int)

  /** A layer of items */
  type Layer[A] = Map[Point, A]

  /** A layout, made up from a number of layers */
  type Layout[A] = List[Layer[A]]

  /** A layout of keys */
  type KeyLayout = Layout[String]

  /** A map of configs */
  type SourceBundle[A] = Map[String, A]

  implicit def layerToOps[A](layer: Layer[A]): LayerOps[A] = LayerOps[A](layer)
  implicit def opsToLayer[A](layerOps: LayerOps[A]): Layer[A] = layerOps.layer
  implicit def layoutToOps[A](layout: Layout[A]): LayoutOps[A] = LayoutOps[A](layout)
  implicit def opsToLayout[A](layoutOps: LayoutOps[A]): Layout[A] = layoutOps.layout
}
