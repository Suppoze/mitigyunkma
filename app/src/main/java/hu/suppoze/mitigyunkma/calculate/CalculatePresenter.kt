package hu.suppoze.mitigyunkma.calculate

import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import hu.suppoze.mitigyunkma.R
import hu.suppoze.mitigyunkma.base.Navigator
import hu.suppoze.mitigyunkma.core.IndexCalculator
import hu.suppoze.mitigyunkma.model.Drink
import hu.suppoze.mitigyunkma.util.ResourceHelper
import io.realm.Realm
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import java.util.*

class CalculatePresenter(fragment: Fragment) : TextWatcher {

    val view: CalculateFragment
    lateinit var realm: Realm

    var percent: Double = .0
    var capacity: Double = .0
    var price: Double = .0

    companion object {
        var instance: CalculatePresenter by DelegatesExt.notNullSingleValue()
    }

    init {
        this.view = fragment as CalculateFragment
        instance = this
    }

    fun onCreate() {
        realm = Realm.getDefaultInstance()
    }

    fun onDestroy() {
        realm.close()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    override fun afterTextChanged(s: Editable?) {

        async() {

            if (noEmptyFields()) {
                capacity = view.capacityField.editText!!.text.toString().toDouble()
                percent = view.percentField.editText!!.text.toString().toDouble()
                price = view.priceField.editText!!.text.toString().toDouble()

                if (zeroCheck()) {
                    val index = calculate().toInt()
                    uiThread {
                        view.drinkIndex.text = index.toString()
                        view.switchActionButtonState(CalculateFragment.ActionButtonState.SAVE)
                    }

                } else {
                    uiThread {
                        view.drinkIndex.text = ""
                        view.switchActionButtonState(CalculateFragment.ActionButtonState.DISABLED)
                    }
                }

            } else {
                uiThread {
                    view.drinkIndex.text = ""
                    view.switchActionButtonState(CalculateFragment.ActionButtonState.NEXT)
                }
            }
        }
    }

    private fun noEmptyFields(): Boolean =
        view.capacityField.editText!!.text.isNotEmpty() &&
        view.percentField.editText!!.text.isNotEmpty() &&
        view.priceField.editText!!.text.isNotEmpty()

    private fun zeroCheck(): Boolean = capacity > 0 && percent > 0 && price > 0

    private fun calculate(): Double =
        IndexCalculator.calculateIndex(
                percent = percent,
                capacity = capacity,
                price = price
        )

    fun saveDrink(drinkName: String) {

        val predicatedName = if (drinkName.isNullOrBlank()) ResourceHelper.getStringRes(R.string.unnamed_drink) else drinkName

        async() {

            val realmInstance = Realm.getDefaultInstance()

            realmInstance.executeTransaction {

                var drink = it.createObject(Drink::class.java)
                drink.capacity = capacity
                drink.percent = percent
                drink.price = price
                drink.name = predicatedName
                drink.index = calculate()
                drink.lastmod = Date()

            }

            realmInstance.close()

            uiThread {
                Navigator.navigate(Navigator.Pages.HISTORY)
            }
        }
    }

    fun editDrink(drink: Drink) {
        view.capacityField.editText?.setText(drink.capacity.toString())
        view.priceField.editText?.setText(drink.price.toString())
        view.percentField.editText?.setText(drink.percent.toString())
    }
}