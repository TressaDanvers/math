package me.pm.tdanvers.math.ring.theory

interface NearSemiring<T: NearSemiring<T>> {
  val one: T
  operator fun unaryPlus(): T

  operator fun plus(other: T): T
  operator fun times(other: T): T
}