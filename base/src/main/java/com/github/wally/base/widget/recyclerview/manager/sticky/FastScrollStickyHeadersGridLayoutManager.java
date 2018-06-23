package com.github.wally.base.widget.recyclerview.manager.sticky;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.github.wally.base.widget.recyclerview.manager.delegate.FastScrollLayoutManagerDelegate;


/**
 * Package: oms.mmc.android.fast.framwork.widget.rv.manager.sticky
 * FileName: FastScrollStickyHeadersGridLayoutManager
 * Date: on 2018/4/2  下午8:06
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class FastScrollStickyHeadersGridLayoutManager<T extends RecyclerView.Adapter & StickyHeaders> extends StickyHeadersGridLayoutManager<T> {
    public FastScrollStickyHeadersGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public FastScrollStickyHeadersGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public FastScrollStickyHeadersGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        FastScrollLayoutManagerDelegate delegate = new FastScrollLayoutManagerDelegate();
        delegate.smoothScrollToPosition(recyclerView, this, position);
    }
}
