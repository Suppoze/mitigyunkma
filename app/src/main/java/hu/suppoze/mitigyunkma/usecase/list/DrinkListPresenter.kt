package hu.suppoze.mitigyunkma.usecase.list

import hu.suppoze.mitigyunkma.MitigyunkApp
import hu.suppoze.mitigyunkma.core.DrinkRepository
import hu.suppoze.mitigyunkma.core.SharedPreferencesRepository
import hu.suppoze.mitigyunkma.entity.Drink
import hu.suppoze.mitigyunkma.usecase.EditDrinkEvent
import io.realm.RealmResults
import net.grandcentrix.thirtyinch.TiPresenter
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class DrinkListPresenter : TiPresenter<DrinkListView>() {

    @Inject lateinit var drinkRepository: DrinkRepository
    @Inject lateinit var sharedPreferencesRepository: SharedPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        MitigyunkApp.appComponent.inject(this)
    }

    fun getBestDrinks(): RealmResults<Drink> {
        return drinkRepository.getBestDrinks() as RealmResults<Drink>
    }

    fun getDrinksHistory(): RealmResults<Drink> {
        return drinkRepository.getDrinksHistory() as RealmResults<Drink>
    }

    fun deleteDrink(drink: Drink) {
        drinkRepository.deleteDrink(drink)
    }

    fun editDrink(drink: Drink) {
        sharedPreferencesRepository.setEditingDrinkName(drink.name)
        EventBus.getDefault().post(EditDrinkEvent(drink))
    }
}