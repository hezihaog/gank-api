package com.github.wally.mvp.ui.activity

import android.os.Bundle
import com.github.wally.mvp.R
import com.github.wally.mvp.base.BaseRxActivity
import com.github.wally.mvp.ui.fragment.MainFragment
import com.gyf.barlibrary.ImmersionBar
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class MainActivity : BaseRxActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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