package hu.suppoze.mitigyunkma.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import hu.suppoze.mitigyunkma.R
import hu.suppoze.mitigyunkma.ui.base.Navigator

import hu.suppoze.mitigyunkma.ui.calculate.CalculateFragment
import hu.suppoze.mitigyunkma.ui.list.DrinkListFragment
import hu.suppoze.mitigyunkma.entity.Drink
import hu.suppoze.mitigyunkma.util.ResourceHelper

class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return Navigator.Pages.values().count()
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            Navigator.Pages.CALCULATE.ordinal -> return CalculateFragment()
            Navigator.Pages.HISTORY.ordinal -> return DrinkListFragment(Drink::lastmod.name)
            Navigator.Pages.BEST.ordinal -> return DrinkListFragment(Drink::index.name)
            else -> {
                throw RuntimeException("Invalid position argument in getItem()!")
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            Navigator.Pages.CALCULATE.ordinal -> return ResourceHelper.getStringRes(R.string.calculate_view_title)
            Navigator.Pages.HISTORY.ordinal -> return ResourceHelper.getStringRes(R.string.history_view_title)
            Navigator.Pages.BEST.ordinal -> return ResourceHelper.getStringRes(R.string.bestof_view_title)
            else -> {
                throw RuntimeException("Invalid position argument in getPageTitle()!")
            }
        }
    }
}
