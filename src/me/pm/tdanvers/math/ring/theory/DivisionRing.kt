package me.pm.tdanvers.math.ring.theory

interface DivisionRing<T: DivisionRing<T>>: Ring<T> {
  val inverse: T
  operator fun div(other: T): T
}