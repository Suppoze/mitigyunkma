package hu.suppoze.mitigyunkma

import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import butterknife.*
import hu.suppoze.mitigyunkma.R
import hu.suppoze.mitigyunkma.MainPagerAdapter

class MainActivity : AppCompatActivity() {

    @Bind(R.id.activity_main_appbar) lateinit var appBarLayout: AppBarLayout
    @Bind(R.id.activity_main_toolbar) lateinit var toolbar: Toolbar
    @Bind(R.id.activity_main_tablayout) lateinit var tabLayout: TabLayout
    @Bind(R.id.activity_main_viewpager) lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        initializeActionBar()
        initializeTabLayout()
        initializeViewPager()
        setListenerForTabLayout()
    }

    private fun initializeActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar.setDisplayHomeAsUpEnabled(true)
        supportActionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher)
    }

    private fun initializeTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.calculate_view_title))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.history_view_title))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.bestof_view_title))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
    }

    private fun initializeViewPager() {
        val adapter = MainPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
    }

    private fun setListenerForTabLayout() {

        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
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
