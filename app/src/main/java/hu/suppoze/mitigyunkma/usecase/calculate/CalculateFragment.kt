package hu.suppoze.mitigyunkma.usecase.calculate

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import hu.suppoze.mitigyunkma.R
import hu.suppoze.mitigyunkma.entity.Drink
import hu.suppoze.mitigyunkma.extension.*
import hu.suppoze.mitigyunkma.usecase.MainActivity
import hu.suppoze.mitigyunkma.usecase.MainPagerAdapter
import hu.suppoze.mitigyunkma.usecase.NavigateToPageEvent
import hu.suppoze.mitigyunkma.usecase.common.BaseFragment
import kotlinx.android.synthetic.main.component_action_button.*
import kotlinx.android.synthetic.main.dialog_save.view.*
import kotlinx.android.synthetic.main.fragment_calculate.*
import org.greenrobot.eventbus.EventBus

class CalculateFragment : BaseFragment<CalculatePresenter, CalculateView>(), CalculateView {

    private lateinit var colorToState: HashMap<ActionButtonState, Int>
    private lateinit var actionButtonTextToState: HashMap<ActionButtonState, String>

    private var currentActionButtonState: ActionButtonState = ActionButtonState.DISABLED
    private var isEditing: Boolean = false
    private var editingDrinkName: String = ""

    override fun providePresenter(): CalculatePresenter {
        return CalculatePresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        colorToState = hashMapOf(
                Pair(ActionButtonState.DISABLED, ContextCompat.getColor(context!!, R.color.action_button_disabled)),
                Pair(ActionButtonState.NEXT, ContextCompat.getColor(context!!, R.color.action_button_next)),
                Pair(ActionButtonState.SAVE, ContextCompat.getColor(context!!, R.color.action_button_save)),
                Pair(ActionButtonState.EDIT, ContextCompat.getColor(context!!, R.color.action_button_save)))
        actionButtonTextToState = hashMapOf(
                Pair(ActionButtonState.DISABLED, activity!!.getString(R.string.next)),
                Pair(ActionButtonState.NEXT, activity!!.getString(R.string.next)),
                Pair(ActionButtonState.SAVE, activity!!.getString(R.string.save)),
                Pair(ActionButtonState.EDIT, activity!!.getString(R.string.edit)))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_calculate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeTextWatchers()
        initializeTilOnTouchListeners()

        resetButton.setOnClickListener { resetFieldsAndCancelEditing() }
        actionButton.setOnClickListener { onActionButtonClicked() }

        numpadView.onInput { writeInFocused(it) }
        numpadView.onDelete { deleteFromFocused() }

        switchActionButtonState(ActionButtonState.NEXT)
        resetDrinkIndex()
    }

    private fun initializeTextWatchers() {
        percentField.editText?.doOnTextChanged { presenter.validate(getDrink(), getFilledFields()) }
        capacityField.editText?.doOnTextChanged { presenter.validate(getDrink(), getFilledFields()) }
        priceField.editText?.doOnTextChanged { presenter.validate(getDrink(), getFilledFields()) }
    }

    private fun initializeTilOnTouchListeners() {
        val blockSoftInputTouchListener = View.OnTouchListener { view, motionEvent ->
            view?.requestFocus()
            view?.requestFocusFromTouch()
            true
        }
        percentField.editText?.setOnTouchListener(blockSoftInputTouchListener)
        capacityField.editText?.setOnTouchListener(blockSoftInputTouchListener)
        priceField.editText?.setOnTouchListener(blockSoftInputTouchListener)
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

        val textColor = ContextCompat.getColor(context!!, R.color.button_text)
        val textFadeColor = ContextCompat.getColor(context!!, R.color.button_text_fade)
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

    private fun resetFieldsAndCancelEditing() {
        presenter.cancelEditDrink()
        if (activity is MainActivity) (activity as MainActivity).refreshTitle()

        isEditing = false
        drinkIndex.text = ""
        percentField.editText!!.text.clear()
        capacityField.editText!!.text.clear()
        priceField.editText!!.text.clear()

        percentField.requestFocus()
    }

    private fun onActionButtonClicked() {
        when (currentActionButtonState) {
            ActionButtonState.NEXT -> findEmptyField()?.requestFocus()
            ActionButtonState.SAVE -> showSaveDialog()
            ActionButtonState.EDIT -> presenter.saveOrEditDrink(getDrink())
            else -> {
                throw RuntimeException()
            }
        }
    }

    private fun showSaveDialog() {
        val dialogContent = View.inflate(context, R.layout.dialog_save, null)
        val dialog = AlertDialog.Builder(context!!, R.style.MyAlertDialogStyle)
                .setTitle(R.string.dialog_save_title)
                .setView(dialogContent)
                .setPositiveButton(R.string.save, { dialogInterface, i ->
                    context?.hideKeyboard(dialogContent.saveDialogField)
                    val retDrink = getDrink()
                    retDrink.name = dialogContent.saveDialogField.editText!!.text.toString()
                    presenter.saveOrEditDrink(retDrink)
                })
                .setNegativeButton(R.string.cancel, { dialogInterface, i ->
                    context?.hideKeyboard(dialogContent.saveDialogField)
                })
                .create()
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        dialog.show()
    }

    private fun findEmptyField(): TextInputLayout? {
        if (percentField.editText!!.text.isNullOrEmpty()) return percentField
        if (priceField.editText!!.text.isNullOrEmpty()) return priceField
        if (capacityField.editText!!.text.isNullOrEmpty()) return capacityField
        else return null
    }

    // TODO: create seperate Drink model for views
    override fun getDrink(): Drink {
        val percentText = percentField.editText?.text.toString()
        val capacityText = capacityField.editText?.text.toString()
        val priceText = priceField.editText?.text.toString()

        return Drink(
                name = editingDrinkName,
                percent = if (percentText.isNullOrEmpty() || percentText == ".") .0 else percentText.toDouble(),
                capacity = if (capacityText.isNullOrEmpty() || capacityText == ".") .0 else capacityText.toDouble(),
                price = if (priceText.isNullOrEmpty() || priceText == ".") .0 else priceText.toDouble())
    }

    // TODO: another method of this
    private fun getFilledFields(): Array<Boolean> =
            arrayOf(!percentField.editText?.text.isNullOrEmpty(),
                    !priceField.editText?.text.isNullOrEmpty(),
                    !capacityField.editText?.text.isNullOrEmpty())

    private fun resetDrinkIndex() {
        drinkIndex.text = getString(R.string.invalid_drink_index)
        drinkIndex.setTextColorWithAnimation(ContextCompat.getColor(context!!, R.color.rating_none))
    }

    override fun onHasEmptyFields() {
        resetDrinkIndex()
        switchActionButtonState(CalculateFragment.ActionButtonState.NEXT)
    }

    override fun onAllFieldsValid() {
        presenter.calculate(getDrink())
    }

    override fun onHasInvalidFields() {
        resetDrinkIndex()
        switchActionButtonState(CalculateFragment.ActionButtonState.DISABLED)
    }

    override fun onPercentValid() {
        percentField.isErrorEnabled = false
        percentField.error = null
    }

    override fun onPercentInvalid() {
        percentField.isErrorEnabled = true
        percentField.error = null
    }

    override fun onPriceValid() {
        priceField.isErrorEnabled = false
        priceField.error = null
    }

    override fun onPriceInvalid() {
        priceField.isErrorEnabled = true
        priceField.error = null
    }

    override fun onCapacityValid() {
        capacityField.isErrorEnabled = false
        capacityField.error = null
    }

    override fun onCapacityInvalid() {
        capacityField.isErrorEnabled = true
        capacityField.error = null
    }

    override fun onDrinkCalculated(index: Double, rating: Double) {
        drinkIndex.text = index.toInt().toString()
        drinkIndex.setTextColorBasedOnRating(rating, context)
        switchActionButtonState(if (isEditing) ActionButtonState.EDIT else ActionButtonState.SAVE)
    }

    override fun loadDrinkForEdit(drink: Drink) {
        isEditing = true

        editingDrinkName = drink.name
        val concatenated = "${getString(R.string.editing)} $editingDrinkName"

        drinkIndex.text = drink.index.toInt().toString()
        percentField.editText?.setText(drink.percent.prettyPrint())
        priceField.editText?.setText(drink.price.prettyPrint())
        capacityField.editText?.setText(drink.capacity.prettyPrint())
    }

    override fun onSuccessfulSaveOrEdit() {
        resetFieldsAndCancelEditing()
        EventBus.getDefault().post(NavigateToPageEvent(MainPagerAdapter.Page.HISTORY))
    }

    enum class ActionButtonState {
        NEXT, DISABLED, SAVE, EDIT
    }
}