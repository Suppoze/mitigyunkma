package hu.suppoze.mitigyunkma.ui.base

import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import com.pascalwelsch.compositeandroid.fragment.CompositeFragment
import hu.suppoze.mitigyunkma.ui.MainActivity
import net.grandcentrix.thirtyinch.TiFragment
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.TiView
import org.jetbrains.anko.inputMethodManager

abstract class BaseFragment<P : TiPresenter<V>, V : TiView> : TiFragment<P, V>() {

    fun hideKeyboard(context: Context, view: View) =
        context.inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

    abstract fun getTitle(): CharSequence

}
