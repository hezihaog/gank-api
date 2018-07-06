package com.github.wally.mvp.mvp.presenter;

import com.github.wally.mvp.base.BasePresenter;
import com.github.wally.mvp.bean.gank.GankMeiZiListBean;
import com.github.wally.mvp.http.IDataSource;
import com.github.wally.mvp.mvp.contract.GankMeiZiListContract;
import com.github.wally.mvp.mvp.model.GankMeiZiListModel;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Package: com.github.wally.mvp.mvp.presenter
 * FileName: GankMeiziListPresenter
 * Date: on 2018/6/16  上午10:41
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankMeiZiListPresenter extends BasePresenter<GankMeiZiListContract.View> implements GankMeiZiListContract.Presenter {
    private final GankMeiZiListModel mModel;

    public GankMeiZiListPresenter() {
        mModel = new GankMeiZiListModel();
    }

    @Override
    public void getMeiZiList(int page, int size, final boolean isRefresh) {
        Disposable disposable = mModel.requestMeiZiList(page, size)
                .subscribe(new Consumer<IDataSource<GankMeiZiListBean>>() {
                    @Override
                    public void accept(IDataSource<GankMeiZiListBean> gankMeiZiListBeanIDataSource) {
                        getMRootView().showMeiZiList(gankMeiZiListBeanIDataSource, isRefresh);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMRootView().showError(throwable);
                    }
                });
        addSubscription(disposable);
    }
}