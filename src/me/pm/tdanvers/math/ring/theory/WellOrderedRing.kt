package me.pm.tdanvers.math.ring.theory

interface WellOrderedRing<T: Ring<T>>: Comparable<T>, Ring<T> {
  val abs: T
}