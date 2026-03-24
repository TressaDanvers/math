package me.pm.tdanvers.math.arithmetic

import me.pm.tdanvers.math.*
import me.pm.tdanvers.math.ring.theory.*
import java.math.*

data class Integral(private val value: BigInteger)
  : Comparable<Integral>, Ring<Integral>, InSet {
  companion object {
    override fun toString() = "ℤ"
    val ZERO = 0.z()
    val ONE = 1.z()
  }

  override val zero get() = ZERO
  override val one get() = ONE
  override fun unaryPlus() = this
  override fun unaryMinus() = Integral(-value)

  override fun plus(other: Integral) =
    Integral(this.value + other.value)

  override fun minus(other: Integral) =
    Integral(this.value - other.value)

  override fun times(other: Integral) =
    Integral(this.value * other.value)

  override fun compareTo(other: Integral) =
    this.value.compareTo(other.value)

  fun asBigInteger() = value

  override fun toString() = "$value"
  override fun toContainingSetString() = "$Integral"

}