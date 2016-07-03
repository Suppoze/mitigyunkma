package hu.suppoze.mitigyunkma.core

object IndexCalculator {

    private const val density: Double = 0.80207
    private const val molecularWeight: Double = 46.06844

    fun calculateIndex(price: Double, capacity: Double, percent: Double): Double =
            price / (capacity * percent * (density / molecularWeight) * 100) * 100
}