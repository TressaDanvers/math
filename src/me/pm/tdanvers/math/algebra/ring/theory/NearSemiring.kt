package me.pm.tdanvers.math.algebra.ring.theory

import me.pm.tdanvers.math.algebra.group.theory.*

interface NearSemiring<T: NearSemiring<T>>: Scales<T> {
  val one: T
  operator fun unaryPlus(): T

  operator fun plus(other: T): T
  operator fun times(other: T): T
}