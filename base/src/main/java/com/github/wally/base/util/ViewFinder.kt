/*
 * Copyright 2017 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2017-03-11 22:24:54
 *
 * GitHub:  https://github.com/GcsSloop
 * Website: http://www.gcssloop.com
 * Weibo:   http://weibo.com/GcsSloop
 */

package com.github.wally.base.util

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.annotation.IdRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.github.wally.base.image.ImageLoader
import com.github.wally.base.image.LoadImageCallback


/**
 * View查找器，封装一些常用方法
 *
 * @author 子和
 */
class ViewFinder {
    private var mViews: HashMap<Int, View>

    /**
     * 获取布局View
     *
     * @return 布局View
     */
    private var rootView: View
    /**
     * 获取Context
     */
    private var context: Context

    constructor(context: Context, inflater: LayoutInflater, parent: ViewGroup, layoutId: Int) {
        this.context = context
        this.mViews = createCacheMap()
        this.rootView = inflater.inflate(layoutId, parent, false)
    }

    constructor(context: Context, rootLayout: View) {
        this.context = context
        this.mViews = createCacheMap()
        this.rootView = rootLayout
    }

    fun getContext(): Context {
        return context
    }

    fun getRootView(): View {
        return rootView
    }

    private fun createCacheMap(): HashMap<Int, View> {
        return HashMap(5)
    }

    /**
     * 通过View的id来获取子View
     *
     * @param resId view的id
     * @param <T>   泛型
     * @return View
     */
    fun <T : View> findView(@IdRes resId: Int): T {
        var view: View? = mViews.get(resId)
        //如果该View没有缓存过，则查找View并缓存
        if (view == null) {
            view = rootView.findViewById(resId)
            mViews.put(resId, view)
        }
        return view as T
    }

    /**
     * 判断字符串是否为空
     *
     * @param str 要判断的字符串
     */
    fun isEmpty(str: CharSequence?): Boolean {
        return str == null || str.length == 0
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str 要判断的字符串
     */
    fun isNotEmpty(str: CharSequence?): Boolean {
        return !isEmpty(str)
    }

    /**
     * 判断View显示的文字是否为空
     */
    fun viewTextIsEmpty(viewId: Int, isFilterSpace: Boolean): Boolean {
        return if (isFilterSpace) isEmpty(getViewTextWithTrim(viewId)) else isEmpty(getViewText(viewId))
    }

    /**
     * 判断View显示的文字是否为空
     */
    fun viewTextIsEmpty(view: TextView, isFilterSpace: Boolean): Boolean {
        return if (isFilterSpace) isEmpty(getViewTextWithTrim(view)) else isEmpty(getViewText(view))
    }

    /**
     * 判断View显示的文字是否不为空，包含Trim
     *
     * @param viewId 控件id
     */
    fun viewTextIsEmptyWithTrim(viewId: Int): Boolean {
        return viewTextIsEmpty(viewId, true)
    }

    /**
     * 判断View显示的文字是否不为空，包含Trim
     *
     * @param view 控件
     */
    fun viewTextIsEmptyWithTrim(view: TextView): Boolean {
        return viewTextIsEmpty(view, true)
    }

    /**
     * 判断View显示的文字是否不为空
     */
    fun viewTextIsNotEmpty(viewId: Int): Boolean {
        return isNotEmpty(getViewTextWithTrim(viewId))
    }

    /**
     * 判断View显示的文字是否不为空
     */
    fun viewTextIsNotEmpty(view: TextView): Boolean {
        return isNotEmpty(getViewTextWithTrim(view))
    }

    //-------------------------------- 设置文字 --------------------------------

    /**
     * 直接控件id设置文字
     *
     * @param text        文字
     * @param defaultText 如果传入文字的值为null，设置的默认文字
     * @param viewId      控件id，必须是TextView以及子类
     */
    @JvmOverloads
    fun setViewText(@IdRes viewId: Int, text: CharSequence, defaultText: CharSequence? = "") {
        val view = findView<View>(viewId)
        if (view is TextView) {
            setViewText(view, text, defaultText)
        }
    }

    /**
     * 为TextView设置文字，如果传入的Text为null，则设置为默认值
     */
    @JvmOverloads
    fun setViewText(view: TextView, text: CharSequence, defaultText: CharSequence? = "") {
        with(view) {
            view.text = text ?: defaultText
        }
    }

    //-------------------------------- 获取TextView及其子类的文字 --------------------------------

    /**
     * 使用View Id，获取文字，当获取为""时，可指定返回默认文字
     *
     * @param viewId      View id
     * @param defaultText 默认文字
     */
    fun getViewText(@IdRes viewId: Int, defaultText: CharSequence = ""): CharSequence {
        val view = findView<View>(viewId)
        return if (view is TextView) {
            getViewText(view, defaultText)
        } else defaultText
    }

    /**
     * 使用View，获取文字，当获取为""时，可指定返回默认文字
     *
     * @param textView    View id
     * @param defaultText 默认文字
     */
    fun getViewText(textView: TextView?, defaultText: CharSequence = ""): CharSequence {
        if (textView == null) {
            return defaultText
        }
        val text = textView.text
        return if (isEmpty(text.toString().trim { it <= ' ' })) {
            defaultText
        } else {
            text
        }
    }

    fun getViewTextWithTrim(@IdRes viewId: Int): CharSequence {
        return getViewText(viewId, "").toString().trim { it <= ' ' }
    }


    fun getViewTextWithTrim(view: TextView): CharSequence {
        return getViewText(view, "").toString().trim { it <= ' ' }
    }

    //-------------------------------- 单次设置监听器 --------------------------------

    /**
     * 查找并且设置点击监听器
     *
     * @param listener 监听器
     * @param id       view 的 id
     */
    fun findAndSetOnClick(@IdRes id: Int, listener: View.OnClickListener): View {
        findView<View>(id).setOnClickListener(listener)
        return findView(id)
    }

    /**
     * 查找并设置长按监听器
     *
     * @param id       View的id
     * @param listener 监听器
     */
    fun findAndSetOnLongClick(@IdRes id: Int, listener: View.OnLongClickListener): View {
        findView<View>(id).setOnLongClickListener(listener)
        return findView(id)
    }

    //-------------------------------- 批量设置监听器 --------------------------------

    /**
     * 批量id，设置同一个点击监听器
     *
     * @param listener 点击监听器
     * @param ids      view 的 id
     */
    fun setOnClickListener(listener: View.OnClickListener, @IdRes vararg ids: Int) {
        if (ids.size == 0) {
            return
        }
        for (id in ids) {
            if (id <= 0) {
                continue
            }
            findView<View>(id).setOnClickListener(listener)
        }
    }

    /**
     * 批量View，设置同一个点击监听器
     *
     * @param listener 点击监听器
     * @param views    多个View对象
     */
    fun setOnClickListener(listener: View.OnClickListener, vararg views: View) {
        for (view in views) {
            view.setOnClickListener(listener)
        }
    }

    /**
     * 批量View id，设置同一个长按监听器
     *
     * @param listener 长按监听器
     * @param ids      View 的id
     */
    fun setOnLongClickListener(listener: View.OnLongClickListener, @IdRes vararg ids: Int) {
        if (ids.size == 0) {
            return
        }
        for (id in ids) {
            findView<View>(id).setOnLongClickListener(listener)
        }
    }

    /**
     * 批量View，设置同一个长按监听器
     *
     * @param listener 长按监听器
     * @param views    多个View对象
     */
    fun setOnLongClickListener(listener: View.OnLongClickListener, vararg views: View) {
        if (views.size == 0) {
            return
        }
        for (view in views) {
            view.setOnLongClickListener(listener)
        }
    }

    //-------------------------------- 设置View的显示隐藏 --------------------------------

    /**
     * 以多个id的方式，批量设置View为显示
     *
     * @param ids 多个View的id
     */
    fun setVisible(@IdRes vararg ids: Int) {
        for (id in ids) {
            if (id <= 0) {
                continue
            }
            findView<View>(id).visibility = View.VISIBLE
        }
    }

    fun setInVisible(vararg ids: Int) {
        for (id in ids) {
            if (id <= 0) {
                continue
            }
            findView<View>(id).visibility = View.INVISIBLE
        }
    }

    /**
     * 以多个id的方式，批量设置View为隐藏
     *
     * @param ids 多个View的id
     */
    fun setGone(@IdRes vararg ids: Int) {
        for (id in ids) {
            findView<View>(id).visibility = View.GONE
        }
    }

    /**
     * 设置多个View显示
     */
    fun setVisible(vararg views: View) {
        for (view in views) {
            view.visibility = View.VISIBLE
        }
    }

    fun setInVisible(vararg views: View) {
        for (view in views) {
            view.visibility = View.INVISIBLE
        }
    }

    /**
     * 设置多个View隐藏
     */
    fun setGone(vararg views: View) {
        for (view in views) {
            view.visibility = View.GONE
        }
    }

    //-------------------------------- 图片加载 --------------------------------

    fun getImageLoader(): ImageLoader {
        return ImageLoader.getInstance()
    }

    /**
     * 加载网络图片
     */
    fun loadUrlImage(url: String, imageView: ImageView, @IdRes defaultImage: Int = -1) {
        getImageLoader().getLoader().loadUrlImage(getContext(), imageView, url, defaultImage)
    }

    /**
     * 加载网络圆形图片
     */
    fun loadUrlImageToRound(url: String, imageView: ImageView, @IdRes defaultImage: Int = -1) {
        getImageLoader().getLoader().loadUrlImageToRound(getContext(), imageView, url, defaultImage)
    }

    /**
     * 加载网络圆角图片
     */
    fun loadUrlImageToCorner(url: String, imageView: ImageView, @IdRes defaultImage: Int = -1) {
        getImageLoader().getLoader().loadUrlImageToCorner(getContext(), imageView, url, defaultImage)
    }

    /**
     * 加载内存卡图片
     */
    fun loadFileImage(filePath: String, imageView: ImageView, @IdRes defaultImage: Int = -1) {
        getImageLoader().getLoader().loadFileImage(getContext(), imageView, filePath, defaultImage)
    }

    /**
     * 加载图片bitmap
     */
    fun loadImageToBitmap(url: String, loadImageCallback: LoadImageCallback) {
        getImageLoader().getLoader().loadImageToBitmap(getContext(), url, loadImageCallback)
    }

    fun loadDrawableResId(@IdRes imageViewId: Int, @DrawableRes resId: Int) {
        val view = findView<View>(imageViewId)
        if (view is ImageView) {
            loadDrawableResId(view, resId)
        }
    }

    fun loadDrawableResId(imageView: ImageView, @DrawableRes resId: Int) {
        getImageLoader().getLoader().loadDrawableResId(getContext(), imageView, resId)
    }

    /**
     * 清除图片缓存
     */
    fun clearImageMemoryCache() {
        getImageLoader().getLoader().clearMemoryCache(getContext())
    }
}