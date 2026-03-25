package me.pm.tdanvers.math.algebra.ring.theory

interface Semiring<T: Semiring<T>>: NearSemiring<T> {
  val zero: T
}