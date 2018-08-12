package com.github.wally.base.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import java.util.*

/**
 * Package: com.github.wally.gank.adapter
 * FileName: FragmentPageAdapter
 * Date: on 2018/6/23  下午11:22
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class PageFragmentStateAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val mFragments = ArrayList<Fragment>()
    private val mTitles = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }

    fun setDatas(fragments: List<Fragment>, titles: List<String>) {
        this.mFragments.clear()
        this.mTitles.clear()
        addDatas(fragments, titles)
    }

    fun addDatas(fragments: List<Fragment>, titles: List<String>) {
        this.mFragments.addAll(fragments)
        this.mTitles.addAll(titles)
    }
}