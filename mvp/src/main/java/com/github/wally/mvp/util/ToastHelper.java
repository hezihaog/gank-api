package com.github.wally.mvp.util;

import com.haoge.easyandroid.easy.EasyToast;

/**
 * Package: com.github.wally.mvp.util
 * FileName: ToastHelper
 * Date: on 2018/7/6  上午9:30
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class ToastHelper {
    public void show(int resId) {
        EasyToast.Companion.newBuilder().build().show(resId);
    }

    public void show(String msg) {
        EasyToast.Companion.newBuilder().build().show(msg, new Object());
    }
}