package com.github.wally.base.widget.recyclerview.manager.delegate;

import android.support.v7.widget.RecyclerView;

/**
 * Package: oms.mmc.android.fast.framwork.widget.rv.manager
 * FileName: IFastScrollLayoutManagerDelegate
 * Date: on 2018/4/2  下午4:11
 * Auther: zihe
 * Descirbe:快速滚动代理
 * Email: hezihao@linghit.com
 */
public interface IFastScrollDelegate {
    /**
     * 快速滚动
     *
     * @param recyclerView 滚动控件
     */
    void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.SmoothScroller.ScrollVectorProvider provider, int position);
}