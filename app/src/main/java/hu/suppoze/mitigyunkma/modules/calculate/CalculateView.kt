package hu.suppoze.mitigyunkma.modules.calculate

import hu.suppoze.mitigyunkma.model.Drink

interface CalculateView {

    fun loadDrinkForEdit(drink: Drink)

    fun getDrink() : Drink

    fun onAllFieldsValid()

    fun onAnyEmptyField()

    fun onAnyFieldInvalid()

    fun onDrinkCalculated(index: Double)
}