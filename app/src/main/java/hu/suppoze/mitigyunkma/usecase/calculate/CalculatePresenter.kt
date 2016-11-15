package hu.suppoze.mitigyunkma.usecase.calculate

import hu.suppoze.mitigyunkma.MitigyunkApp
import hu.suppoze.mitigyunkma.core.DrinkRepository
import hu.suppoze.mitigyunkma.core.SharedPreferencesRepository
import hu.suppoze.mitigyunkma.entity.Drink
import hu.suppoze.mitigyunkma.usecase.EditDrinkEvent
import net.grandcentrix.thirtyinch.TiPresenter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class CalculatePresenter() : TiPresenter<CalculateView>() {

    @Inject lateinit var drinkRepository: DrinkRepository
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

    fun calculate(drink: Drink) {
        val index = drinkRepository.calculateIndex(drink)
        view.onDrinkCalculated(index, drinkRepository.getRatingForIndex(index))
    }

    fun saveOrEditDrink(drink: Drink) {
        drinkRepository.addOrEditDrink(drink)
        cancelEditDrink()
        view.onSuccessfulSaveOrEdit()
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    fun onEditDrinkEvent(editDrinkEvent: EditDrinkEvent) {
        view.loadDrinkForEdit(editDrinkEvent.drink)
    }

    fun cancelEditDrink() {
        sharedPreferences.cancelEditingDrink()
    }
}