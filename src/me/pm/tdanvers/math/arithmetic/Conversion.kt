package me.pm.tdanvers.math.arithmetic

fun Natural.nz() = asBigInteger().nz()
fun Natural.z() = asBigInteger().z()
fun Natural.q() = asBigInteger().q()

fun Whole.z() = asBigInteger().z()
fun Whole.q() = asBigInteger().q()

fun Integral.q() = asBigInteger().q()
fun Integral.nzAbs() = abs.asBigInteger().nz()

fun Rational.zRounded() = rounded.dividend
fun Rational.zCeiling() = ceiling.dividend
fun Rational.zFloor() = floor.dividend