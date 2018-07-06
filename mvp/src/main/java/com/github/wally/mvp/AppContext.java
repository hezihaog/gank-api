package com.github.wally.mvp;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.wally.mvp.http.cache.Repository;
import com.haoge.easyandroid.EasyAndroid;

/**
 * Package: com.github.wally.mvp
 * FileName: AppContext
 * Date: on 2018/6/16  下午7:25
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class AppContext extends Application {
    private Repository mRepository;
    private static AppContext mInstance;

    public AppContext() {
        mInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //打印日志
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        EasyAndroid.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mRepository = Repository.init(getFilesDir());
        MultiDex.install(this);
    }

    public static AppContext getInstance() {
        return mInstance;
    }

    public Repository getRepository() {
        return mRepository;
    }
}