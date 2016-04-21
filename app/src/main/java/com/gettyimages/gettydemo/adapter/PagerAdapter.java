package com.gettyimages.gettydemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gettyimages.gettydemo.fragment.GridFragment;
import com.gettyimages.gettydemo.fragment.ListFragment;
import com.gettyimages.gettydemo.second.GridFragment2;
import com.gettyimages.gettydemo.second.ListFragment2;

/**
 * Created by namseok on 16. 4. 17..
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    ListFragment mListFrag;
    GridFragment mGridFrag;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        mGridFrag = new GridFragment();
        mListFrag = new ListFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mGridFrag;
            case 1:
                return mListFrag;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
