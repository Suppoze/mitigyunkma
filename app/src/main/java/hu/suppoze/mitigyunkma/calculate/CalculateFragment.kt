package hu.suppoze.mitigyunkma.calculate

import android.animation.ObjectAnimator
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import butterknife.Bind
import butterknife.ButterKnife
import butterknife.OnClick

import hu.suppoze.mitigyunkma.R
import org.jetbrains.anko.support.v4.alert


class CalculateFragment : Fragment() {

    @Bind(R.id.calculate_view_capacity) lateinit var capacityField: TextInputLayout
    @Bind(R.id.calculate_view_percentage) lateinit var percentageField: TextInputLayout
    @Bind(R.id.calculate_view_price) lateinit var priceField: TextInputLayout
    @Bind(R.id.component_drink_index_textview) lateinit var drinkIndex: TextView
    @Bind(R.id.button_save) lateinit var saveButton: RelativeLayout
    @Bind(R.id.button_save_background) lateinit var saveButtonBackground: RelativeLayout
    @Bind(R.id.button_reset) lateinit var resetButton: RelativeLayout

    val presenter: CalculatePresenter = CalculatePresenter(this)

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_calculate, container, false)
        ButterKnife.bind(this, view)

        percentageField.editText.addTextChangedListener(presenter)
        capacityField.editText.addTextChangedListener(presenter)
        priceField.editText.addTextChangedListener(presenter)

        setSaveButtonEnabled(false)

        return view
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    fun setSaveButtonEnabled(isEnabled: Boolean) {
        saveButton.isClickable = isEnabled
        animateSaveButtonColor(isEnabled)
    }

    private fun animateSaveButtonColor(isEnabled: Boolean) {
        var background: TransitionDrawable? = saveButtonBackground.background as TransitionDrawable?
        if (background != null) {
            if (isEnabled) {
                background.startTransition(300)
            } else {
                background.reverseTransition(300)
            }
        }
    }

    @OnClick(R.id.button_reset)
    fun onResetClicked() {
        drinkIndex.text = ""

        percentageField.editText.text.clear()
        capacityField.editText.text.clear()
        priceField.editText.text.clear()

        percentageField.requestFocus()
    }

    @OnClick(R.id.button_save)
    fun onSaveClicked() {
        alert("Save button clicked") {
            positiveButton("Cool, brah") {}
        }.show()
    }

}
