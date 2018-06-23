package com.github.wally.mvp.util;

import android.os.Bundle;

import com.github.wally.mvp.bean.gank.DisplayMeiZiImageBean;
import com.github.wally.mvp.constants.Constants;
import com.github.wally.mvp.ui.fragment.GankMeiZiDetailFragment;
import com.github.wally.mvp.ui.fragment.RandomMeiZiFragment;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Package: com.github.wally.mvp.util
 * FileName: UIHelper
 * Date: on 2018/6/18  下午1:46
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class UIHelper {
    private UIHelper() {
    }

    public static void showMeiZiDetail(SupportActivity activity, DisplayMeiZiImageBean bean) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.Key.GANK_MEIZI_BEAN, bean);
        GankMeiZiDetailFragment fragment = FragmentFactory.newInstance(activity, GankMeiZiDetailFragment.class, args);
        activity.start(fragment);
    }

    public static void showRandomMeiZiList(SupportActivity activity) {
        activity.start(new RandomMeiZiFragment());
    }
}