package hu.suppoze.mitigyunkma.ui.calculate

import hu.suppoze.mitigyunkma.EditDrinkEvent
import hu.suppoze.mitigyunkma.MitigyunkApp
import hu.suppoze.mitigyunkma.ui.base.Navigator
import hu.suppoze.mitigyunkma.core.IndexCalculator
import hu.suppoze.mitigyunkma.core.SharedPreferencesRepository

import hu.suppoze.mitigyunkma.entity.Drink
import io.realm.Realm
import net.grandcentrix.thirtyinch.TiPresenter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import javax.inject.Inject

class CalculatePresenter() : TiPresenter<CalculateView>() {

    @Inject lateinit var realm: Realm
    @Inject lateinit var sharedPreferences: SharedPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        MitigyunkApp.appComponent.inject(this)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    fun validate(drink: Drink, fieldsFilled: Array<Boolean>) {
        var validFields: Int = 0
        val allFieldsFilled = fieldsFilled.all { it == true }

        if (fieldsFilled[0] == true && (drink.percent == .0 || drink.percent > 100))
            view.onPercentInvalid()
        else {
            view.onPercentValid()
            validFields++
        }

        if (fieldsFilled[1] == true && drink.price == .0)
            view.onPriceInvalid()
        else {
            view.onPriceValid()
            validFields++
        }

        if (fieldsFilled[2] == true && drink.capacity == .0)
            view.onCapacityInvalid()
        else {
            view.onCapacityValid()
            validFields++
        }

        if (validFields == 3 && allFieldsFilled) {
            view.onAllFieldsValid()
        } else if (validFields < 3 && allFieldsFilled) {
            view.onHasInvalidFields()
        } else if (!allFieldsFilled) {
            view.onHasEmptyFields()
        }
    }

    fun calculate(drink: Drink) = view.onDrinkCalculated(IndexCalculator.calculateIndex(drink))

    fun saveDrink(drink: Drink, defaultName: String) {

        val predicatedName = if (drink.name.isNullOrBlank()) {
            "$defaultName #${sharedPreferences.getUnnamedDrinkCountAndIncrement()}"
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

    @Subscribe(threadMode = ThreadMode.POSTING)
    fun onEditDrinkEvent(editDrinkEvent: EditDrinkEvent) {
        view.loadDrinkForEdit(editDrinkEvent.drink)
    }

    private fun navigateToHistory() {
        Navigator.navigate(Navigator.Pages.HISTORY)
    }
}