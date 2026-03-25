@file:Suppress("UNCHECKED_CAST", "UPPER_BOUND_VIOLATED_IN_TYPE_OPERATOR_OR_PARAMETER_BOUNDS_WARNING")

package me.pm.tdanvers.math.algebra

import me.pm.tdanvers.math.*
import me.pm.tdanvers.math.algebra.group.theory.*
import me.pm.tdanvers.math.algebra.ring.theory.*
import kotlin.Comparable as WellOrdered

data class Dual<T: Ring<T>>(val scalar: T, val dual: T = scalar.zero)
  : WellOrderedRing<Dual<T>>, DivisionRing<Dual<T>>, ScalesWith<Dual<T>, T>, InSet {

  // \uD835\uDCE1 = 𝓡
  companion object { override fun toString() = "\uD835\uDCE1[ε]"}

  val conjugate get() = Dual(scalar, -dual)
  val quadrance get() = (this * conjugate).scalar

  fun rotate() = Dual(-dual, scalar)

  override val zero get() = Dual(scalar.zero)
  override val one get() = Dual(scalar.one)
  override fun unaryPlus() = this
  override fun unaryMinus() = Dual(-scalar, -dual)
  override val abs get() = if (this < zero) -this else this

  override val inverse: Dual<T> get() {
    require(scalar is DivisionRing<*>) { "${toContainingSetString()} is not a division ring" }
    try { return (quadrance as DivisionRing<T>).inverse * conjugate }
    catch (e: IllegalArgumentException) { throw IllegalArgumentException("$this is not invertible", e) }
  }

  override fun plus(other: Dual<T>) = Dual(
    this.scalar + other.scalar,
    this.dual + other.dual
  )

  override fun minus(other: Dual<T>) = Dual(
    this.scalar - other.scalar,
    this.dual - other.dual
  )

  override fun times(other: T) =
    Dual(this.scalar * other, this.dual * other)

  override fun timesLeft(other: T) =
    Dual(other * this.scalar, other * this.dual)

  override fun times(other: Dual<T>) = Dual(
    this.scalar * other.scalar,
    this.dual * other.scalar + this.scalar * other.dual
  )

  override fun div(other: Dual<T>) =
    if (this.scalar == scalar.zero && other.scalar == scalar.zero)
      this.rotate() * other.rotate().inverse
    else this * other.inverse

  override fun compareTo(other: Dual<T>): Int {
    require(scalar is WellOrdered<*>) { "${toContainingSetString()} is not well-ordered" }
    return if (this.scalar == other.scalar) (this.dual as WellOrdered<T>).compareTo(other.dual)
    else (this.scalar as WellOrdered<T>).compareTo(other.scalar)
  }

  override fun toString() =
    listOf("$scalar", "${dual}ε")
      .filter { !it.matches("^0ε?$".toRegex()) }
      .joinToString("+")
      .replace("+-", "-")
      .replace("+⁻", "-")
      .replace("(?<=^|[+-])1ε".toRegex(), "ε")
      .ifEmpty { "0" }

  override fun toContainingSetString() =
    (if (scalar is InSet) (scalar as InSet).toContainingSetString() else "\uD835\uDCE1") + "[ε]"
}