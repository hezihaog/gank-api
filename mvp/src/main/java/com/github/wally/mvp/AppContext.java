package com.github.wally.mvp;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.wally.mvp.dagger.AppComponent;
import com.github.wally.mvp.dagger.AppModule;
import com.github.wally.mvp.dagger.DaggerAppComponent;
import com.github.wally.mvp.dagger.ToastModule;
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
    private AppComponent mComponent;

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
        initAppComponent();
    }

    private void initAppComponent() {
        mComponent = DaggerAppComponent
                .builder()
                .toastModule(new ToastModule())
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return this.mComponent;
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