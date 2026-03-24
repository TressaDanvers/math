package me.pm.tdanvers.math.arithmetic

import me.pm.tdanvers.math.*
import me.pm.tdanvers.math.ring.theory.*
import java.math.*

data class Integral(private val value: BigInteger)
  : WellOrderedRing<Integral>, Factoring<Integral>, InSet {
  companion object {
    override fun toString() = "ℤ"
    val ZERO = 0.z()
    val ONE = 1.z()
  }

  override val zero get() = ZERO
  override val one get() = ONE
  override fun unaryPlus() = this
  override fun unaryMinus() = Integral(-value)
  override val abs get() = Integral(value.abs())

  override val factors: Map<Integral, Integral> get() {
    require(value.signum() != 0) { "∀n∈$Whole: factor(0) ∉ ${Natural}ⁿ" }
    return value.abs().factor()
      .map { (a, b) -> Integral(a) to Integral(b) }
      .map { (a, b) -> a to if (value.signum() < 0) -b else b }
      .let { it + it.map { (a, b) -> -a to -b } }
      .let { it + it.map { (a, b) -> b to a } }
      .toMap()
  }

  override val primeFactors: List<Integral> get() =
    if (value.signum() == 0) listOf(ZERO)
    else if (value.signum() < 0) listOf(-ONE) + (-this).primeFactors
    else value.factor()
      .map { (a, b) -> Integral(a) to Integral(b) }
      .drop(1).firstOrNull()
      ?.let { (p, l) -> listOf(p) + l.primeFactors }
      ?: if (value == BigInteger.ONE) emptyList() else listOf(this)

  override fun plus(other: Integral) =
    Integral(this.value + other.value)

  override fun minus(other: Integral) =
    Integral(this.value - other.value)

  override fun times(other: Integral) =
    Integral(this.value * other.value)

  override fun gcf(other: Integral): Integral {
    require(this.value.signum() != 0 || other.value.signum() != 0) { "gcf(0,0) ∉ $Integral" }
    return Integral(this.value.gcd(other.value).abs())
  }

  override fun compareTo(other: Integral) =
    this.value.compareTo(other.value)

  fun asBigInteger() = value

  override fun toString() = "$value"
  override fun toContainingSetString() = "$Integral"
}