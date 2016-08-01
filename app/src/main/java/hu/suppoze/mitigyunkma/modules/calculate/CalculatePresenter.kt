package hu.suppoze.mitigyunkma.modules.calculate

import android.os.Handler
import hu.suppoze.mitigyunkma.R
import hu.suppoze.mitigyunkma.modules.base.Navigator
import hu.suppoze.mitigyunkma.core.IndexCalculator
import hu.suppoze.mitigyunkma.model.Drink
import hu.suppoze.mitigyunkma.modules.base.BasePresenter
import hu.suppoze.mitigyunkma.util.ResourceHelper
import java.util.*

class CalculatePresenter(val view: CalculateView) : BasePresenter() {

    companion object {
        var instance: CalculatePresenter by DelegatesExt.notNullSingleValue()
    }

    init {
        instance = this
    }

    fun calculate(drink : Drink) = view.onDrinkCalculated(IndexCalculator.calculateIndex(drink))

    fun saveDrink(drink: Drink) {
        val predicatedName = if (drink.name.isNullOrBlank()) {
            val unnamedCount = realm.where(Drink::class.java)
                    .contains(Drink::name.name, ResourceHelper.getStringRes(R.string.unnamed_drink))
                    .count()
            if (unnamedCount > 0)
                "${ResourceHelper.getStringRes(R.string.unnamed_drink)} ($unnamedCount)"
            else
                ResourceHelper.getStringRes(R.string.unnamed_drink)
        } else drink.name


        realm.executeTransaction {
            drink.name = predicatedName
            drink.index = IndexCalculator.calculateIndex(drink)
            drink.lastmod = Date()
            realm.copyToRealmOrUpdate(drink)
        }

        view.onSuccessfulSave()
        navigateToHistory()
    }

    fun editDrink(drink: Drink) {
        realm.executeTransaction {
            drink.index = IndexCalculator.calculateIndex(drink)
            realm.copyToRealmOrUpdate(drink)
        }

        view.onSuccessfulSave()
        navigateToHistory()
    }

    fun loadDrinkForEdit(drink: Drink) = view.loadDrinkForEdit(drink)

    private fun navigateToHistory() {
        Handler().postDelayed(
                { Navigator.navigate(Navigator.Pages.HISTORY) },
                250)
    }
}