package me.pm.tdanvers.math.arithmetic

import java.math.*

fun BigInteger.n() = let(::Natural)
fun Byte.n() = toInt().n()
fun Short.n() = toInt().n()
fun Int.n() = toBigInteger().n()
fun Long.n() = toBigInteger().n()

fun BigInteger.nz() = let(::Whole)
fun Byte.nz() = toInt().nz()
fun Short.nz() = toInt().nz()
fun Int.nz() = toBigInteger().nz()
fun Long.nz() = toBigInteger().nz()

fun BigInteger.z() = let(::Integral)
fun Byte.z() = toInt().z()
fun Short.z() = toInt().z()
fun Int.z() = toBigInteger().z()
fun Long.z() = toBigInteger().z()

fun BigInteger.q() = let(::Rational)
fun Byte.q() = toInt().q()
fun Short.q() = toInt().q()
fun Int.q() = toBigInteger().q()
fun Long.q() = toBigInteger().q()

fun BigDecimal.q(): Rational {
  var numerator = this
  var denominator = BigInteger.ONE

  while (numerator.scale() > 0) {
    denominator *= BigInteger.TEN
    numerator *= BigDecimal.TEN
    numerator = numerator.stripTrailingZeros()
  }

  return Rational(numerator.toBigIntegerExact(), denominator).simplified
}

fun Float.q() = toBigDecimal().q()
fun Double.q() = toBigDecimal().q()