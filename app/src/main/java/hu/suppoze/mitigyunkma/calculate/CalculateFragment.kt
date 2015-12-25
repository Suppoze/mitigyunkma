package hu.suppoze.mitigyunkma.calculate

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

    // TODO: Create custom numpad view
    @Bind(R.id.numpad_0) lateinit var num0: Button
    @Bind(R.id.numpad_1) lateinit var num1: Button
    @Bind(R.id.numpad_2) lateinit var num2: Button
    @Bind(R.id.numpad_3) lateinit var num3: Button
    @Bind(R.id.numpad_4) lateinit var num4: Button
    @Bind(R.id.numpad_5) lateinit var num5: Button
    @Bind(R.id.numpad_6) lateinit var num6: Button
    @Bind(R.id.numpad_7) lateinit var num7: Button
    @Bind(R.id.numpad_8) lateinit var num8: Button
    @Bind(R.id.numpad_9) lateinit var num9: Button
    @Bind(R.id.numpad_delete) lateinit var numDelete: Button
    @Bind(R.id.numpad_decimal) lateinit var numDecimal: Button

    val presenter: CalculatePresenter = CalculatePresenter(this)

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_calculate, container, false)
        ButterKnife.bind(this, view)

        percentageField.editText.addTextChangedListener(presenter)
        percentageField.editText.showSoftInputOnFocus = false

        capacityField.editText.addTextChangedListener(presenter)
        capacityField.editText.showSoftInputOnFocus = false

        priceField.editText.addTextChangedListener(presenter)
        priceField.editText.showSoftInputOnFocus = false

        initializeNumpad()

        setSaveButtonEnabled(false)

        return view
    }

    private fun initializeNumpad() {

        // TODO: Create custom numpad view
        num0.setOnClickListener { writeInFocused("0") }
        num1.setOnClickListener { writeInFocused("1") }
        num2.setOnClickListener { writeInFocused("2") }
        num3.setOnClickListener { writeInFocused("3") }
        num4.setOnClickListener { writeInFocused("4") }
        num5.setOnClickListener { writeInFocused("5") }
        num6.setOnClickListener { writeInFocused("6") }
        num7.setOnClickListener { writeInFocused("7") }
        num8.setOnClickListener { writeInFocused("8") }
        num9.setOnClickListener { writeInFocused("9") }
        numDecimal.setOnClickListener { writeInFocused(".") }
        numDelete.setOnClickListener { deleteFromFocused() }
    }

    private fun deleteFromFocused() {
        val focusedTextField = determineFocused()?.text
        if (focusedTextField.isNullOrEmpty()) return
        focusedTextField?.delete(focusedTextField.length - 1, focusedTextField.length)
    }

    private fun writeInFocused(s: String) {
        determineFocused()?.text?.append(s)
    }

    private fun determineFocused(): EditText? {
        if (capacityField.editText.isFocused) return capacityField.editText
        else if (percentageField.editText.isFocused) return percentageField.editText
        else if (priceField.editText.isFocused) return priceField.editText
        throw RuntimeException("CalculateFragment: Determining focus failed.")
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
