@file:Suppress("UNCHECKED_CAST")

package me.pm.tdanvers.math.algebra.group.theory

interface Scales<T: Scales<T>> {
  operator fun <U: ScalesWith<U, T>> times(other: U): U =
    other.timesLeft(this as T)
}