package hu.suppoze.mitigyunkma.ui.base

import net.grandcentrix.thirtyinch.TiFragment
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.TiView

abstract class BaseFragment<P : TiPresenter<V>, V : TiView> : TiFragment<P, V>() {

    abstract fun getTitle(): CharSequence

}
