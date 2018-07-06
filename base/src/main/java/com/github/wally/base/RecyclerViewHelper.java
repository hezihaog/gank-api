package com.github.wally.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.github.wally.base.adapter.ListScrollImageRequestListener;

import java.util.ArrayList;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Package: com.github.wally.base
 * FileName: RecyclerViewHelper
 * Date: on 2018/6/17  下午10:59
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class RecyclerViewHelper {
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private MultiTypeAdapter mAdapter;
    private OnLoadListener mOnLoadListener;
    /**
     * 第一页开始的页码，默认为1，如不是从1开始，可创建时传入
     */
    private int mStartPageNum;
    /**
     * 列表滚动监听
     */
    private RecyclerViewScrollHelper mRecyclerViewScrollHelper;
    /**
     * 是否是第一次刷新
     */
    private boolean isFirstRefresh = true;
    /**
     * 是否是第一次加载更多
     */
    private boolean isFirstLoadMore = true;
    /**
     * 当前页码
     */
    private int mPage = mStartPageNum;
    /**
     * 列表数据
     */
    private ArrayList listDatas;
    /**
     * 是否有下一页
     */
    private boolean mHasNext = true;

    private RecyclerViewHelper(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView,
                               MultiTypeAdapter adapter, OnLoadListener onLoadListener, int startPageNum) {
        this.mRefreshLayout = refreshLayout;
        this.mRecyclerView = recyclerView;
        this.mAdapter = adapter;
        this.mOnLoadListener = onLoadListener;
        this.mStartPageNum = startPageNum;
        setup();
    }

    public static RecyclerViewHelper create(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView
            , MultiTypeAdapter adapter, OnLoadListener onLoadListener) {
        return create(refreshLayout, recyclerView, adapter, onLoadListener, 1);
    }

    public static RecyclerViewHelper create(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView
            , MultiTypeAdapter adapter, OnLoadListener onLoadListener, int startPageNum) {
        return new RecyclerViewHelper(refreshLayout, recyclerView, adapter, onLoadListener, startPageNum);
    }

    private void setup() {
        this.mRecyclerViewScrollHelper = RecyclerViewScrollHelper.create();
        this.mRecyclerViewScrollHelper.attachRecyclerView(mRecyclerView);
        //下拉刷新
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        //加载更多
        addScrollListener(new RecyclerViewScrollHelper.SimpleScrollAdapter() {

            @Override
            public void onScrolledToTop() {
                mRefreshLayout.setEnabled(false);
            }

            @Override
            public void onScrolledToDown() {
                super.onScrolledToDown();
                if (!mRefreshLayout.isEnabled()) {
                    mRefreshLayout.setEnabled(true);
                }
            }

            @Override
            public void onScrolledToBottom() {
                if (mHasNext) {
                    loadMore();
                }
            }
        });
        //增加列表惯性滚动时，暂停请求，停止和拽托时恢复请求
        mRecyclerView.addOnScrollListener(new ListScrollImageRequestListener());
    }

    /**
     * 刷新
     */
    private void refresh() {
        //下拉刷新每次都是第一页
        this.mPage = 1;
        //将是否有下一页的标志位重置
        this.mHasNext = true;
        this.mRefreshLayout.setRefreshing(true);
        if (mOnLoadListener != null) {
            mOnLoadListener.onSwipeRefresh(mPage, isFirstRefresh);
        }
        if (isFirstRefresh) {
            isFirstRefresh = false;
        }
    }

    public void startRefresh() {
        refresh();
    }

    public void startRefreshWithLoading() {
        mRefreshLayout.setRefreshing(true);
        startRefresh();
    }

    /**
     * 更新数据集，在更新数据时调用
     */
    public void updateDataSource(boolean isRefresh, ArrayList newDataSource, boolean hasNext) {
        if (listDatas == null) {
            listDatas = new ArrayList();
        }
        if (isRefresh) {
            listDatas.clear();
        }
        listDatas.addAll(newDataSource);
        mAdapter.setItems(listDatas);
        if (isRefresh) {
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.notifyItemRangeInserted(mAdapter.getItemCount() - 1, listDatas.size());
        }
        finishLoading();
        setHasNext(hasNext);
    }

    /**
     * 结束刷新时调用
     */
    public void finishLoading() {
        this.mRefreshLayout.setRefreshing(false);
    }

    /**
     * 加载更多
     */
    private void loadMore() {
        if (!mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(true);
            mPage++;
            if (mOnLoadListener != null) {
                mOnLoadListener.onLoadMore(mPage, isFirstLoadMore);
            }
            if (isFirstLoadMore) {
                isFirstLoadMore = false;
            }
        }
    }

    public void addScrollListener(RecyclerViewScrollHelper.ScrollListener scrollListener) {
        this.mRecyclerViewScrollHelper.addScrollListener(scrollListener);
    }

    public void moveToTop() {
        this.mRecyclerView.smoothScrollToPosition(0);
    }

    /**
     * 提供给外部的加载回调
     */
    public interface OnLoadListener {
        /**
         * 下拉刷新回调
         *
         * @param page    当前页码
         * @param isFirst 是否第一次刷新
         */
        void onSwipeRefresh(int page, boolean isFirst);

        /**
         * 加载更多
         *
         * @param page    当前页码
         * @param isFirst 是否第一次加载更多
         */
        void onLoadMore(int page, boolean isFirst);
    }

    /**
     * 设置是否还有下一页
     */
    public void setHasNext(boolean hasNext) {
        this.mHasNext = hasNext;
    }
}