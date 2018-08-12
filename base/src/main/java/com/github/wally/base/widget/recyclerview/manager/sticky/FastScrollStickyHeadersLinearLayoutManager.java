package com.github.wally.base.widget.recyclerview.manager.sticky;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.github.wally.base.widget.recyclerview.manager.delegate.FastScrollDelegate;
import com.github.wally.base.widget.recyclerview.manager.sticky.base.StickyHeaders;
import com.github.wally.base.widget.recyclerview.manager.sticky.base.StickyHeadersLinearLayoutManager;


/**
 * Package: oms.mmc.android.fast.framwork.widget.rv.manager
 * FileName: FastScrollStickyHeadersLinearLayoutManager
 * Date: on 2018/4/2  下午8:02
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class FastScrollStickyHeadersLinearLayoutManager<T extends RecyclerView.Adapter & StickyHeaders> extends StickyHeadersLinearLayoutManager<T> {

    public FastScrollStickyHeadersLinearLayoutManager(Context context) {
        super(context);
    }

    public FastScrollStickyHeadersLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        FastScrollDelegate delegate = new FastScrollDelegate();
        delegate.smoothScrollToPosition(recyclerView, this, position);
    }
}