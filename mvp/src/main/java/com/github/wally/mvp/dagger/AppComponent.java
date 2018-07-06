package com.github.wally.mvp.dagger;

import android.content.Context;

import com.github.wally.mvp.util.ToastHelper;

import dagger.Component;

/**
 * Package: com.github.wally.mvp.dagger
 * FileName: MainComponent
 * Date: on 2018/7/6  上午10:10
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
@Component(modules = {AppModule.class, ToastModule.class})
public interface AppComponent {
    Context getContext();

    ToastHelper getToastHelper();
}