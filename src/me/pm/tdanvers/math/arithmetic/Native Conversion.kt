package me.pm.tdanvers.math.arithmetic

fun Natural.toIntExact() = asBigInteger().intValueExact()
fun Whole.toIntExact() = asBigInteger().intValueExact()
fun Integral.toIntExact() = asBigInteger().intValueExact()