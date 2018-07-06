package com.github.wally.mvp.ui.activity;

import android.os.Bundle;

import com.github.wally.mvp.R;
import com.github.wally.mvp.base.BaseRxActivity;
import com.github.wally.mvp.dagger.AppComponent;
import com.github.wally.mvp.dagger.DaggerToastComponent;
import com.github.wally.mvp.ui.fragment.GankMeiZiListFragment;
import com.github.wally.mvp.ui.fragment.MainFragment;
import com.github.wally.mvp.util.ToastHelper;
import com.gyf.barlibrary.ImmersionBar;

import javax.inject.Inject;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends BaseRxActivity {
    @Inject
    ToastHelper mToastHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImmersionBar.with(this).init();
        if (findFragment(GankMeiZiListFragment.class) == null) {
            loadRootFragment(R.id.root, new MainFragment());
        }
        mToastHelper.show("hello~");
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerToastComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
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