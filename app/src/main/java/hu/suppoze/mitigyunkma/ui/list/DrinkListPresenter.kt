package hu.suppoze.mitigyunkma.ui.list

import hu.suppoze.mitigyunkma.ui.EditDrinkEvent
import hu.suppoze.mitigyunkma.MitigyunkApp
import hu.suppoze.mitigyunkma.entity.Drink
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import net.grandcentrix.thirtyinch.TiPresenter
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class DrinkListPresenter : TiPresenter<DrinkListView>() {

    @Inject lateinit var realm: Realm

    override fun onCreate() {
        super.onCreate()
        MitigyunkApp.appComponent.inject(this)
    }

    // TODO: This should go to a repository
    fun getRealmResultDataSet(sortByField: String): RealmResults<Drink> {
        return realm.where(Drink::class.java)
                .findAllSorted(
                        sortByField,
                        if (sortByField == Drink::lastmod.name) Sort.DESCENDING else Sort.ASCENDING)
    }

    fun deleteDrink(drink: Drink) {
        realm.executeTransaction {
            drink.deleteFromRealm()
        }
    }

    fun editDrink(drink: Drink) {
        EventBus.getDefault().post(EditDrinkEvent(drink))
    }
}