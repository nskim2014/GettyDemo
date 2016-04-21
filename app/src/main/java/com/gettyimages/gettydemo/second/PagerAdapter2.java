package com.gettyimages.gettydemo.second;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by namseok on 16. 4. 17..
 */
public class PagerAdapter2 extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    ListFragment2 mListFrag;
    GridFragment2 mGridFrag;

    public PagerAdapter2(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        mListFrag = new ListFragment2();
        mGridFrag = new GridFragment2();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mListFrag;
            case 1:
                return mGridFrag;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}

