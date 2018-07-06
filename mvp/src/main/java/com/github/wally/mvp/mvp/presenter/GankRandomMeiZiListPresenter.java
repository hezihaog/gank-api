package com.github.wally.mvp.mvp.presenter;

import com.github.wally.mvp.base.BasePresenter;
import com.github.wally.mvp.bean.gank.GankRandomListBean;
import com.github.wally.mvp.enums.GankRandomCategory;
import com.github.wally.mvp.http.IDataSource;
import com.github.wally.mvp.mvp.contract.RandomMeiZiListContract;
import com.github.wally.mvp.mvp.model.GankRandomMeiZiListModel;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Package: com.github.wally.mvp.mvp.presenter
 * FileName: GankRandomMeiZiListPresenter
 * Date: on 2018/6/18  上午10:59
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankRandomMeiZiListPresenter extends BasePresenter<RandomMeiZiListContract.View> implements RandomMeiZiListContract.Presenter {
    private final RandomMeiZiListContract.Model mModel;

    public GankRandomMeiZiListPresenter() {
        mModel = new GankRandomMeiZiListModel();
    }

    @Override
    public void getRandomMeiZiList(GankRandomCategory category, int size, final boolean isRefresh) {
        Disposable disposable = mModel.requestRandomMeiZiList(category, size)
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMRootView().showError(throwable);
                    }
                })
                .subscribe(new Consumer<IDataSource<GankRandomListBean>>() {
                    @Override
                    public void accept(IDataSource<GankRandomListBean> gankRandomListBeanDataSource) throws Exception {
                        getMRootView().showRandomMeiZiList(gankRandomListBeanDataSource, isRefresh);
                    }
                });
        addSubscription(disposable);
    }
}
