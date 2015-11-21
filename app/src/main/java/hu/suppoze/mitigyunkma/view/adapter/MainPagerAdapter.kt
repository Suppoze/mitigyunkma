package hu.suppoze.mitigyunkma.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log

import hu.suppoze.mitigyunkma.view.fragment.CalculateFragment

class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return PAGERADAPTER_NUMBER_OF_OPTIONS
    }

    override fun getItem(position: Int): Fragment {
        val calculateFragment = CalculateFragment()
        when (position) {
            0 -> return calculateFragment
            1 -> return calculateFragment
            2 -> return calculateFragment
            else -> {
                Log.e("Mitigyunkma", "Invalid position argument in getItem()!")
                throw RuntimeException("Invalid position argument in getItem()!")
            }
        }
    }

    companion object {
        private val PAGERADAPTER_NUMBER_OF_OPTIONS = 3
    }

}
