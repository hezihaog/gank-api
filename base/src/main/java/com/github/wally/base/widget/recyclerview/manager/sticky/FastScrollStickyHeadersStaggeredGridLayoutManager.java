package com.github.wally.base.widget.recyclerview.manager.sticky;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.github.wally.base.widget.recyclerview.manager.delegate.FastScrollLayoutManagerDelegate;


/**
 * Package: oms.mmc.android.fast.framwork.widget.rv.manager.sticky
 * FileName: FastScrollStickyHeadersStaggeredGridLayoutManager
 * Date: on 2018/4/2  下午8:07
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class FastScrollStickyHeadersStaggeredGridLayoutManager<T extends RecyclerView.Adapter & StickyHeaders> extends StickyHeadersStaggeredGridLayoutManager<T> {
    public FastScrollStickyHeadersStaggeredGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public FastScrollStickyHeadersStaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        FastScrollLayoutManagerDelegate delegate = new FastScrollLayoutManagerDelegate();
        delegate.smoothScrollToPosition(recyclerView, this, position);
    }
}
