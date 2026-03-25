@file:Suppress("UNCHECKED_CAST", "UPPER_BOUND_VIOLATED_IN_TYPE_OPERATOR_OR_PARAMETER_BOUNDS_WARNING")

package me.pm.tdanvers.math.algebra

import me.pm.tdanvers.math.*
import me.pm.tdanvers.math.algebra.group.theory.*
import me.pm.tdanvers.math.algebra.ring.theory.*

data class Gaussian<T: Ring<T>>(val scalar: T, val gaussian: T = scalar.zero)
  : DivisionRing<Gaussian<T>>, ScalesWith<Gaussian<T>, T>, InSet {

  // \uD835\uDCE1 = 𝓡
  companion object { override fun toString() = "\uD835\uDCE1[i]"}

  val conjugate get() = Gaussian(scalar, -gaussian)
  val quadrance get() = (this * conjugate).scalar

  override val zero get() = Gaussian(scalar.zero)
  override val one get() = Gaussian(scalar.one)
  override fun unaryPlus() = this
  override fun unaryMinus() = Gaussian(-scalar, -gaussian)

  override val inverse: Gaussian<T> get() {
    require(scalar is DivisionRing<*>) { "${toContainingSetString()} is not a division ring" }
    try { return (quadrance as DivisionRing<T>).inverse * conjugate }
    catch (e: IllegalArgumentException) { throw IllegalArgumentException("$this is not invertible", e) }
  }

  override fun plus(other: Gaussian<T>) = Gaussian(
    this.scalar + other.scalar,
    this.gaussian + other.gaussian
  )

  override fun minus(other: Gaussian<T>) = Gaussian(
    this.scalar - other.scalar,
    this.gaussian - other.gaussian
  )

  override fun times(other: T) =
    Gaussian(this.scalar * other, this.gaussian * other)

  override fun timesLeft(other: T) =
    Gaussian(other * this.scalar, other * this.gaussian)

  override fun times(other: Gaussian<T>) = Gaussian(
    this.scalar * other.scalar - this.gaussian * other.gaussian,
    this.gaussian * other.scalar + this.scalar * other.gaussian
  )

  override fun div(other: Gaussian<T>) = this * other.inverse

  override fun toString() =
    listOf("$scalar", "${gaussian}i")
      .filter { !it.matches("^0i?$".toRegex()) }
      .joinToString("+")
      .replace("+-", "-")
      .replace("+⁻", "-")
      .replace("(?<=^|[+-])1i".toRegex(), "i")
      .ifEmpty { "0" }

  override fun toContainingSetString() =
    (if (scalar is InSet) (scalar as InSet).toContainingSetString() else "\uD835\uDCE1") + "[i]"
}