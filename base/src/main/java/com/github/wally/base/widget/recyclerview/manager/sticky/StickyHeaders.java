package com.github.wally.base.widget.recyclerview.manager.sticky;

import android.view.View;

/**
 * Created by jay on 2017/12/4 上午10:52
 * <p>
 * Adds sticky headers capabilities to the {@link android.support.v7.widget.RecyclerView.Adapter}. Should return {@code true} for all
 * positions that represent sticky headers.
 *
 * @link https://github.com/Doist/RecyclerViewExtensions/blob/master/StickyHeaders
 */
public interface StickyHeaders {
    boolean isStickyHeader(int position);

    boolean isStickyHeaderViewType(int viewType);

    interface ViewSetup {
        /**
         * Adjusts any necessary properties of the {@code holder} that is being used as a sticky header.
         * <p>
         * {@link #teardownStickyHeaderView(View)} will be called sometime after this method
         * and before any other calls to this method go through.
         */
        void setupStickyHeaderView(View stickyHeader);

        /**
         * Reverts any properties changed in {@link #setupStickyHeaderView(View)}.
         * <p>
         * Called after {@link #setupStickyHeaderView(View)}.
         */
        void teardownStickyHeaderView(View stickyHeader);
    }
}
