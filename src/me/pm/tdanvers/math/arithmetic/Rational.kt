package me.pm.tdanvers.math.arithmetic

import me.pm.tdanvers.math.*
import me.pm.tdanvers.math.ring.theory.*
import java.math.*

data class Rational(
  private val numerator: BigInteger,
  private val denominator: BigInteger = BigInteger.ONE
): Comparable<Rational>, DivisionRing<Rational>, InSet {
  init { require(denominator.signum() != 0) { "$this ∉ $Rational" } }
  companion object {
    override fun toString() = "ℚ"
    val ZERO = 0.q()
    val ONE = 1.q()
  }

  val normalized get() =
    if (denominator.signum() >= 0) this
    else Rational(-numerator, -denominator)

  val simplified get() = normalized.run {
    if (denominator == BigInteger.ONE) this
    else numerator.gcd(denominator).abs().let { gcd ->
      if (gcd == BigInteger.ONE) this
      else Rational(numerator / gcd, denominator / gcd)
    }
  }

  val dividend = normalized.numerator.z()
  val divisor = normalized.denominator.n()

  override val zero: Rational get() = ZERO
  override val one: Rational get() = ONE
  override fun unaryPlus() = this
  override fun unaryMinus() = Rational(-numerator, denominator)
  override val inverse get() = Rational(denominator, numerator).normalized

  fun Rational.floor() = simplified.run {
    if (denominator == BigInteger.ONE) this
    else if (normalized.numerator.signum() >= 0) Rational(numerator / denominator)
    else -((-this) + one).run { Rational(numerator / denominator) }
  }

  override fun plus(other: Rational) = Rational(
    this.numerator * other.denominator + this.denominator * other.numerator,
    this.denominator * other.denominator
  ).simplified

  override fun minus(other: Rational) = Rational(
    this.numerator * other.denominator - this.denominator * other.numerator,
    this.denominator * other.denominator
  ).simplified

  override fun times(other: Rational) = Rational(
    this.numerator * other.numerator,
    this.denominator * other.denominator
  ).simplified

  override fun div(other: Rational) = Rational(
    this.numerator * other.denominator,
    this.denominator * other.numerator
  ).simplified

  override fun equals(other: Any?) =
    other is Rational && this.numerator * other.denominator == this.denominator * other.numerator

  override fun compareTo(other: Rational) = this.normalized.run {
    other.normalized.let { other ->
      (this.numerator * other.denominator).compareTo(this.denominator * other.numerator)
    }
  }

  override fun hashCode() = simplified.run {
    numerator.hashCode() + denominator.hashCode().shl(16)
  }

  override fun toString() = normalized.run {
    if (denominator == ONE) "$numerator"
    else "${numerator.toSuperscript()}⁄${denominator.toSubscript()}"
  }

  override fun toContainingSetString() = "$Rational"
}
