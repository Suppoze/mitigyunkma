package hu.suppoze.mitigyunkma.calculate

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import butterknife.ButterKnife
import butterknife.Bind
import butterknife.OnClick

import hu.suppoze.mitigyunkma.R
import hu.suppoze.mitigyunkma.base.BaseFragment

class CalculateFragment : BaseFragment() {

    @Bind(R.id.calculate_view_capacity) lateinit var capacityField: TextInputLayout
    @Bind(R.id.calculate_view_percentage) lateinit var percentField: TextInputLayout
    @Bind(R.id.calculate_view_price) lateinit var priceField: TextInputLayout
    @Bind(R.id.component_drink_index_textview) lateinit var drinkIndex: TextView
    @Bind(R.id.button_action) lateinit var actionButton: RelativeLayout
    @Bind(R.id.button_action_background) lateinit var actionButtonBackground: RelativeLayout
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

    var previousActionButtonState: ActionButtonState = ActionButtonState.DISABLED
    lateinit var colorToState: Map<ActionButtonState, Int>

    val presenter: CalculatePresenter = CalculatePresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate();
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_calculate, container, false)
        ButterKnife.bind(this, view)

        percentField.editText.addTextChangedListener(presenter)
        capacityField.editText.addTextChangedListener(presenter)
        priceField.editText.addTextChangedListener(presenter)

        percentField.editText.showSoftInputOnFocus = false
        capacityField.editText.showSoftInputOnFocus = false
        priceField.editText.showSoftInputOnFocus = false

        initializeNumpad()

        colorToState = hashMapOf(
                Pair(ActionButtonState.DISABLED, ContextCompat.getColor(context, R.color.action_button_disabled)),
                Pair(ActionButtonState.NEXT, ContextCompat.getColor(context, R.color.action_button_next)),
                Pair(ActionButtonState.SAVE, ContextCompat.getColor(context, R.color.action_button_save))
        )
        switchActionButtonState(ActionButtonState.NEXT)

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
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


    private fun writeInFocused(s: String) {
        determineFocused()?.text?.append(s)
    }

    private fun deleteFromFocused() {
        val focusedTextField = determineFocused()?.text
        if (focusedTextField.isNullOrEmpty()) return
        focusedTextField?.delete(focusedTextField.length - 1, focusedTextField.length)
    }

    private fun determineFocused(): EditText? {
        if (capacityField.editText.isFocused) return capacityField.editText
        else if (percentField.editText.isFocused) return percentField.editText
        else if (priceField.editText.isFocused) return priceField.editText
        throw RuntimeException("CalculateFragment: Determining focus failed.")
    }

    fun switchActionButtonState(state: ActionButtonState) {
        if (previousActionButtonState != state) {
            animateActionButton(previousActionButtonState, state)
            previousActionButtonState = state
        }
        actionButton.isClickable = (state != ActionButtonState.DISABLED)
    }

    private fun animateActionButton(oldState: ActionButtonState, newState: ActionButtonState) {
        val fromColor = colorToState[oldState]
        val toColor = colorToState[newState]

        val animator: ObjectAnimator = ObjectAnimator.ofObject(
                actionButtonBackground, "background", ArgbEvaluator(), fromColor, toColor)
        animator.setDuration(200)
        animator.start()
    }

    @OnClick(R.id.button_reset)
    fun onResetClicked() {
        drinkIndex.text = ""

        percentField.editText.text.clear()
        capacityField.editText.text.clear()
        priceField.editText.text.clear()

        percentField.requestFocus()
    }

    @OnClick(R.id.button_action)
    fun onSaveClicked() {
        presenter.saveDrink()
    }

    enum class ActionButtonState {
        NEXT, DISABLED, SAVE
    }

}