package hu.suppoze.mitigyunkma.usecase

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import hu.suppoze.mitigyunkma.usecase.calculate.CalculateFragment
import hu.suppoze.mitigyunkma.usecase.list.DrinkListHistory
import hu.suppoze.mitigyunkma.usecase.common.BaseFragment
import hu.suppoze.mitigyunkma.usecase.list.DrinkListBest
import java.util.*

class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    // Lazy implementation
    private val pages: HashMap<Int, () -> Fragment> = hashMapOf(
            Pair(Position.CALCULATE.ordinal, { CalculateFragment() }),
            Pair(Position.HISTORY.ordinal, { DrinkListHistory() }),
            Pair(Position.BEST.ordinal, { DrinkListBest() })
    )

    override fun getCount(): Int {
        return pages.count()
    }

    override fun getItem(position: Int): Fragment {
        return pages[position]!!.invoke()
    }

    override fun getPageTitle(position: Int): CharSequence = (getItem(position) as BaseFragment<*, *>).getTitle()

    enum class Position {
        CALCULATE,
        HISTORY,
        BEST
    }
}