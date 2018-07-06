package com.github.wally.mvp.ui.activity;

import android.os.Bundle;

import com.github.wally.mvp.R;
import com.github.wally.mvp.base.BaseRxActivity;
import com.github.wally.mvp.ui.fragment.GankMeiZiListFragment;
import com.github.wally.mvp.ui.fragment.MainFragment;
import com.gyf.barlibrary.ImmersionBar;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends BaseRxActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImmersionBar.with(this).init();
        if (findFragment(GankMeiZiListFragment.class) == null) {
            loadRootFragment(R.id.root, new MainFragment());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}