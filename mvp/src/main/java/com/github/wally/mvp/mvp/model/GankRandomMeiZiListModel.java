package com.github.wally.mvp.mvp.model;

import com.github.wally.mvp.base.IBaseModel;
import com.github.wally.mvp.bean.gank.GankRandomListBean;
import com.github.wally.mvp.constants.GankRandomCategory;
import com.github.wally.mvp.http.BaseDataSource;
import com.github.wally.mvp.http.IDataSource;
import com.github.wally.mvp.http.IoToMainScheduler;
import com.github.wally.mvp.http.RetrofitManager;
import com.github.wally.mvp.mvp.contract.RandomMeiZiListContract;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Package: com.github.wally.mvp.mvp.model
 * FileName: GankRandomMeiZiListModel
 * Date: on 2018/6/18  上午10:51
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankRandomMeiZiListModel implements IBaseModel, RandomMeiZiListContract.Model {

    @Override
    public Observable<IDataSource<GankRandomListBean>> requestRandomMeiZiList(GankRandomCategory category, final int size) {
        return RetrofitManager.getInstance()
                .getGankApiService()
                .getRandomContent(category.getCategory(), size)
                .map(new Function<GankRandomListBean, IDataSource<GankRandomListBean>>() {
                    @Override
                    public IDataSource<GankRandomListBean> apply(GankRandomListBean gankRandomListBean) throws Exception {
                        //随机妹子图是没有下一页的，所以直接将hasNext设置为false
                        return new BaseDataSource<GankRandomListBean>(gankRandomListBean,
                                false);
                    }
                })
                .compose(new IoToMainScheduler<IDataSource<GankRandomListBean>>());
    }
}