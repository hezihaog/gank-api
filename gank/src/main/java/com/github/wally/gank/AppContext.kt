package com.github.wally.gank

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex

import com.alibaba.android.arouter.launcher.ARouter
import com.github.wally.gank.http.cache.Repository
import com.haoge.easyandroid.EasyAndroid

/**
 * Package: com.github.wally.gank
 * FileName: AppContext
 * Date: on 2018/6/16  下午7:25
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class AppContext : Application() {
    lateinit var repository: Repository
        private set

    companion object {
        lateinit var instance: AppContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
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