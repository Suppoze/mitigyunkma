package hu.suppoze.mitigyunkma.modules.calculate

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.content.ContextCompat
import android.view.*
import android.widget.*

import hu.suppoze.mitigyunkma.R
import hu.suppoze.mitigyunkma.modules.base.BaseFragment
import hu.suppoze.mitigyunkma.extensions.onFinishedAnimation
import hu.suppoze.mitigyunkma.extensions.prettyPrint
import hu.suppoze.mitigyunkma.model.Drink
import kotlinx.android.synthetic.main.dialog_save.view.*
import kotlinx.android.synthetic.main.component_action_button.*
import kotlinx.android.synthetic.main.fragment_calculate.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.support.v4.alert

class CalculateFragment : BaseFragment(), CalculateView {

    val presenter: CalculatePresenter = CalculatePresenter(this)

    lateinit var colorToState: Map<ActionButtonState, Int>
    lateinit var actionButtonTextToState: Map<ActionButtonState, String>

    var currentActionButtonState: ActionButtonState = ActionButtonState.DISABLED
    var isEditing: Boolean = false
    var editingDrinkName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onViewCreated()

        colorToState = hashMapOf(
                Pair(ActionButtonState.DISABLED, ContextCompat.getColor(context, R.color.action_button_disabled)),
                Pair(ActionButtonState.NEXT, ContextCompat.getColor(context, R.color.action_button_next)),
                Pair(ActionButtonState.SAVE, ContextCompat.getColor(context, R.color.action_button_save)),
                Pair(ActionButtonState.EDIT, ContextCompat.getColor(context, R.color.action_button_save)))
        actionButtonTextToState = hashMapOf(
                Pair(ActionButtonState.DISABLED, activity.getString(R.string.next)),
                Pair(ActionButtonState.NEXT, activity.getString(R.string.next)),
                Pair(ActionButtonState.SAVE, activity.getString(R.string.save)),
                Pair(ActionButtonState.EDIT, activity.getString(R.string.edit)))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_calculate, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resetButton.onClick { resetState() }
        actionButton.onClick { onActionButtonClicked() }

        initializeTextWatcher()

        percentField.editText?.setOnTouchListener(BlockSoftInputTouchListener)
        capacityField.editText?.setOnTouchListener(BlockSoftInputTouchListener)
        priceField.editText?.setOnTouchListener(BlockSoftInputTouchListener)

        numpadView.setInputListener { writeInFocused(it) }
        numpadView.setDeleteListener { deleteFromFocused() }

        switchActionButtonState(ActionButtonState.NEXT)
    }

    private fun initializeTextWatcher() {
        val textWatcher = DrinkPropertiesTextWatcher(presenter)

        percentField.editText?.addTextChangedListener(textWatcher)
        capacityField.editText?.addTextChangedListener(textWatcher)
        priceField.editText?.addTextChangedListener(textWatcher)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
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
        if (capacityField.editText!!.isFocused) return capacityField.editText
        else if (percentField.editText!!.isFocused) return percentField.editText
        else if (priceField.editText!!.isFocused) return priceField.editText
        throw RuntimeException("CalculateFragment: Determining focus failed.")
    }

    fun switchActionButtonState(newActionButtonState: ActionButtonState) {
        if (currentActionButtonState != newActionButtonState) {
            animateActionButton(currentActionButtonState, newActionButtonState)
            currentActionButtonState = newActionButtonState
        }
        actionButton.isClickable = (newActionButtonState != ActionButtonState.DISABLED)
    }

    private fun animateActionButton(oldState: ActionButtonState, newState: ActionButtonState) {
        val fromColor = colorToState[oldState]
        val toColor = colorToState[newState]
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), fromColor, toColor)

        colorAnimation.duration = 200
        colorAnimation.addUpdateListener { actionButtonBackground.setBackgroundColor(it.animatedValue as Int) }
        colorAnimation.start()

        val textColor = ContextCompat.getColor(context, R.color.button_text)
        val textFadeColor = ContextCompat.getColor(context, R.color.button_text_fade)
        animateActionButtonText(newState, textColor, textFadeColor)
    }

    private fun animateActionButtonText(newState: ActionButtonState, fromTextColor: Int, toTextColor: Int, repeat: Boolean = true) {
        val textColorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), fromTextColor, toTextColor)

        textColorAnimation.duration = 100
        textColorAnimation.addUpdateListener { actionButtonText.setTextColor(it.animatedValue as Int) }
        textColorAnimation.onFinishedAnimation {
            if (repeat) {
                actionButtonText.text = actionButtonTextToState[newState]
                animateActionButtonText(newState, toTextColor, fromTextColor, false)
            }
        }
        textColorAnimation.start()
    }

    fun resetState() {
        isEditing = false
        editingDrinkTextView.text = ""

        drinkIndex.text = ""

        percentField.editText!!.text.clear()
        capacityField.editText!!.text.clear()
        priceField.editText!!.text.clear()

        percentField.requestFocus()
    }

    fun onActionButtonClicked() {
        when (currentActionButtonState) {
            ActionButtonState.NEXT -> selectNextEmptyField()?.requestFocus()
            ActionButtonState.SAVE -> showSaveDialog()
            ActionButtonState.EDIT -> presenter.editDrink(getDrink())
            else -> {
                throw RuntimeException()
            }
        }
    }

    private fun showSaveDialog() {
        val dialog =
            alert {
                val dialogView = View.inflate(context, R.layout.dialog_save, null)
                title(R.string.dialog_save_title)
                customView(dialogView)

                positiveButton(R.string.save) {
                    hideKeyboard(context, dialogView.saveDialogField)
                    val retDrink = getDrink()
                    retDrink.name = dialogView.saveDialogField.editText!!.text.toString()
                    presenter.saveDrink(retDrink)
                }
                negativeButton(R.string.cancel) { hideKeyboard(context, dialogView.saveDialogField) }
            }.builder.create()

        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        dialog.show()
    }

    private fun selectNextEmptyField(): TextInputLayout? {
        if (percentField.editText!!.text.isNullOrEmpty()) return percentField
        if (priceField.editText!!.text.isNullOrEmpty()) return priceField
        if (capacityField.editText!!.text.isNullOrEmpty()) return capacityField
        else return null
    }

    override fun getDrink(): Drink {
        try {
            return Drink(
                    name = editingDrinkName,
                    percent = percentField.editText?.text.toString().toDouble(),
                    capacity = capacityField.editText?.text.toString().toDouble(),
                    price = priceField.editText?.text.toString().toDouble())
        } catch (e: Exception) {
            return Drink()
        }
    }

    override fun onAllFieldsValid() {
        presenter.calculate(getDrink())
    }

    override fun onAnyFieldInvalid() {
        drinkIndex.text = ""
        switchActionButtonState(CalculateFragment.ActionButtonState.DISABLED)
    }

    override fun onAnyEmptyField() {
        drinkIndex.text = ""
        switchActionButtonState(CalculateFragment.ActionButtonState.NEXT)
    }

    override fun onDrinkCalculated(index: Double) {
        drinkIndex.text = index.toInt().toString()
        switchActionButtonState(if (isEditing) ActionButtonState.EDIT else ActionButtonState.SAVE)
    }

    override fun loadDrinkForEdit(drink: Drink) {
        isEditing = true

        editingDrinkName = drink.name
        editingDrinkTextView.text = "${getString(R.string.editing)} $editingDrinkName"

        drinkIndex.text = drink.index.toInt().toString()
        percentField.editText?.setText(drink.percent.prettyPrint())
        priceField.editText?.setText(drink.price.prettyPrint())
        capacityField.editText?.setText(drink.capacity.prettyPrint())
    }

    override fun onSuccessfulSave() {
        resetState()
    }

    enum class ActionButtonState {
        NEXT, DISABLED, SAVE, EDIT
    }

    companion object BlockSoftInputTouchListener : View.OnTouchListener {
        override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
            view?.requestFocus()
            view?.requestFocusFromTouch()
            return true
        }
    }
}