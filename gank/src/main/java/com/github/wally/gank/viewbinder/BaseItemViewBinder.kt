package com.github.wally.gank.viewbinder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.wally.base.base.LayoutCallback
import com.github.wally.base.util.ViewFinder
import com.github.wally.base.util.ViewFinderHost
import me.drakeet.multitype.ItemViewBinder

/**
 * Package: com.github.wally.gank.viewbinder
 * FileName: BaseItemViewBinder
 * Date: on 2018/8/29  下午3:15
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
abstract class BaseItemViewBinder<T, VH : RecyclerView.ViewHolder> : ItemViewBinder<T, VH>(), LayoutCallback, ViewFinderHost {
    private lateinit var mViewFinder: ViewFinder

    override fun onLayoutBefore() {

    }

    override fun onLayoutAfter() {

    }

    override fun onFindView(rootView: View) {

    }

    override fun onBindViewContent() {

    }

    override fun getViewFinder(): ViewFinder {
        return mViewFinder
    }

    abstract fun onCreateViewHolder(root: View): VH

    override final fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH {
        val rootLayout = inflater.inflate(onLayoutId(), parent, false)
        mViewFinder = ViewFinder(parent.context, rootLayout)
        val viewHolder = onCreateViewHolder(rootLayout)
        onFindView(rootLayout)
        onBindViewContent()
        return viewHolder
    }
}