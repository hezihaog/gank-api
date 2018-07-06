package com.github.wally.mvp.dagger;

import com.github.wally.mvp.util.ToastHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Package: com.github.wally.mvp.dagger
 * FileName: ToastHelperModule
 * Date: on 2018/7/6  上午11:02
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
@Module
public class ToastModule {

    @Provides
    ToastHelper providesToastHelper() {
        return new ToastHelper();
    }
}