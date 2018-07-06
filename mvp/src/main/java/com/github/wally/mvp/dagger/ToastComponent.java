package com.github.wally.mvp.dagger;

import com.github.wally.mvp.ui.activity.MainActivity;
import com.github.wally.mvp.ui.fragment.GankMeiZiDetailFragment;
import com.github.wally.mvp.ui.fragment.GankMeiZiListFragment;
import com.github.wally.mvp.ui.fragment.GankSearchCategoryListFragment;
import com.github.wally.mvp.ui.fragment.GankSearchFragment;
import com.github.wally.mvp.ui.fragment.MainFragment;
import com.github.wally.mvp.ui.fragment.RandomMeiZiFragment;

import dagger.Component;

/**
 * Package: com.github.wally.mvp.dagger
 * FileName: ToastComponent
 * Date: on 2018/7/6  上午11:04
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
@Component(dependencies = AppComponent.class)
public interface ToastComponent {
    MainActivity inject(MainActivity activity);

    GankMeiZiDetailFragment inject(GankMeiZiDetailFragment fragment);

    GankMeiZiListFragment inject(GankMeiZiListFragment fragment);

    RandomMeiZiFragment inject(RandomMeiZiFragment fragment);

    MainFragment inject(MainFragment fragment);

    GankSearchFragment inject(GankSearchFragment fragment);

    GankSearchCategoryListFragment inject(GankSearchCategoryListFragment fragment);
}