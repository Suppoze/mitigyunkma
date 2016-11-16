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
            updateRatings()
        }
    }

    override fun deleteDrink(drink: Drink) {
        realm.executeTransaction {
            drink.deleteFromRealm()
            updateRatings()
        }
    }

    override fun getBestDrinks(): List<Drink> {
        return realm.where(Drink::class.java).findAllSorted(Drink::index.name, Sort.ASCENDING)
    }

    override fun getDrinksHistory(): List<Drink> {
        return realm.where(Drink::class.java).findAllSorted(Drink::lastmod.name, Sort.DESCENDING)
    }

    override fun getRatingForIndex(index: Double): Double {
        var max = realm.where(Drink::class.java).max(Drink::index.name)
        var min = realm.where(Drink::class.java).min(Drink::index.name)

        if (max == null || min == null) return .0

        max = Math.max(max as Double, index)
        min = Math.min(min as Double, index)

        return (index - min) / (max - min)
    }

    private fun updateRatings() {
        val max = realm.where(Drink::class.java).max(Drink::index.name) as Double?
        val min = realm.where(Drink::class.java).min(Drink::index.name) as Double?

        if (max == null || min == null) return

        realm.where(Drink::class.java).findAll().forEach { it.rating = (it.index - min) / (max - min) }
    }
}
