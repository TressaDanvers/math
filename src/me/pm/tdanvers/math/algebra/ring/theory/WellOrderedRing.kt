package me.pm.tdanvers.math.algebra.ring.theory

import kotlin.Comparable as WellOrdered

interface WellOrderedRing<T: Ring<T>>: WellOrdered<T>, Ring<T> {
  val abs: T
}