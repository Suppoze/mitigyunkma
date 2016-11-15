package hu.suppoze.mitigyunkma.core

import android.content.SharedPreferences

class SharedPreferencesManager(private val sharedPreferences: SharedPreferences) : SharedPreferencesRepository {

    val DRINK_COUNT_KEY: String = "DRINK_COUNT_KEY"
    val EDITING_DRINK_NAME_KEY: String = "EDITING_DRINK_NAME_KEY"
    val IS_EDITING_DRINK_KEY: String = "IS_EDITING_DRINK_KEY"

    override fun getUnnamedDrinkCountAndIncrement(): Int {
        val retVal = sharedPreferences.getInt(DRINK_COUNT_KEY, 0)
        sharedPreferences.edit().putInt(DRINK_COUNT_KEY, retVal + 1).apply()
        return retVal
    }

    override fun isEditingDrink(): Boolean {
        return sharedPreferences.getBoolean(IS_EDITING_DRINK_KEY, false)
    }

    override fun cancelEditingDrink() {
        sharedPreferences.edit().putBoolean(IS_EDITING_DRINK_KEY, false).apply()
    }

    override fun setEditingDrinkName(drinkName: String) {
        sharedPreferences.edit().putString(EDITING_DRINK_NAME_KEY, drinkName).apply()
        sharedPreferences.edit().putBoolean(IS_EDITING_DRINK_KEY, true).apply()
    }

    override fun getEditingDrinkName(): String {
        return sharedPreferences.getString(EDITING_DRINK_NAME_KEY, EDITING_DRINK_NAME_KEY)
    }

}
