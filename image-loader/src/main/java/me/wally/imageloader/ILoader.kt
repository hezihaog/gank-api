package me.wally.imageloader

import android.content.Context
import android.widget.ImageView

/**
 * Package: com.github.wally.base.image
 * FileName: ImageLoader
 * Date: on 2018/8/29  下午5:31
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
interface ILoader {
    /**
     * 加载网络图片
     */
    fun loadUrlImage(context: Context, imageView: ImageView, url: String, defaultImage: Int)

    /**
     * 加载网络圆形图片
     */
    fun loadUrlImageToRound(context: Context, imageView: ImageView, url: String, defaultImage: Int)

    /**
     * 加载网络圆角图片
     */
    fun loadUrlImageToCorner(context: Context, imageView: ImageView, url: String, defaultImage: Int)

    /**
     * 加载内存卡图片
     */
    fun loadFileImage(context: Context, imageView: ImageView, filePath: String, defaultImage: Int)

    /**
     * 加载资源图片
     */
    fun loadDrawableResId(context: Context, imageView: ImageView, resId: Int)

    /**
     * 加载图片bitmap
     */
    fun loadImageToBitmap(context: Context, url: String, loadImageCallback: LoadImageCallback)

    /**
     * 清除图片缓存
     */
    fun clearMemoryCache(context: Context)
}