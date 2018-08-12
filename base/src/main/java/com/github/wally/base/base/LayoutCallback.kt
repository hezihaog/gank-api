package com.github.wally.base.base

import android.view.View

/**
 * Package: com.github.wally.mvp.base
 * FileName: LayoutCallback
 * Date: on 2018/6/17  上午8:53
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
interface LayoutCallback {
    /**
     * 设置布局前回调
     */
    fun onLayoutBefore()

    /**
     * 获取布局id
     */
    fun onLayoutId(): Int

    /**
     * 设置布局后回调
     */
    fun onLayoutAfter()

    /**
     * 查找控件时回调
     */
    fun onFindView(rootView: View)

    /**
     * 查找完控件，绑定控件内容时回调
     */
    fun onBindViewContent()
}