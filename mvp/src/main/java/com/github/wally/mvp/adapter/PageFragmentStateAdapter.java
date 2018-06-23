package com.github.wally.mvp.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Package: com.github.wally.mvp.adapter
 * FileName: FragmentPageAdapter
 * Date: on 2018/6/23  下午11:22
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class PageFragmentStateAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private List<String> mTitles = new ArrayList<String>();

    public PageFragmentStateAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    public void setDatas(List<Fragment> fragments, List<String> titles) {
        this.mFragments.clear();
        this.mTitles.clear();
        addDatas(fragments, titles);
    }

    public void addDatas(List<Fragment> fragments, List<String> titles) {
        this.mFragments.addAll(fragments);
        this.mTitles.addAll(titles);
    }
}
