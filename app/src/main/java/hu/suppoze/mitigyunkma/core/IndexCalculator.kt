package hu.suppoze.mitigyunkma.core

import hu.suppoze.mitigyunkma.model.Drink

object IndexCalculator {

    private const val density: Double = 0.80207
    private const val molecularWeight: Double = 46.06844

    fun calculateIndex(drink: Drink): Double =
            drink.price / (drink.capacity * drink.percent * (density / molecularWeight) * 100) * 100
}