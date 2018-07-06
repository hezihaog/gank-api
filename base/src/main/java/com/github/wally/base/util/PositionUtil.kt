package com.github.wally.base.util

import android.support.v7.widget.RecyclerView

/**
 * Package: com.github.wally.base.util
 * FileName: PositionUtil
 * Date: on 2018/6/23  上午10:23
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
object PositionUtil {
    /**
     * 获取RecyclerView当前位置position
     */
    fun getCurrentPosition(recyclerView: RecyclerView): Int {
        val view = recyclerView.layoutManager.getChildAt(0)
        return if (view != null) {
            recyclerView.layoutManager.getPosition(view)
        } else {
            -1
        }
    }
}