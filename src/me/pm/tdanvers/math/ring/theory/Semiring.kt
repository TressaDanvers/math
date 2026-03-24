package me.pm.tdanvers.math.ring.theory

interface Semiring<T: Semiring<T>>: NearSemiring<T> {
  val zero: T
}