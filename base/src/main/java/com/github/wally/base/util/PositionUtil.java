package com.github.wally.base.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Package: com.github.wally.base.util
 * FileName: PositionUtil
 * Date: on 2018/6/23  上午10:23
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class PositionUtil {
    private PositionUtil() {
    }

    /**
     * 获取RecyclerView当前位置position
     */
    public static int getCurrentPosition(RecyclerView recyclerView) {
        View view = recyclerView.getLayoutManager().getChildAt(0);
        if (view != null) {
            return recyclerView.getLayoutManager().getPosition(view);
        } else {
            return -1;
        }
    }
}