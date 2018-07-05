package com.github.wally.mvp;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.wally.mvp.http.cache.Repository;

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
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mRepository = Repository.init(getFilesDir());
        //打印日志
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    public static AppContext getInstance() {
        return mInstance;
    }

    public Repository getRepository() {
        return mRepository;
    }
}