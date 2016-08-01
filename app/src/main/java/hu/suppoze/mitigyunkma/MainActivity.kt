package hu.suppoze.mitigyunkma

import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

import hu.suppoze.mitigyunkma.modules.base.Navigator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeActionBar()
        initializeTabLayout()
        initializeViewPager()
    }

    private fun initializeActionBar() {
        setSupportActionBar(mainActivityToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun initializeTabLayout() {
        mainActivityTablayout.addTab(mainActivityTablayout.newTab().setText(R.string.calculate_view_title))
        mainActivityTablayout.addTab(mainActivityTablayout.newTab().setText(R.string.history_view_title))
        mainActivityTablayout.addTab(mainActivityTablayout.newTab().setText(R.string.bestof_view_title))
        mainActivityTablayout.tabGravity = TabLayout.GRAVITY_FILL
        setListenerForTabLayout()
    }

    private fun initializeViewPager() {
        val adapter = MainPagerAdapter(supportFragmentManager)
        mainActivityViewpager.adapter = adapter
        mainActivityViewpager.offscreenPageLimit = adapter.count
        mainActivityViewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(mainActivityTablayout))
        setListenerForViewPager()
        Navigator.viewPager = mainActivityViewpager
    }

    private fun setListenerForTabLayout() {
        mainActivityTablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                mainActivityViewpager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) { }
            override fun onTabReselected(tab: TabLayout.Tab) { }
        })
    }

    private fun setListenerForViewPager() {
        mainActivityViewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                if (position == Navigator.Pages.CALCULATE.ordinal) {
                    mainActivityAppbar.setExpanded(true)
                }
            }

            override fun onPageScrollStateChanged(state: Int) { }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) { }
        })
    }

    fun turnOffToolbarScrolling() {
        (mainActivityToolbar.layoutParams as AppBarLayout.LayoutParams).scrollFlags = 0
        (mainActivityAppbar.layoutParams as CoordinatorLayout.LayoutParams).behavior = null
    }

    fun turnOnToolbarScrolling() {
        (mainActivityToolbar.layoutParams as AppBarLayout.LayoutParams).scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS or AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
        (mainActivityAppbar.layoutParams as CoordinatorLayout.LayoutParams).behavior = AppBarLayout.Behavior()
    }
}
