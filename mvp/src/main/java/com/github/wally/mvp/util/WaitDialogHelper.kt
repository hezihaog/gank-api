package com.github.wally.mvp.util

import android.app.Activity
import android.app.ProgressDialog
import android.os.Handler
import android.os.Looper
import com.github.wally.mvp.adapter.ActivityLifecycleCallbacksAdapter

/**
 * Package: com.github.wally.mvp.util
 * FileName: WaitDialogHelper
 * Date: on 2018/6/17  下午10:33
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class WaitDialogHelper private constructor(private val mActivity: Activity?) : IWaitDialogHandler {
    private var mDialog: ProgressDialog? = null
    private var mMainHandler: Handler? = null

    init {
        this.mMainHandler = Handler(mActivity?.getMainLooper())
        val application = mActivity!!.application
        application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacksAdapter() {
            override fun onActivityDestroyed(activity: Activity) {
                super.onActivityDestroyed(activity)
                if (mMainHandler != null) {
                    mMainHandler!!.removeCallbacks(null)
                }
                mMainHandler = null
                if (mDialog != null) {
                    mDialog!!.dismiss()
                }
                mDialog = null
                application.unregisterActivityLifecycleCallbacks(this)
            }
        })
    }

    override fun showWaitDialog() {
        val runnable = Runnable {
            if (mActivity == null || mActivity.isFinishing) {
                return@Runnable
            }
            if (mDialog == null) {
                mDialog = ProgressDialog(mActivity)
            }
            if (!mDialog!!.isShowing) {
                mDialog!!.show()
            }
        }
        handlePostMainThread(runnable)
    }

    override fun hideWaitDialog() {
        val runnable = Runnable {
            if (mDialog == null) {
                return@Runnable
            }
            if (mDialog!!.isShowing) {
                mDialog!!.dismiss()
            }
        }
        handlePostMainThread(runnable)
    }

    private fun handlePostMainThread(runnable: Runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run()
        } else {
            mMainHandler!!.post(runnable)
        }
    }

    companion object {
        fun create(activity: Activity?): WaitDialogHelper {
            if (activity == null) {
                throw NullPointerException("activity is null")
            }
            return WaitDialogHelper(activity)
        }
    }
}