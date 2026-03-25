package me.pm.tdanvers.math.arithmetic.number.theory

interface Factoring<T: Factoring<T>> {
  val factors: Map<T, T>
  val primeFactors: List<T>
  fun gcf(other: T): T
}