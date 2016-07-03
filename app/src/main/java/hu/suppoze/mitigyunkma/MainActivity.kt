package hu.suppoze.mitigyunkma

import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

import hu.suppoze.mitigyunkma.base.Navigator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeActionBar()
        initializeTabLayout()
        initializeViewPager()
        setListenerForTabLayout()
    }

    private fun initializeActionBar() {
        setSupportActionBar(mainActivityToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.mipmap.ic_launcher)
    }

    private fun initializeTabLayout() {
        mainActivityTablayout.addTab(mainActivityTablayout.newTab().setText(R.string.calculate_view_title))
        mainActivityTablayout.addTab(mainActivityTablayout.newTab().setText(R.string.history_view_title))
        mainActivityTablayout.addTab(mainActivityTablayout.newTab().setText(R.string.bestof_view_title))
        mainActivityTablayout.tabGravity = TabLayout.GRAVITY_FILL
    }

    private fun initializeViewPager() {
        val adapter = MainPagerAdapter(supportFragmentManager)
        mainActivityViewpager.adapter = adapter
        mainActivityViewpager.offscreenPageLimit = adapter.count
        mainActivityViewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(mainActivityTablayout))
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

}
