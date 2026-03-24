package me.pm.tdanvers.math

import me.pm.tdanvers.math.arithmetic.*
import java.math.*

fun Int.toSuperscript() = superscript()
fun Int.toSubscript() = subscript()
fun BigInteger.toSuperscript() = superscript()
fun BigInteger.toSubscript() = subscript()

fun Natural.toSuperscript() = superscript()
fun Natural.toSubscript() = subscript()
fun Whole.toSuperscript() = superscript()
fun Whole.toSubscript() = subscript()
fun Integral.toSuperscript() = superscript()
fun Integral.toSubscript() = subscript()

private const val superscriptNotation = "⁰¹²³⁴⁵⁶⁷⁸⁹⁻"
private fun Any.superscript() =
  "$this".map { c ->
    if (c.isDigit()) superscriptNotation[c.digitToInt()]
    else superscriptNotation[10]
  }.joinToString("")

private const val subscriptNotation = "₀₁₂₃₄₅₆₇₈₉₋"
private fun Any.subscript() =
  "$this".map { c ->
    if (c.isDigit()) subscriptNotation[c.digitToInt()]
    else subscriptNotation[10]
  }.joinToString("")