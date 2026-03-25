package me.pm.tdanvers.math.algebra.ring.theory

interface Ring<T: Ring<T>>: Semiring<T> {
  operator fun unaryMinus(): T
  operator fun minus(other: T): T
}