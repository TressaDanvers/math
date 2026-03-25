package me.pm.tdanvers.math.arithmetic.number.theory

import java.math.*

internal fun BigInteger.factor() = sequence {
  var i = BigInteger.ONE
  while (i * i <= this@factor) {
    if (this@factor.mod(i).signum() == 0)
      yield(i to this@factor / i)
    i++
  }
}