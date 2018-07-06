package com.github.wally.mvp.mvp.presenter;

import com.github.wally.mvp.base.BasePresenter;
import com.github.wally.mvp.enums.GankSearchCategory;
import com.github.wally.mvp.mvp.contract.GankSearchContract;
import com.github.wally.mvp.mvp.model.GankSearchModel;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Package: com.github.wally.mvp.mvp.presenter
 * FileName: GankSearchPresenter
 * Date: on 2018/6/23  下午11:05
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankSearchPresenter extends BasePresenter<GankSearchContract.View>
        implements GankSearchContract.Presenter {
    private final GankSearchContract.Model mModel;

    public GankSearchPresenter() {
        mModel = new GankSearchModel();
    }

    @Override
    public void requestSearchCategoryList() {
        Disposable disposable = mModel.getSearchCategoryList()
                .subscribe(new Consumer<List<GankSearchCategory>>() {
                    @Override
                    public void accept(List<GankSearchCategory> categoryList) throws Exception {
                        getMRootView().allocSearchCategory(categoryList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMRootView().showError(throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
        addSubscription(disposable);
    }
}