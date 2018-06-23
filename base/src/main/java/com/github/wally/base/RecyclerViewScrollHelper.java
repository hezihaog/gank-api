package com.github.wally.base;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Package: com.github.wally.base
 * FileName: RecyclerViewScrollHelper
 * Date: on 2018/6/16  下午3:45
 * Auther: zihe
 * Descirbe: RecyclerView滚动帮助类
 * Email: hezihao@linghit.com
 */
public class RecyclerViewScrollHelper {
    /**
     * 列表控件
     */
    private RecyclerView mRecyclerView;
    /**
     * 第一次进入界面时也会回调滚动，所以当手动滚动再监听
     */
    private boolean isNotFirst = false;
    /**
     * 滚动监听
     */
    private ArrayList<ScrollListener> mListeners;

    private RecyclerViewScrollHelper() {
    }

    public static RecyclerViewScrollHelper create() {
        return new RecyclerViewScrollHelper();
    }

    public void attachRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        setup();
    }

    private void setup() {
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isNotFirst = true;
                if (mListeners != null && mListeners.size() > 0) {
                    //如果滚动到最后一行，RecyclerView.canScrollVertically(1)的值表示是否能向上滚动，false表示已经滚动到底部
                    if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                            !recyclerView.canScrollVertically(1)) {
                        for (ScrollListener listener : mListeners) {
                            listener.onScrolledToBottom();
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mListeners != null && isNotFirst) {
                    //RecyclerView.canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
                    if (!recyclerView.canScrollVertically(-1)) {
                        for (ScrollListener listener : mListeners) {
                            listener.onScrolledToTop();
                        }
                    }
                    //下滑
                    if (dy < 0) {
                        for (ScrollListener listener : mListeners) {
                            listener.onScrolledToDown();
                        }
                    }
                    //上滑
                    if (dy > 0) {
                        for (ScrollListener listener : mListeners) {
                            listener.onScrolledToUp();
                        }
                    }
                }
            }
        });
    }

    public void addScrollListener(ScrollListener listener) {
        if (mListeners == null) {
            mListeners = new ArrayList<>();
        }
        mListeners.add(listener);
    }

    /**
     * 滚动回调
     */
    public interface ScrollListener {
        /**
         * 当前正在向上滚动
         */
        void onScrolledToUp();

        /**
         * 当前正在向下滚动
         */
        void onScrolledToDown();

        /**
         * 滚动到顶部
         */
        void onScrolledToTop();

        /**
         * 滚动到底部
         */
        void onScrolledToBottom();
    }

    /**
     * 监听空实现
     */
    public static class SimpleScrollAdapter implements ScrollListener {

        @Override
        public void onScrolledToUp() {

        }

        @Override
        public void onScrolledToDown() {

        }

        @Override
        public void onScrolledToTop() {

        }

        @Override
        public void onScrolledToBottom() {

        }
    }
}