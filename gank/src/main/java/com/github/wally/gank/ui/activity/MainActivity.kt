package com.github.wally.gank.ui.activity

import com.github.wally.base.base.BaseRxActivity
import com.github.wally.gank.R
import com.github.wally.gank.mvp.view.MainFragment
import com.gyf.barlibrary.ImmersionBar
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class MainActivity : BaseRxActivity() {
    override fun onLayoutId(): Int {
        return R.layout.activity_main;
    }

    override fun onLayoutAfter() {
        super.onLayoutAfter()
        ImmersionBar.with(this).init()
        if (findFragment(MainFragment::class.java) == null) {
            loadRootFragment(R.id.root, MainFragment())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ImmersionBar.with(this).destroy()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}