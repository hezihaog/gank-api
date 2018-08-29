package com.github.wally.base.util

import android.app.Activity
import android.app.ProgressDialog
import android.os.Handler
import android.os.Looper
import com.github.wally.base.adapter.ActivityLifecycleCallbacksAdapter

/**
 * Package: com.github.wally.gank.util
 * FileName: WaitDialogHelper
 * Date: on 2018/6/17  下午10:33
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class WaitDialogHelper private constructor(private val mActivity: Activity) : IWaitDialogHandler {
    private var mDialog: ProgressDialog? = null
    private var mMainHandler: Handler = Handler(mActivity.getMainLooper())

    init {
        val application = mActivity.application
        application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacksAdapter() {
            override fun onActivityDestroyed(activity: Activity) {
                super.onActivityDestroyed(activity)
                mMainHandler.removeCallbacks(null)
                mDialog.let {
                    it!!.dismiss()
                    mDialog = null
                }
                application.unregisterActivityLifecycleCallbacks(this)
            }
        })
    }

    override fun showWaitDialog(msg: String?) {
        val runnable = Runnable {
            mActivity.run {
                if (mActivity.isFinishing) {
                    return@Runnable
                }
                if (mDialog == null) {
                    mDialog = ProgressDialog(mActivity)
                }
                mDialog.let {
                    if (!(it!!.isShowing)) {
                        it.setMessage(msg.toString())
                        it.show()
                    }
                }
            }
        }
        handlePostMainThread(runnable)
    }

    override fun hideWaitDialog() {
        val runnable = Runnable {
            mDialog.let {
                if (it!!.isShowing) {
                    it.dismiss()
                }
            }
        }
        handlePostMainThread(runnable)
    }

    private fun handlePostMainThread(runnable: Runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run()
        } else {
            mMainHandler.post(runnable)
        }
    }

    companion object {
        fun create(activity: Activity): WaitDialogHelper {
            return WaitDialogHelper(activity)
        }
    }
}