package com.github.wally.base.widget.recyclerview.manager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.github.wally.base.widget.recyclerview.manager.delegate.FastScrollLayoutManagerDelegate;


/**
 * Package: oms.mmc.android.fast.framwork.widget.rv.manager
 * FileName: FastScrollLinearLayoutManager
 * Date: on 2018/4/2  下午7:56
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class FastScrollLinearLayoutManager extends LinearLayoutManager {
    public FastScrollLinearLayoutManager(Context context) {
        super(context);
    }

    public FastScrollLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public FastScrollLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        FastScrollLayoutManagerDelegate delegate = new FastScrollLayoutManagerDelegate();
        delegate.smoothScrollToPosition(recyclerView, this, position);
    }
}
