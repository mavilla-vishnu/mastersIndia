package co.mastersindia.autotax.adapter;

/**
 * Created by Pandu on 9/2/2017.
 */


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import co.mastersindia.autotax.fragments.Goods_fragment;
import co.mastersindia.autotax.fragments.Service_fragment;

public class PageAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;

    public PageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Goods_fragment tab1 = new Goods_fragment();
                return tab1;
            case 1:
                Service_fragment tab2 = new Service_fragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}