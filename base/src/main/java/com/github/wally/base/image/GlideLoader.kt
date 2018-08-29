package com.github.wally.base.image

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import java.io.File

/**
 * Package: com.github.wally.base.image
 * FileName: GlideImageLoader
 * Date: on 2018/8/29  下午5:33
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class GlideLoader : ILoader {

    override fun loadUrlImage(context: Context, imageView: ImageView, url: String, defaultImage: Int) {
        Glide.with(context)
                .load(url)
                .placeholder(defaultImage)
                .error(defaultImage)
                .crossFade()
                //                .thumbnail(0.7f)
                .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
    }

    override fun loadUrlImageToRound(context: Context, imageView: ImageView, url: String, defaultImage: Int) {
        Glide.with(context)
                .load(url)
                .placeholder(defaultImage)
                .error(defaultImage)
                //.centerCrop()//网友反馈，设置此属性可能不起作用,在有些设备上可能会不能显示为圆形。
                //                .thumbnail(0.7f)
                .transform(GlideCircleTransform(context))
                .crossFade()
                .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
    }

    override fun loadUrlImageToCorner(context: Context, imageView: ImageView, url: String, defaultImage: Int) {
        Glide.with(context)
                .load(url)
                .placeholder(defaultImage)
                .error(defaultImage)
                //.centerCrop()
                //                .thumbnail(0.7f)
                .transform(GlideRoundTransform(context))
                .crossFade()
                .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
    }

    override fun loadFileImage(context: Context, imageView: ImageView, filePath: String, defaultImage: Int) {
        Glide.with(context)
                .load(File(filePath))
                .placeholder(defaultImage)
                .error(defaultImage)
                .crossFade()
                .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
    }

    override fun loadDrawableResId(context: Context, imageView: ImageView, resId: Int) {
        Glide.with(context).load(resId).into(imageView)
    }

    override fun loadImageToBitmap(context: Context, url: String, loadImageCallback: LoadImageCallback) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, glideAnimation: GlideAnimation<in Bitmap>) {
                        loadImageCallback?.onSuccess(resource)
                    }

                    override fun onLoadFailed(e: Exception?, errorDrawable: Drawable?) {
                        super.onLoadFailed(e, errorDrawable)
                        loadImageCallback?.onFail()
                    }
                })
    }

    override fun clearMemoryCache(context: Context) {
        Glide.get(context).clearDiskCache()
    }

    //基本功能：Glide显示为圆形图片
    inner class GlideCircleTransform(context: Context) : BitmapTransformation(context) {

        override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap? {
            return circleCrop(pool, toTransform)
        }

        private fun circleCrop(pool: BitmapPool, source: Bitmap?): Bitmap? {
            if (source == null) {
                return null
            }

            val size = Math.min(source.width, source.height)
            val x = (source.width - size) / 2
            val y = (source.height - size) / 2

            val squared = Bitmap.createBitmap(source, x, y, size, size)

            var result: Bitmap? = pool.get(size, size, Bitmap.Config.ARGB_8888)
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
            }

            val canvas = Canvas(result!!)
            val paint = Paint()
            paint.shader = BitmapShader(squared, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            paint.isAntiAlias = true
            val r = size / 2f
            canvas.drawCircle(r, r, r, paint)
            return result
        }

        override fun getId(): String {
            return javaClass.name
        }
    }

    //基本功能：Glide显示为圆角图片
    inner class GlideRoundTransform @JvmOverloads constructor(context: Context, dp: Int = 4) : BitmapTransformation(context) {
        private var radius = 0f

        init {
            this.radius = Resources.getSystem().displayMetrics.density * dp
        }

        override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap? {
            return roundCrop(pool, toTransform)
        }

        private fun roundCrop(pool: BitmapPool, source: Bitmap?): Bitmap? {
            if (source == null) {
                return null
            }

            var result: Bitmap? = pool.get(source.width, source.height, Bitmap.Config.ARGB_8888)
            if (result == null) {
                result = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
            }

            val canvas = Canvas(result!!)
            val paint = Paint()
            paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            paint.isAntiAlias = true
            val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
            canvas.drawRoundRect(rectF, radius, radius, paint)
            return result
        }

        override fun getId(): String {
            return javaClass.name + Math.round(radius)
        }
    }
}