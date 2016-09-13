package hu.suppoze.mitigyunkma.core

import android.content.SharedPreferences

class SharedPreferencesManager(private val sharedPreferences: SharedPreferences) : SharedPreferencesRepository {

    val DRINK_COUNT_KEY: String = "DRINK_COUNT_KEY"

    override fun getUnnamedDrinkCountAndIncrement(): Int {
        val retVal = sharedPreferences.getInt(DRINK_COUNT_KEY, 0)
        sharedPreferences.edit().putInt(DRINK_COUNT_KEY, retVal + 1).apply()
        return retVal
    }

}
