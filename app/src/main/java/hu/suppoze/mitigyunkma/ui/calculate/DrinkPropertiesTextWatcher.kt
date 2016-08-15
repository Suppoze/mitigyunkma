package hu.suppoze.mitigyunkma.ui.calculate

import android.text.Editable
import android.text.TextWatcher

class DrinkPropertiesTextWatcher(
        val presenter: CalculatePresenter
) : TextWatcher {

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

    override fun afterTextChanged(p0: Editable?) {
        if (hasEmptyFields()) {
            presenter.view.onAnyEmptyField()
        } else {
            presenter.view.onAllFieldsValid()
        }
    }

    private fun hasEmptyFields(): Boolean {
        val drink = presenter.view.getDrink()
        return drink.capacity.toString().isNullOrEmpty() || drink.capacity == .0
                || drink.percent.toString().isNullOrEmpty() || drink.percent == .0
                || drink.price.toString().isNullOrEmpty() || drink.price == .0
    }
}