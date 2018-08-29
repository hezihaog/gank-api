package com.github.wally.gank.ext

import android.content.Context
import android.support.annotation.IdRes
import android.view.View
import android.widget.TextView
import com.github.wally.base.util.ViewFinderHost

/**
 * Package: com.github.wally.gank.ext
 * FileName: ViewFinderExt
 * Date: on 2018/8/29  下午2:19
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
fun ViewFinderHost.getContext(): Context {
    return this.getViewFinder().getContext()
}

fun <T : View> ViewFinderHost.findView(resId: Int): T {
    return this.getViewFinder().findView(resId)
}

fun ViewFinderHost.getRootView(): View {
    return this.getViewFinder().getRootView()
}

fun ViewFinderHost.isEmpty(str: CharSequence): Boolean {
    return this.getViewFinder().isEmpty(str)
}

fun ViewFinderHost.isNotEmpty(str: CharSequence): Boolean {
    return this.getViewFinder().isNotEmpty(str)
}

fun ViewFinderHost.setViewText(text: CharSequence, defaultText: CharSequence = "", @IdRes viewId: Int) {
    return this.getViewFinder().setViewText(text, defaultText, viewId)
}

fun ViewFinderHost.setViewText(text: CharSequence?, defaultText: CharSequence = "", view: TextView) {
    return this.getViewFinder().setViewText(text, defaultText, view)
}