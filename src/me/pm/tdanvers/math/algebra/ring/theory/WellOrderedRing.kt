package me.pm.tdanvers.math.algebra.ring.theory

interface WellOrderedRing<T: Ring<T>>: Comparable<T>, Ring<T> {
  val abs: T
}