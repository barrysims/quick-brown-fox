package com.barrysims.qbf.util

import scala.language.implicitConversions

/**
 *
 */
case class ListOps[A](list: List[A]) {

  def chunkList(p: A => Boolean): List[List[A]] = splitListRec(list)(p)

  private def splitListRec(list: List[A])(p: A => Boolean): List[List[A]] =
    list.dropWhile(p) match {
      case Nil => Nil
      case x :: xs =>
        val (ys, zs) = xs span (!p(_))
        (x :: ys) :: splitListRec(zs.drop(1))(p)
    }
}

object ListOps {
  implicit def listToOps[A](list: List[A]): ListOps[A] = ListOps[A](list)
  implicit def opToList[A](listOps: ListOps[A]): List[A] = listOps.list
}
