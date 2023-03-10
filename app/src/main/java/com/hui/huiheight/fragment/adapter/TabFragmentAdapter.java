package com.hui.huiheight.fragment.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

import walke.base.BaseFragment;


/**
 * Created by walke.Z on 2017/8/2.
 */

public class TabFragmentAdapter extends FragmentPagerAdapter {
    String[] tabs;
    List<BaseFragment> pageFragments;

    public TabFragmentAdapter(FragmentManager fm, String[] tabs, List<BaseFragment> pageFragments) {
        super(fm);
        this.tabs = tabs;
        this.pageFragments = pageFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return pageFragments.get(position);
    }

    @Override
    public int getCount() {
        return pageFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        return super.getPageTitle(position);
        return tabs[position];
    }
}
