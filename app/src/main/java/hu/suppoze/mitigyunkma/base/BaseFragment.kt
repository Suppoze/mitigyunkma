package hu.suppoze.mitigyunkma.base

import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import hu.suppoze.mitigyunkma.R
import org.jetbrains.anko.inputMethodManager
import org.jetbrains.anko.support.v4.alert

abstract class BaseFragment : Fragment() {

    fun showAlert(message: String) {
        alert(message) {
            positiveButton("OK") { }
        }.show()
    }

    fun showError(messageId: Int) {
        alert(messageId) {
            title(R.string.error_alert_title)
            positiveButton(R.string.error_alert_positive) { }
        }.show()
    }

    fun showKeyboard(context: Context, view: View) =
        context.inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)

    fun hideKeyboard(context: Context, view: View) =
        context.inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

    fun switchInputError(editText: EditText, error: String? = null) {
        editText.error = error
    }
}
