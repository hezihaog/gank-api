package com.github.wally.mvp.util;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Looper;

import com.github.wally.mvp.adapter.ActivityLifecycleCallbacksAdapter;

/**
 * Package: com.github.wally.mvp.util
 * FileName: WaitDialogHelper
 * Date: on 2018/6/17  下午10:33
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class WaitDialogHelper implements IWaitDialogHandler {
    private Activity mActivity;
    private ProgressDialog mDialog;
    private Handler mMainHandler;

    private WaitDialogHelper(Activity activity) {
        this.mActivity = activity;
        this.mMainHandler = new Handler(activity.getMainLooper());
        final Application application = mActivity.getApplication();
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacksAdapter() {
            @Override
            public void onActivityDestroyed(Activity activity) {
                super.onActivityDestroyed(activity);
                if (mMainHandler != null) {
                    mMainHandler.removeCallbacks(null);
                }
                mMainHandler = null;
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                mDialog = null;
                application.unregisterActivityLifecycleCallbacks(this);
            }
        });
    }

    public static WaitDialogHelper create(Activity activity) {
        if (activity == null) {
            throw new NullPointerException("activity is null");
        }
        return new WaitDialogHelper(activity);
    }

    @Override
    public void showWaitDialog() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (mActivity == null || mActivity.isFinishing()) {
                    return;
                }
                if (mDialog == null) {
                    mDialog = new ProgressDialog(mActivity);
                }
                if (!mDialog.isShowing()) {
                    mDialog.show();
                }
            }
        };
        handlePostMainThread(runnable);
    }

    @Override
    public void hideWaitDialog() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (mDialog == null) {
                    return;
                }
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        };
        handlePostMainThread(runnable);
    }

    private void handlePostMainThread(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            mMainHandler.post(runnable);
        }
    }
}