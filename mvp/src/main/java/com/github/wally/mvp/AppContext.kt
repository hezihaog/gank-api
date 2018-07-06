package com.github.wally.mvp

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex

import com.alibaba.android.arouter.launcher.ARouter
import com.github.wally.mvp.http.cache.Repository
import com.haoge.easyandroid.EasyAndroid

/**
 * Package: com.github.wally.mvp
 * FileName: AppContext
 * Date: on 2018/6/16  下午7:25
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class AppContext : Application() {
    var repository: Repository? = null
        private set

    init {
        instance = this
    }

    companion object {
        var instance: AppContext? = null
    }

    override fun onCreate() {
        super.onCreate()
        //打印日志
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
        EasyAndroid.init(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        this.repository = Repository.init(filesDir)
        MultiDex.install(this)
    }
}