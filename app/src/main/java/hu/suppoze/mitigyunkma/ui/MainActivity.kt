package hu.suppoze.mitigyunkma.ui

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import hu.suppoze.mitigyunkma.R
import hu.suppoze.mitigyunkma.extension.setIconColorStateList
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeActionBar()
        initializeTabLayout()
        initializeViewPager()
    }

    override fun onStart() {
        EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
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
    }

    private fun setListenerForTabLayout() {
        mainActivityTabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                mainActivityViewpager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
    }

    private fun setListenerForViewPager() {
        mainActivityViewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                if (position == MainPagerAdapter.Position.CALCULATE.ordinal) {
                    mainActivityAppbar.setExpanded(true, true)
                }
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNavigateToHistoryEvent(event: NavigateToHistoryEvent) {
        mainActivityViewpager.currentItem = MainPagerAdapter.Position.HISTORY.ordinal
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNavigateToCalculateEvent(event: NavigateToCalculateEvent) {
        mainActivityViewpager.currentItem = MainPagerAdapter.Position.CALCULATE.ordinal
    }
}