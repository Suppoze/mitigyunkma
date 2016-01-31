package hu.suppoze.mitigyunkma.calculate

import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import hu.suppoze.mitigyunkma.core.IndexCalculator
import hu.suppoze.mitigyunkma.model.Drink
import io.realm.Realm
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

class CalculatePresenter(fragment: Fragment) : TextWatcher {

    val view: CalculateFragment
    lateinit var realm: Realm

    var percent: Double = .0
    var capacity: Double = .0
    var price: Double = .0

    init {
        this.view = fragment as CalculateFragment
    }

    fun onCreate() {
        realm = Realm.getInstance(view.context)
    }

    fun onDestroy() {
        realm.close()
    }

    override fun afterTextChanged(s: Editable?) {    }
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {    }
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        async() {

            if (noEmptyFields()) {
                capacity = view.capacityField.editText.text.toString().toDouble()
                percent = view.percentField.editText.text.toString().toDouble()
                price = view.priceField.editText.text.toString().toDouble()

                if (zeroCheck()) {
                    val index = calculate().toInt()

                    uiThread {
                        view.drinkIndex.text = index.toString()
                        view.switchActionButtonState(CalculateFragment.ActionButtonState.SAVE)
                    }
                } else {
                    uiThread {
                        view.switchActionButtonState(CalculateFragment.ActionButtonState.DISABLED)
                        view.drinkIndex.text = ""
                    }
                }
            }
        }
    }

    private fun noEmptyFields(): Boolean =
        view.capacityField.editText.text.isNotEmpty() &&
        view.percentField.editText.text.isNotEmpty() &&
        view.priceField.editText.text.isNotEmpty()

    private fun zeroCheck(): Boolean = capacity > 0 && percent > 0 && price > 0

    private fun calculate(): Double =
        IndexCalculator.calculateIndex(
                percent = percent,
                capacity = capacity,
                price = price
        )

    fun saveDrink() {
        async() {
            realm.executeTransaction {
                realm.copyToRealm(Drink (
                        name = "Test",
                        index = calculate(),
                        capacity = capacity,
                        percent = percent,
                        price = price
                ))
            }

            uiThread {
                view.showAlert("Pia hozzáadása sikeres")
            }
        }
    }
}