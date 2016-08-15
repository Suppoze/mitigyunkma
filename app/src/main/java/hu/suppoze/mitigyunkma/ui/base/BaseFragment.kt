package hu.suppoze.mitigyunkma.ui.base

import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import org.jetbrains.anko.inputMethodManager

abstract class BaseFragment : Fragment() {

    fun hideKeyboard(context: Context, view: View) =
        context.inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

}
