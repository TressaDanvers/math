package me.pm.tdanvers.math.arithmetic

import java.math.*

fun BigInteger.n() = let(::Natural)
fun Int.n() = toBigInteger().n()

fun BigInteger.nz() = let(::Whole)
fun Int.nz() = toBigInteger().nz()

fun BigInteger.z() = let(::Integral)
fun Int.z() = toBigInteger().z()

fun BigInteger.q() = let(::Rational)
fun Int.q() = toBigInteger().q()