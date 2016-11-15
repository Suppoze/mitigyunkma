package hu.suppoze.mitigyunkma.core

interface SharedPreferencesRepository {

    fun getUnnamedDrinkCountAndIncrement(): Int

    fun isEditingDrink(): Boolean

    fun cancelEditingDrink()

    fun setEditingDrinkName(drinkName : String)

    fun getEditingDrinkName(): String

}
