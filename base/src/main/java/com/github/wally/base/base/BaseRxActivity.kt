package com.github.wally.base.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.CheckResult
import android.view.LayoutInflater
import android.view.View
import com.github.wally.base.util.ViewFinder
import com.github.wally.base.util.ViewFinderHost

import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import me.yokeyword.fragmentation.SupportActivity

/**
 * Package: com.github.wally.gank.base
 * FileName: BaseRxActivity
 * Date: on 2018/6/16  上午12:08
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
abstract class BaseRxActivity : SupportActivity(), LifecycleProvider<ActivityEvent>, LayoutCallback, ViewFinderHost {
    private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()
    private lateinit var mViewFinder: ViewFinder

    override fun onLayoutBefore() {

    }

    override fun onLayoutAfter() {

    }

    override fun onFindView(rootView: View) {

    }

    override fun onBindViewContent() {

    }

    override fun getViewFinder(): ViewFinder {
        return mViewFinder
    }

    @CheckResult
    override fun lifecycle(): Observable<ActivityEvent> {
        return lifecycleSubject.hide()
    }

    @CheckResult
    override fun <T> bindUntilEvent(event: ActivityEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    @CheckResult
    override fun <T> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(ActivityEvent.CREATE)
        onLayoutBefore()
        val rootLayout = LayoutInflater.from(applicationContext).inflate(onLayoutId(), null)
        setContentView(rootLayout)
        mViewFinder = ViewFinder(this, rootLayout)
        onFindView(rootLayout)
        onLayoutAfter()
        onBindViewContent()
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(ActivityEvent.START)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(ActivityEvent.RESUME)
    }

    @CallSuper
    override fun onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE)
        super.onPause()
    }

    @CallSuper
    override fun onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP)
        super.onStop()
    }

    @CallSuper
    override fun onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY)
        super.onDestroy()
    }
}