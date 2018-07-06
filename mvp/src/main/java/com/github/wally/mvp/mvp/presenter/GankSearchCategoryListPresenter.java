package com.github.wally.mvp.mvp.presenter;

import com.github.wally.mvp.base.BasePresenter;
import com.github.wally.mvp.bean.gank.GankSearchBean;
import com.github.wally.mvp.enums.GankSearchCategory;
import com.github.wally.mvp.http.IDataSource;
import com.github.wally.mvp.mvp.contract.GankSearchCategoryListContract;
import com.github.wally.mvp.mvp.model.GankSearchCategoryListModel;

import io.reactivex.functions.Consumer;

/**
 * Package: com.github.wally.mvp.mvp.presenter
 * FileName: GankSearchCategoryListPresenter
 * Date: on 2018/6/24  上午12:09
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankSearchCategoryListPresenter extends BasePresenter<GankSearchCategoryListContract.View>
        implements GankSearchCategoryListContract.Presenter {
    private final GankSearchCategoryListContract.Model mModel;

    public GankSearchCategoryListPresenter() {
        mModel = new GankSearchCategoryListModel();
    }

    @Override
    public void getSearchCategoryList(int page, int count, GankSearchCategory category, final boolean isRefresh) {
        mModel.requestSearchCategoryList(page, count, category)
                .subscribe(new Consumer<IDataSource<GankSearchBean>>() {
                    @Override
                    public void accept(IDataSource<GankSearchBean> dataSource) throws Exception {
                        getMRootView().showSearchCategoryList(dataSource, isRefresh);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMRootView().showError(throwable);
                    }
                });
    }
}
