package com.github.wally.mvp.mvp.presenter;

import android.os.Bundle;

import com.github.wally.mvp.base.BasePresenter;
import com.github.wally.mvp.bean.gank.DisplayMeiZiImageBean;
import com.github.wally.mvp.mvp.contract.GankMeiZiDetailContract;
import com.github.wally.mvp.mvp.model.GankMeiZiDetailModel;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Package: com.github.wally.mvp.mvp.presenter
 * FileName: GankMeiZiDetailPresenter
 * Date: on 2018/6/16  下午6:45
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankMeiZiDetailPresenter extends BasePresenter<GankMeiZiDetailContract.View> implements GankMeiZiDetailContract.Presenter {
    private final GankMeiZiDetailContract.Model mModel;

    public GankMeiZiDetailPresenter() {
        mModel = new GankMeiZiDetailModel();
    }

    @Override
    public void getMeiZiDetail(Bundle bundle) {
        mModel.requestMeiZiDetail(bundle)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addSubscription(disposable);
                    }
                })
                .subscribe(new Consumer<DisplayMeiZiImageBean>() {
                    @Override
                    public void accept(DisplayMeiZiImageBean bean) throws Exception {
                        mRootView.showMeiZiDetail(bean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mRootView.showError(throwable);
                    }
                });
    }
}
