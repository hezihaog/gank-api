package com.github.wally.base.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

public class ImageDisplayUtil {
    private ImageDisplayUtil() {
    }

    /**
     * 加载图片Url拿到Bitmap对象
     */
    public static void loadImage(Context context, String imageUrl, final int reqWidth, final int reqHeight, final LoadCallback callback) {
        Glide.with(context.getApplicationContext()).load(imageUrl).asBitmap().into(new SimpleTarget<Bitmap>(reqWidth, reqHeight) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (callback != null) {
                    callback.onLoad(resource);
                }
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                if (callback != null) {
                    callback.onLoadFailed(e);
                }
            }
        });
    }

    /**
     * 加载回调
     */
    public interface LoadCallback {
        void onLoad(Bitmap bitmap);

        void onLoadFailed(Exception e);
    }

    /**
     * 展示图片，展示时回调
     */
    public static void display(Context context, String imageUrl, ImageView imageView, final DisplayCallback callback) {
        Glide.with(context).load(imageUrl).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                if (callback != null) {
                    callback.onException();
                }
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                if (callback != null) {
                    callback.onReady();
                }
                return false;
            }
        }).crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate().into(imageView);
    }

    public interface DisplayCallback {
        void onException();

        void onReady();
    }

    public static class DisplayCallbackAdapter implements DisplayCallback {

        @Override
        public void onException() {

        }

        @Override
        public void onReady() {

        }
    }
}