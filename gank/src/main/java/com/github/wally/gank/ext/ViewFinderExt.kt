package com.github.wally.gank.ext

import android.content.Context
import android.support.annotation.IdRes
import android.view.View
import android.widget.ImageView
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

fun ViewFinderHost.setViewText(@IdRes viewId: Int, text: CharSequence, defaultText: CharSequence? = "") {
    return this.getViewFinder().setViewText(viewId, text, defaultText)
}

fun ViewFinderHost.setViewText(view: TextView, text: CharSequence, defaultText: CharSequence? = "") {
    return this.getViewFinder().setViewText(view, text, defaultText)
}

fun ViewFinderHost.loadUrlImage(url: String, imageView: ImageView, @IdRes defaultImage: Int = -1) {
    return this.getViewFinder().loadUrlImage(url, imageView, defaultImage)
}