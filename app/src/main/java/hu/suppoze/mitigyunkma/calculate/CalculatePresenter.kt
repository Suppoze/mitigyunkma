package hu.suppoze.mitigyunkma.calculate

import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import hu.suppoze.mitigyunkma.core.IndexCalculator
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

class CalculatePresenter(fragment: Fragment) : TextWatcher {

    val view: CalculateFragment

    init {
        this.view = fragment as CalculateFragment
    }

    override fun afterTextChanged(s: Editable?) {    }
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (noEmptyFields()) {
            async {

                val index = calculate()

                uiThread {
                    view.drinkIndex.text = index.toString()
                    view.setSaveButtonEnabled(true)
                }
            }
        } else {
            view.setSaveButtonEnabled(false)
        }
    }

    private fun noEmptyFields(): Boolean {
        return view.capacityField.editText.text.isNotEmpty() &&
                view.percentageField.editText.text.isNotEmpty() &&
                view.priceField.editText.text.isNotEmpty()
    }

    private fun calculate(): Int {
        return IndexCalculator.calculateIndex(
                    IndexCalculator.ParameterBundle(
                            percent = view.percentageField.editText.text.toString(),
                            capacity = view.capacityField.editText.text.toString(),
                            price = view.priceField.editText.text.toString()))
    }

}
