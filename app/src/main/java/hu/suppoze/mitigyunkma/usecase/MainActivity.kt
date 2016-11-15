package hu.suppoze.mitigyunkma.usecase

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

    fun refreshTitle() {
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
        val onPageChangeListener = object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                if (position == MainPagerAdapter.Page.CALCULATE.ordinal) {
                    mainActivityAppbar.setExpanded(true, true)
                }
                title = mainActivityViewpager.adapter.getPageTitle(position)
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        }
        mainActivityViewpager.addOnPageChangeListener(onPageChangeListener)
        mainActivityViewpager.post { onPageChangeListener.onPageSelected(MainPagerAdapter.Page.CALCULATE.ordinal) }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNavigateToPage(event: NavigateToPageEvent) {
        mainActivityViewpager.currentItem = event.page.ordinal
    }
}