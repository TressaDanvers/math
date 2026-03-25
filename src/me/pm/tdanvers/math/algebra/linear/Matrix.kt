@file:Suppress("UNCHECKED_CAST", "UPPER_BOUND_VIOLATED_IN_TYPE_OPERATOR_OR_PARAMETER_BOUNDS_WARNING")

package me.pm.tdanvers.math.algebra.linear

import me.pm.tdanvers.math.*
import me.pm.tdanvers.math.algebra.group.theory.*
import me.pm.tdanvers.math.algebra.ring.theory.*
import me.pm.tdanvers.math.arithmetic.*

data class Matrix<T: Ring<T>>(private val rows: List<List<T>>)
  : DivisionRing<Matrix<T>>, ScalesWith<Matrix<T>, T>, InSet {
  init {
    require(rows.isNotEmpty())
    { "\uD835\uDD3B₀ₓₘ is not a valid matrix type" }
    require(rows.first().isNotEmpty())
    { "\uD835\uDD3Bₙₓ₀ is not a valid matrix type" }
    require(rows.all { it.size == rows.first().size })
    { "in \uD835\uDD3Bₙₓₘ, m must be consistent" }
  }

  // \uD835\uDCE1 = 𝓡
  companion object { override fun toString() = "\uD835\uDCE1ₙₓₘ"}

  constructor(value: T, size: Natural):
      this(size.asBigInteger().intValueExact()
        .let { size -> List(size) { r -> List(size) { c -> if (r == c) value else value.zero } } })

  constructor(rows: Natural, vararg values: T):
      this(values.toList().chunked(rows.toIntExact()))

  private var columnsMemo: List<List<T>>? = null
  private val columns get() =
    columnsMemo ?:
    rows.first().indices.map { i -> rows.map { it[i] } }.also { columnsMemo = it }

  val topLeft get() = rows.first().first()
  val width get() = rows.first().size.n()
  val height get() = rows.size.n()
  val dimensions get() = width to height

  private var transposeMemo: Matrix<T>? = null
  val transpose get() =
    transposeMemo ?:
    columns.let(::Matrix).also { transposeMemo = it }

  private var cofactorsMemo: Matrix<T>? = null
  val cofactors get() =
    cofactorsMemo ?:
    rows.mapIndexed { r, row -> List(row.size) { c -> cofactor((r + 1).n(), (c + 1).n())  } }
      .let(::Matrix).also { cofactorsMemo = it }

  fun minor(row: Natural, column: Natural) =
    rows.filterIndexed { r, _ -> (r + 1) != row.toIntExact() }
      .map { it.filterIndexed { c, _ -> (c + 1) != column.toIntExact() } }
      .let(::Matrix)

  fun cofactor(row: Natural, column: Natural) =
    minor(row, column).determinant.let { if ((row + column).toIntExact() % 2 == 0) it else -it }

  private var determinantMemo: T? = null
  val determinant: T get() {
    require(width == height) { "det($this) is not defined" }

    return determinantMemo ?:
    (if (width == 1.n()) topLeft
    else rows.first()
      .mapIndexed { c, it -> it * cofactor(1.n(), (c + 1).n()) }
      .reduce { a, b -> a + b }).also { determinantMemo = it }
  }

  override val zero get() =
    rows.map { row -> row.map { it.zero } }.let(::Matrix)
  override val one: Matrix<T> get() {
    require(width == height) { "I is not defined for a non-square matrix" }
    return Matrix(topLeft.one, width)
  }

  override fun unaryPlus() = this

  override fun unaryMinus() =
    Matrix(rows.map { row -> row.map { -it } })


  private var inverseMemo: Matrix<T>? = null
  override val inverse: Matrix<T> get() {
    require(topLeft is DivisionRing<*>) { "${toContainingSetString()} is not a division ring" }
    try {
      return inverseMemo ?:
      (determinant as DivisionRing<T>)
        .runCatching { inverse }
        .getOrElse { throw IllegalArgumentException("det($this) is not invertible", it) }
        .let { it * cofactors.transpose }
        .also { inverseMemo = it }
    } catch (e: IllegalArgumentException) {
      throw IllegalArgumentException("$this is not an invertible matrix", e)
    }
  }

  override fun plus(other: Matrix<T>): Matrix<T> {
    require(this.dimensions == other.dimensions) { "$this+$other is not defined" }
    return this.rows
      .zip(other.rows)
      .map { (a, b) -> a.zip(b) }
      .map { it.map { (a, b) -> a + b } }
      .let(::Matrix)
  }

  override fun minus(other: Matrix<T>) = this + -other

  override fun times(other: T) =
    rows.map { row -> row.map { it * other } }.let(::Matrix)

  override fun timesLeft(other: T) =
    rows.map { row -> row.map { other * it } }.let(::Matrix)

  override fun times(other: Matrix<T>): Matrix<T> {
    require(this.width == other.height) { "$this×$other is not defined" }
    return this.rows.map { row ->
      other.columns.map { col ->
        row.zip(col)
          .map { (a, b) -> a * b }
          .reduce { a, b -> a + b }
      }
    }.let(::Matrix)
  }

  override fun div(other: Matrix<T>): Matrix<T> = this * other.inverse

  override fun toString() =
    rows.joinToString(" | ") { it.joinToString(" ") }.let { "[ $it ]" }

  override fun toContainingSetString() =
    (if (topLeft is InSet) (topLeft as InSet).toContainingSetString() else "\uD835\uDCE1") +
      "${width.toSubscript()}ₓ${height.toSubscript()}"
}