package com.github.wally.base.adapter;

import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;

/**
 * 列表滚动监听，暂时和恢复图片请求
 */
public class ListScrollImageRequestListener extends RecyclerView.OnScrollListener {
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        //在快速惯性滚动时，暂定请求
        if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
            Glide.with(recyclerView.getContext()).pauseRequests();
        } else {
            //停下来，或者拽拖中时恢复
            Glide.with(recyclerView.getContext()).resumeRequestsRecursive();
        }
    }
}