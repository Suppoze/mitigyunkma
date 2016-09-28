package hu.suppoze.mitigyunkma.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import hu.suppoze.mitigyunkma.ui.base.Navigator

import hu.suppoze.mitigyunkma.ui.calculate.CalculateFragment
import hu.suppoze.mitigyunkma.ui.list.DrinkListHistory
import hu.suppoze.mitigyunkma.ui.base.BaseFragment
import hu.suppoze.mitigyunkma.ui.list.DrinkListBest

class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return Navigator.Pages.values().count()
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            Navigator.Pages.CALCULATE.ordinal -> return CalculateFragment()
            Navigator.Pages.HISTORY.ordinal -> return DrinkListHistory()
            Navigator.Pages.BEST.ordinal -> return DrinkListBest()
            else -> {
                throw RuntimeException("Invalid position argument in getItem()!")
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence = (getItem(position) as BaseFragment<*, *>).getTitle()
}
