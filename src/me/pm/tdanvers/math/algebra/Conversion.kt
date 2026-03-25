package me.pm.tdanvers.math.algebra

import me.pm.tdanvers.math.algebra.linear.*
import me.pm.tdanvers.math.algebra.ring.theory.*
import me.pm.tdanvers.math.arithmetic.*

fun <T: Ring<T>> Gaussian<T>.asMatrix() = Matrix(2.n(), scalar, gaussian, -gaussian, scalar)
fun <T: Ring<T>> Dual<T>.asMatrix() = Matrix(2.n(), scalar, dual, dual.zero, scalar)
fun <T: Ring<T>> Hyperbolic<T>.asMatrix() = Matrix(2.n(), scalar, hyperbolic, hyperbolic, scalar)