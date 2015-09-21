package hu.suppoze.mitigyunkma.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import hu.suppoze.mitigyunkma.view.fragment.CalculateFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private static final int PAGERADAPTER_NUMBER_OF_OPTIONS = 3;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGERADAPTER_NUMBER_OF_OPTIONS;
    }

    @Override
    public Fragment getItem(int position) {
        CalculateFragment calculateFragment = new CalculateFragment();
        switch (position) {
            case 0:
                return calculateFragment;
            case 1:
                return calculateFragment;
            case 2:
                return calculateFragment;
            default:
                Log.e("Mitigyunkma", "Invalid position argument in getItem()!");
                throw new RuntimeException("Invalid position argument in getItem()!");
        }
    }

}
