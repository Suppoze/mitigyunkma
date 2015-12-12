package hu.suppoze.mitigyunkma.calculate

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import butterknife.Bind
import butterknife.ButterKnife

import hu.suppoze.mitigyunkma.R
import hu.suppoze.mitigyunkma.core.IndexCalculator


class CalculateFragment : Fragment() {

    @Bind(R.id.calculate_view_capacity) lateinit var capacityField: TextInputLayout
    @Bind(R.id.calculate_view_percentage) lateinit var percentageField: TextInputLayout
    @Bind(R.id.calculate_view_price) lateinit var priceField: TextInputLayout
    @Bind(R.id.component_drink_index_textview) lateinit var drinkIndex: TextView
    @Bind(R.id.button_calculate) lateinit var calculateButton: RelativeLayout

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_calculate, container, false)
        ButterKnife.bind(this, view)

        val textChangeHandler: TextChangeHandler = TextChangeHandler(drinkIndex, calculate())

        percentageField.editText.addTextChangedListener(textChangeHandler)
        capacityField.editText.addTextChangedListener(textChangeHandler)
        priceField.editText.addTextChangedListener(textChangeHandler)

        return view
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    fun calculate(): () -> Int {
        return {
            IndexCalculator.calculateIndex(
                    IndexCalculator.ParameterBundle(
                            percent = percentageField.editText.text.toString(),
                            capacity = capacityField.editText.text.toString(),
                            price = priceField.editText.text.toString()))
        }
    }

}
