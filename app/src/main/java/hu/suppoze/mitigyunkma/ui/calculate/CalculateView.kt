package hu.suppoze.mitigyunkma.ui.calculate

import hu.suppoze.mitigyunkma.entity.Drink

interface CalculateView {

    fun loadDrinkForEdit(drink: Drink)

    fun getDrink() : Drink

    fun onAllFieldsValid()

    fun onAnyEmptyField()

    fun onAnyFieldInvalid()

    fun onDrinkCalculated(index: Double)

    fun onSuccessfulSave()
}