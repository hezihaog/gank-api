package com.github.wally.base.image

import android.graphics.Bitmap

/**
 * Package: com.github.wally.base.image
 * FileName: LoadImageCallback
 * Date: on 2018/8/29  下午5:31
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
interface LoadImageCallback {
    fun onSuccess(bitmap: Bitmap)

    fun onFail()
}