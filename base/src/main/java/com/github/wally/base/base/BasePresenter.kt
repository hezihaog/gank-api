package com.github.wally.base.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Package: com.github.wally.gank.base
 * FileName: BasePresenter
 * Date: on 2018/6/14  下午11:55
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
abstract class BasePresenter<T : IView> : IPresenter<T> {
    protected var mRootView: T? = null
    private val compositeDisposable = CompositeDisposable()

    /**
     * 绑定视图
     */
    override fun attachView(view: T) {
        this.mRootView = view
    }

    /**
     * 解绑视图
     */
    override fun detachView() {
        this.mRootView = null
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}