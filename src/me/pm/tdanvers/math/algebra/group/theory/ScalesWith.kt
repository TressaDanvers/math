package me.pm.tdanvers.math.algebra.group.theory

interface ScalesWith<T: ScalesWith<T, U>, U: Scales<U>> {
  operator fun times(other: U): T
  fun timesLeft(other: U): T
}