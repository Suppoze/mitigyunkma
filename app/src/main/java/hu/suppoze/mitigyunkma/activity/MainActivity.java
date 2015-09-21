package hu.suppoze.mitigyunkma.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.InjectView;
import hu.suppoze.mitigyunkma.R;
import hu.suppoze.mitigyunkma.view.adapter.MainPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.activity_main_appbar) AppBarLayout appBarLayout;
    @InjectView(R.id.activity_main_toolbar) Toolbar toolbar;
    @InjectView(R.id.activity_main_tablayout) TabLayout tabLayout;
    @InjectView(R.id.activity_main_viewpager) ViewPager viewPager;

    private static final float TOOLBAR_ELEVATION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initializeActionBar();
        initializeTabLayout();
        initializeViewPager();
        setListenerForTabLayout();
    }

    private void initializeActionBar() {
        setSupportActionBar(toolbar);
        ViewCompat.setElevation(appBarLayout, TOOLBAR_ELEVATION);
    }

    private void initializeTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private void initializeViewPager() {
        final MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void setListenerForTabLayout() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
