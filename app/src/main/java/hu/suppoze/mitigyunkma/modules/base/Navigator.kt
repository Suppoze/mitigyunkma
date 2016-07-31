package hu.suppoze.mitigyunkma.modules.base

import android.support.v4.view.ViewPager

object Navigator {

    lateinit var viewPager: ViewPager

    fun navigate(page: Pages) {
        viewPager.currentItem = page.ordinal
    }

    enum class Pages {
        CALCULATE,
        HISTORY,
        BEST
    }
}
