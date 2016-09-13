package hu.suppoze.mitigyunkma.ui.calculate

import hu.suppoze.mitigyunkma.entity.Drink
import net.grandcentrix.thirtyinch.TiView

interface CalculateView : TiView {

    fun loadDrinkForEdit(drink: Drink)

    fun getDrink() : Drink

    fun onHasEmptyFields()

    fun onPercentValid()

    fun onPercentInvalid()

    fun onPriceValid()

    fun onPriceInvalid()

    fun onCapacityValid()

    fun onCapacityInvalid()

    fun onAllFieldsValid()

    fun onHasInvalidFields()

    fun onDrinkCalculated(index: Double)

    fun onSuccessfulSave()
}