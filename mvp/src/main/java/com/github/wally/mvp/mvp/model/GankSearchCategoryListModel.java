package com.github.wally.mvp.mvp.model;

import com.github.wally.mvp.AppContext;
import com.github.wally.mvp.bean.gank.GankSearchBean;
import com.github.wally.mvp.enums.GankSearchCategory;
import com.github.wally.mvp.http.BaseDataSource;
import com.github.wally.mvp.http.IDataSource;
import com.github.wally.mvp.http.IoToMainScheduler;
import com.github.wally.mvp.mvp.contract.GankSearchCategoryListContract;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Package: com.github.wally.mvp.mvp.model
 * FileName: GankSearchCategoryListModel
 * Date: on 2018/6/23  下午11:57
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankSearchCategoryListModel implements GankSearchCategoryListContract.Model {
    @Override
    public Observable<IDataSource<GankSearchBean>> requestSearchCategoryList(int page, final int count, GankSearchCategory category) {
        return AppContext.getInstance()
                .getRepository()
                .getSearchCategoryList(category.getCategory(), count, page)
                .map(new Function<GankSearchBean, IDataSource<GankSearchBean>>() {
                    @Override
                    public IDataSource<GankSearchBean> apply(GankSearchBean gankSearchBean) throws Exception {
                        return new BaseDataSource<GankSearchBean>(gankSearchBean,
                                gankSearchBean.getResults().size() >= count);
                    }
                })
                .compose(new IoToMainScheduler<IDataSource<GankSearchBean>>());
    }
}
