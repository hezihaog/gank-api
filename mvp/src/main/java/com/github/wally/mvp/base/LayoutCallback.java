package com.github.wally.mvp.base;

import android.view.View;

/**
 * Package: com.github.wally.mvp.base
 * FileName: LayoutCallback
 * Date: on 2018/6/17  上午8:53
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public interface LayoutCallback {
    void onLayoutBefore();

    int onLayoutId();

    void onLayoutAfter();

    void onFindView(View rootView);

    void onBindViewContent();
}