package com.github.wally.mvp.adapter

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * Package: com.github.wally.mvp.adapter
 * FileName: ActivityLifecycleCallbacksAdapter
 * Date: on 2018/6/17  下午10:37
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
open class ActivityLifecycleCallbacksAdapter : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle) {

    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}
