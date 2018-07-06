package com.github.wally.mvp.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Package: com.github.wally.mvp.dagger
 * FileName: MainModule
 * Date: on 2018/7/6  上午10:09
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
@Module
public class AppModule {
    private Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @Provides
    Context provideContext() {
        return this.mContext;
    }
}