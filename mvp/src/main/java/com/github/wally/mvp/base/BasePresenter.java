package com.github.wally.mvp.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Package: com.github.wally.mvp.base
 * FileName: BasePresenter
 * Date: on 2018/6/14  下午11:55
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public abstract class BasePresenter<T extends IBaseView> implements IPresenter<T> {
    protected T mRootView;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    /**
     * 绑定视图
     */
    @Override
    public void attachView(T view) {
        this.mRootView = view;
    }

    /**
     * 解绑视图
     */
    @Override
    public void detachView() {
        this.mRootView = null;
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }

    public void addSubscription(Disposable disposable) {
        compositeDisposable.add(disposable);
    }
}