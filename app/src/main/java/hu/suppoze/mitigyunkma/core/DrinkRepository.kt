package hu.suppoze.mitigyunkma.core

import hu.suppoze.mitigyunkma.entity.Drink

interface DrinkRepository {

    fun calculateIndex(drink: Drink): Double

    fun addOrEditDrink(drink: Drink)

    fun deleteDrink(drink: Drink)

    fun getBestDrinks(): List<Drink>

    fun getDrinksHistory(): List<Drink>

    fun getRatingForIndex(index: Double): Double

}
