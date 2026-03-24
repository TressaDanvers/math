package me.pm.tdanvers.math.arithmetic

import me.pm.tdanvers.math.*
import me.pm.tdanvers.math.ring.theory.*
import java.math.*

data class Natural(private val value: BigInteger)
  : Comparable<Natural>, NearSemiring<Natural>, Factoring<Natural>, InSet {
  init { require(value.signum() > 0) { "$this ∉ $Natural" } }
  companion object {
    override fun toString() = "ℕ₊"
    val ONE = 1.n()
  }

  override val one get() = ONE
  override fun unaryPlus() = this

  override val factors get() =
    value.factor()
      .map { (a, b) -> Natural(a) to Natural(b) }
      .let { it + it.map { (a, b) -> b to a } }
      .toMap()

  override val primeFactors: List<Natural> get() =
    value.factor()
      .map { (a, b) -> Natural(a) to Natural(b) }
      .drop(1).firstOrNull()
      ?.let { (p, l) -> listOf(p) + l.primeFactors }
      ?: if (value == BigInteger.ONE) emptyList() else listOf(this)

  override fun plus(other: Natural) =
    Natural(this.value + other.value)

  override fun times(other: Natural) =
    Natural(this.value * other.value)

  override fun gcf(other: Natural) =
    Natural(this.value.gcd(other.value))

  override fun compareTo(other: Natural) =
    this.value.compareTo(other.value)

  fun asBigInteger() = value

  override fun toString() = "$value"
  override fun toContainingSetString() = "$Natural"
}