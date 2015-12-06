package hu.suppoze.mitigyunkma.view.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import butterknife.Bind
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnItemClick

import hu.suppoze.mitigyunkma.R
import hu.suppoze.mitigyunkma.core.IndexCalculator
import org.jetbrains.anko.async
import org.jetbrains.anko.db.DoubleParser
import org.jetbrains.anko.uiThread

class CalculateFragment : Fragment() {

    @Bind(R.id.calculate_view_capacity) lateinit var capacityField: TextInputLayout
    @Bind(R.id.calculate_view_percentage) lateinit var percentageField: TextInputLayout
    @Bind(R.id.calculate_view_price) lateinit var priceField: TextInputLayout
    @Bind(R.id.calculate_view_drink_index) lateinit var drinkIndex: TextView
    @Bind(R.id.button_calculate) lateinit var calculateButton: RelativeLayout

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_calculate, container, false)
        ButterKnife.bind(this, view)

        return view
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    @OnClick(R.id.button_calculate)
    fun onCalculateButtonClicked() {
        async {

            // TODO: Outsource conversion
            val capacity = (capacityField.editText.text.toString()).toDouble()
            val percentage = (percentageField.editText.text.toString()).toDouble()
            val price = (priceField.editText.text.toString()).toDouble()

            val index = IndexCalculator.calculateIndex(percentage, capacity, price)

            uiThread {
                drinkIndex.text = index.toString()
            }
        }
    }

}
