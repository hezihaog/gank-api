package com.github.wally.gank.util

import android.os.Bundle
import com.github.wally.base.util.FragmentFactory

import com.github.wally.gank.bean.gank.DisplayMeiZiImageBean
import com.github.wally.gank.constants.Constants
import com.github.wally.gank.mvp.view.GankMeiZiDetailFragment
import com.github.wally.gank.mvp.view.GankSearchFragment
import com.github.wally.gank.mvp.view.RandomMeiZiFragment
import com.haoge.easyandroid.easy.EasyBundle

import me.yokeyword.fragmentation.SupportActivity

/**
 * Package: com.github.wally.gank.util
 * FileName: UIHelper
 * Date: on 2018/6/18  下午1:46
 * Auther: zihe
 * Descirbe:跳转帮助类
 * Email: hezihao@linghit.com
 */
object UIHelper {
    /**
     * 妹子图详情页面
     *
     * @param bean 妹子图显示需要的素材
     */
    fun showMeiZiDetail(activity: SupportActivity, bean: DisplayMeiZiImageBean) {
        val args :Bundle = EasyBundle.create().put(Constants.Key.GANK_MEIZI_BEAN to bean).bundle
        val fragment = FragmentFactory.newInstance(activity, GankMeiZiDetailFragment::class.java, args)
        activity.start(fragment)
    }

    /**
     * 随机妹子图列表
     */
    fun showRandomMeiZiList(activity: SupportActivity) {
        activity.start(RandomMeiZiFragment())
    }

    /**
     * 搜索分类
     */
    fun showSearchCategory(activity: SupportActivity) {
        activity.start(GankSearchFragment())
    }
}