@file:Suppress("UNCHECKED_CAST", "UPPER_BOUND_VIOLATED_IN_TYPE_OPERATOR_OR_PARAMETER_BOUNDS_WARNING")

package me.pm.tdanvers.math.algebra

import me.pm.tdanvers.math.*
import me.pm.tdanvers.math.algebra.group.theory.*
import me.pm.tdanvers.math.algebra.ring.theory.*

data class Hyperbolic<T: Ring<T>>(val scalar: T, val hyperbolic: T = scalar.zero)
  : DivisionRing<Hyperbolic<T>>, ScalesWith<Hyperbolic<T>, T>, InSet {

  // \uD835\uDCE1 = 𝓡
  companion object { override fun toString() = "\uD835\uDCE1[j]"}

  val conjugate get() = Hyperbolic(scalar, -hyperbolic)
  val quadrance get() = (this * conjugate).scalar

  fun project() = Hyperbolic(scalar)

  override val zero get() = Hyperbolic(scalar.zero)
  override val one get() = Hyperbolic(scalar.one)
  override fun unaryPlus() = this
  override fun unaryMinus() = Hyperbolic(-scalar, -hyperbolic)

  override val inverse: Hyperbolic<T> get() {
    require(scalar is DivisionRing<*>) { "${toContainingSetString()} is not a division ring" }
    try { return (quadrance as DivisionRing<T>).inverse * conjugate }
    catch (e: IllegalArgumentException) { throw IllegalArgumentException("$this is not invertible", e) }
  }

  override fun plus(other: Hyperbolic<T>) = Hyperbolic(
    this.scalar + other.scalar,
    this.hyperbolic + other.hyperbolic
  )

  override fun minus(other: Hyperbolic<T>) = Hyperbolic(
    this.scalar - other.scalar,
    this.hyperbolic - other.hyperbolic
  )

  override fun times(other: T) =
    Hyperbolic(this.scalar * other, this.hyperbolic * other)

  override fun timesLeft(other: T) =
    Hyperbolic(other * this.scalar, other * this.hyperbolic)

  override fun times(other: Hyperbolic<T>) = Hyperbolic(
    this.scalar * other.scalar + this.hyperbolic * other.hyperbolic,
    this.hyperbolic * other.scalar + this.scalar * other.hyperbolic
  )

  override fun div(other: Hyperbolic<T>) =
    if ((this.scalar == this.hyperbolic && other.scalar == other.hyperbolic) ||
      (this.scalar == -this.hyperbolic && other.scalar == -other.hyperbolic))
      this.project() * other.project().inverse
    else this * other.inverse

  override fun toString() =
    listOf("$scalar", "${hyperbolic}j")
      .filter { !it.matches("^0j?$".toRegex()) }
      .joinToString("+")
      .replace("+-", "-")
      .replace("+⁻", "-")
      .replace("(?<=^|[+-])1j".toRegex(), "j")
      .ifEmpty { "0" }

  override fun toContainingSetString() =
    (if (scalar is InSet) (scalar as InSet).toContainingSetString() else "\uD835\uDCE1") + "[j]"
}