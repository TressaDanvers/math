package me.pm.tdanvers.math.arithmetic

import me.pm.tdanvers.math.*
import me.pm.tdanvers.math.ring.theory.*
import java.math.*

data class Whole(private val value: BigInteger)
  : Comparable<Whole>, Semiring<Whole>, InSet {
  init { require(value.signum() >= 0) { "$this ∉ $Whole" } }
  companion object {
    override fun toString() = "ℕ₀"
    val ZERO = 0.nz()
    val ONE = 1.nz()
  }

  override val zero get() = ZERO
  override val one get() = ONE
  override fun unaryPlus() = this

  override fun plus(other: Whole) =
    Whole(this.value + other.value)

  override fun times(other: Whole) =
    Whole(this.value * other.value)

  fun asBigInteger() = value

  override fun compareTo(other: Whole) =
    this.value.compareTo(other.value)

  override fun toString() = "$value"
  override fun toContainingSetString() = "$Whole"
}