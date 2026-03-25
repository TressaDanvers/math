package me.pm.tdanvers.math.arithmetic

import me.pm.tdanvers.math.*
import me.pm.tdanvers.math.arithmetic.number.theory.Factoring
import me.pm.tdanvers.math.algebra.ring.theory.Semiring
import java.math.*

data class Whole(private val value: BigInteger)
  : Comparable<Whole>, Semiring<Whole>, Factoring<Whole>, InSet {
  init { require(value.signum() >= 0) { "$this ∉ $Whole" } }
  companion object {
    override fun toString() = "ℕ₀"
    val ZERO = 0.nz()
    val ONE = 1.nz()
  }

  override val zero get() = ZERO
  override val one get() = ONE
  override fun unaryPlus() = this

  override val factors: Map<Whole, Whole> get() {
    require(value.signum() != 0) { "∀n∈$Whole: factor(0) ∉ ${Whole}ⁿ" }
    return value.factor()
      .map { (a, b) -> Whole(a) to Whole(b) }
      .let { it + it.map { (a, b) -> b to a } }
      .toMap()
  }

  override val primeFactors: List<Whole> get() =
    if (value.signum() == 0) listOf(ZERO)
    else value.factor()
      .map { (a, b) -> Whole(a) to Whole(b) }
      .drop(1).firstOrNull()
      ?.let { (p, l) -> listOf(p) + l.primeFactors }
      ?: if (value == BigInteger.ONE) emptyList() else listOf(this)

  override fun plus(other: Whole) =
    Whole(this.value + other.value)

  override fun times(other: Whole) =
    Whole(this.value * other.value)

  override fun gcf(other: Whole): Whole {
    require(this.value.signum() != 0 || other.value.signum() != 0) { "gcf(0,0) ∉ $Whole" }
    return Whole(this.value.gcd(other.value))
  }

  fun asBigInteger() = value

  override fun compareTo(other: Whole) =
    this.value.compareTo(other.value)

  override fun toString() = "$value"
  override fun toContainingSetString() = "$Whole"
}