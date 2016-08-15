package hu.suppoze.mitigyunkma.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import hu.suppoze.mitigyunkma.ui.MainActivity
import hu.suppoze.mitigyunkma.R
import org.jetbrains.anko.inputMethodManager
import org.jetbrains.anko.support.v4.alert

abstract class BaseFragment : Fragment() {

    fun hideKeyboard(context: Context, view: View) =
        context.inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

}
