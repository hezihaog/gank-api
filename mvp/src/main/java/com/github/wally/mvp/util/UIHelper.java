package com.github.wally.mvp.util;

import android.os.Bundle;

import com.github.wally.mvp.bean.gank.DisplayMeiZiImageBean;
import com.github.wally.mvp.constants.Constants;
import com.github.wally.mvp.ui.fragment.GankMeiZiDetailFragment;
import com.github.wally.mvp.ui.fragment.GankSearchFragment;
import com.github.wally.mvp.ui.fragment.RandomMeiZiFragment;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Package: com.github.wally.mvp.util
 * FileName: UIHelper
 * Date: on 2018/6/18  下午1:46
 * Auther: zihe
 * Descirbe:跳转帮助类
 * Email: hezihao@linghit.com
 */
public class UIHelper {
    private UIHelper() {
    }

    /**
     * 妹子图详情页面
     *
     * @param bean 妹子图显示需要的素材
     */
    public static void showMeiZiDetail(SupportActivity activity, DisplayMeiZiImageBean bean) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.Key.INSTANCE.getGANK_MEIZI_BEAN(), bean);
        GankMeiZiDetailFragment fragment = FragmentFactory.INSTANCE.newInstance(activity, GankMeiZiDetailFragment.class, args);
        activity.start(fragment);
    }

    /**
     * 随机妹子图列表
     */
    public static void showRandomMeiZiList(SupportActivity activity) {
        activity.start(new RandomMeiZiFragment());
    }

    /**
     * 搜索分类
     */
    public static void showSearchCategory(SupportActivity activity) {
        activity.start(new GankSearchFragment());
    }
}