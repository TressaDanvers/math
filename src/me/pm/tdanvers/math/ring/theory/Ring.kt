package me.pm.tdanvers.math.ring.theory

interface Ring<T: Ring<T>>: Semiring<T> {
  operator fun unaryMinus(): T
  operator fun minus(other: T): T
}