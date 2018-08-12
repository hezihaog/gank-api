package com.github.wally.base.widget.recyclerview.manager.delegate;

import android.graphics.PointF;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

/**
 * Package: oms.mmc.android.fast.framwork.widget.rv.manager
 * FileName: FastScrollLayoutManagerDelegate
 * Date: on 2018/4/2  下午4:09
 * Auther: zihe
 * Descirbe:快速滚动代理
 * Email: hezihao@linghit.com
 */
public class FastScrollDelegate implements IFastScrollDelegate {

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, final RecyclerView.SmoothScroller.ScrollVectorProvider provider, int position) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {

            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return provider.computeScrollVectorForPosition(targetPosition);
            }

            //该方法控制速度。
            //if returned value is 2 ms, it means scrolling 1000 pixels with LinearInterpolation should take 2 seconds.
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                /*
                     控制单位速度,  毫秒/像素, 滑动1像素需要多少毫秒.
                     默认为 (25F/densityDpi) 毫秒/像素
                     mdpi上, 1英寸有160个像素点, 25/160,
                     xxhdpi,1英寸有480个像素点, 25/480,
                  */

                //return 10F / displayMetrics.densityDpi;//可以减少时间，默认25F
                return super.calculateSpeedPerPixel(displayMetrics);
            }

            //该方法计算滑动所需时间。在此处间接控制速度。
            //Calculates the time it should take to scroll the given distance (in pixels)
            @Override
            protected int calculateTimeForScrolling(int dx) {
               /*
                   控制距离, 然后根据上面那个方(calculateSpeedPerPixel())提供的速度算出时间,

                   默认一次 滚动 TARGET_SEEK_SCROLL_DISTANCE_PX = 10000个像素,

                   在此处可以减少该值来达到减少滚动时间的目的.
                */

                //间接计算时提高速度，也可以直接在calculateSpeedPerPixel提高
                if (dx > 3000) {
                    dx = 3000;
                }
                int time = super.calculateTimeForScrolling(dx);
                return time;
            }
        };

        linearSmoothScroller.setTargetPosition(position);
        recyclerView.getLayoutManager().startSmoothScroll(linearSmoothScroller);
    }
}
