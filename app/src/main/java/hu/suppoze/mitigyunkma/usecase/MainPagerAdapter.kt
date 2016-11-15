package hu.suppoze.mitigyunkma.usecase

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import hu.suppoze.mitigyunkma.MitigyunkApp
import hu.suppoze.mitigyunkma.R
import hu.suppoze.mitigyunkma.core.SharedPreferencesRepository
import hu.suppoze.mitigyunkma.usecase.calculate.CalculateFragment
import hu.suppoze.mitigyunkma.usecase.list.DrinkListBest
import hu.suppoze.mitigyunkma.usecase.list.DrinkListHistory
import java.util.*
import javax.inject.Inject

class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    @Inject lateinit var context: Context
    @Inject lateinit var sharedPreferencesRepository: SharedPreferencesRepository

    init {
        MitigyunkApp.appComponent.inject(this)
    }

    // Lazy implementation
    private val pageFragments: HashMap<Int, () -> Fragment> = hashMapOf(
            Pair(Page.CALCULATE.ordinal, { CalculateFragment() }),
            Pair(Page.HISTORY.ordinal, { DrinkListHistory() }),
            Pair(Page.BEST.ordinal, { DrinkListBest() })
    )

    override fun getCount(): Int {
        return pageFragments.count()
    }

    override fun getItem(position: Int): Fragment {
        return pageFragments[position]!!.invoke()
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            Page.CALCULATE.ordinal -> getCalculatePageTitle()
            Page.HISTORY.ordinal -> context.getString(R.string.history_view_title)
            Page.BEST.ordinal -> context.getString(R.string.bestof_view_title)
            else -> context.getString(R.string.calculate_view_drink_index)
        }
    }

    private fun getCalculatePageTitle(): CharSequence {
        if (sharedPreferencesRepository.isEditingDrink())
            return "${context.getString(R.string.calculate_view_title_edit_prefix)} ${sharedPreferencesRepository.getEditingDrinkName()}"
        else
            return context.getString(R.string.calculate_view_title)
    }

    enum class Page {
        CALCULATE,
        HISTORY,
        BEST
    }
}