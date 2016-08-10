package hu.suppoze.mitigyunkma

import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewPager
import hu.suppoze.mitigyunkma.`interface`.ToolbarCollapseController
import hu.suppoze.mitigyunkma.extensions.setIconColorStateList
import kotlinx.android.synthetic.main.activity_main.*

import hu.suppoze.mitigyunkma.modules.base.Navigator

class MainActivity : AppCompatActivity(), ToolbarCollapseController {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeActionBar()
        initializeTabLayout()
        initializeViewPager()
    }

    override fun onResume() {
        super.onResume()
        title = mainActivityViewpager.adapter.getPageTitle(mainActivityViewpager.currentItem)
    }

    private fun initializeActionBar() {
        setSupportActionBar(mainActivityToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun initializeTabLayout() {
        mainActivityTabLayout.tabGravity = TabLayout.GRAVITY_FILL
        mainActivityTabLayout.setIconColorStateList(this, R.color.selector_tablayout_icon)
        setListenerForTabLayout()
    }

    private fun initializeViewPager() {
        val adapter = MainPagerAdapter(supportFragmentManager)
        mainActivityViewpager.adapter = adapter
        mainActivityViewpager.offscreenPageLimit = adapter.count
        mainActivityViewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(mainActivityTabLayout))
        setListenerForViewPager()
        Navigator.viewPager = mainActivityViewpager
    }

    private fun setListenerForTabLayout() {
        mainActivityTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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
                title = mainActivityViewpager.adapter.getPageTitle(position)
                if (position == Navigator.Pages.CALCULATE.ordinal) {
                    mainActivityAppbar.setExpanded(true)
                }
            }

            override fun onPageScrollStateChanged(state: Int) { }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) { }
        })
    }

    override val turnOffToolbarScrolling : () -> Unit = {
        (mainActivityToolbar.layoutParams as AppBarLayout.LayoutParams).scrollFlags = 0
        (mainActivityAppbar.layoutParams as CoordinatorLayout.LayoutParams).behavior = null
    }

    override val turnOnToolbarScrolling : () -> Unit = {
        (mainActivityToolbar.layoutParams as AppBarLayout.LayoutParams).scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS or AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
        (mainActivityAppbar.layoutParams as CoordinatorLayout.LayoutParams).behavior = AppBarLayout.Behavior()
    }
}