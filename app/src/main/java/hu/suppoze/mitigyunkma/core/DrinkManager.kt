package hu.suppoze.mitigyunkma.core

import hu.suppoze.mitigyunkma.entity.Drink
import io.realm.Realm
import io.realm.Sort
import java.util.*

class DrinkManager(private val realm: Realm,
                   private val sharedPreferences: SharedPreferencesRepository,
                   private val defaultDrinkName: String) : DrinkRepository {

    val density: Double = 0.80207
    val molecularWeight: Double = 46.06844

    override fun calculateIndex(drink: Drink): Double {
        return drink.price / (drink.capacity * drink.percent * (density / molecularWeight) * 100) * 100
    }

    override fun addOrEditDrink(drink: Drink) {
        val predicatedName = if (drink.name.isNullOrBlank()) {
            "$defaultDrinkName #${sharedPreferences.getUnnamedDrinkCountAndIncrement()}"
        } else drink.name

        realm.executeTransaction {
            drink.name = predicatedName
            drink.index = calculateIndex(drink)
            drink.lastmod = Date()
            realm.copyToRealmOrUpdate(drink)
        }
    }

    override fun deleteDrink(drink: Drink) {
        realm.executeTransaction {
            drink.deleteFromRealm()
        }
    }

    override fun getBestDrinks(): List<Drink> {
        return realm.where(Drink::class.java).findAllSorted(Drink::index.name, Sort.ASCENDING)
    }

    override fun getDrinksHistory(): List<Drink> {
        return realm.where(Drink::class.java).findAllSorted(Drink::lastmod.name, Sort.DESCENDING)
    }
}