package com.github.wally.mvp.mvp.model;

import com.github.wally.mvp.AppContext;
import com.github.wally.mvp.bean.gank.GankMeiZiListBean;
import com.github.wally.mvp.http.BaseDataSource;
import com.github.wally.mvp.http.IDataSource;
import com.github.wally.mvp.http.IoToMainScheduler;
import com.github.wally.mvp.mvp.contract.GankMeiZiListContract;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Package: com.github.wally.mvp.mvp.model
 * FileName: GankMeiziModel
 * Date: on 2018/6/16  上午10:39
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankMeiZiListModel implements GankMeiZiListContract.Model {

    @Override
    public Observable<IDataSource<GankMeiZiListBean>> requestMeiZiList(int page, final int size) {
        //这里直接用RxCache，会自动判断内存缓存、磁盘缓存，没有才去调用网络接口
        return AppContext.getInstance().getRepository()
                .getMeizi(size, page)
                .map(new Function<GankMeiZiListBean, IDataSource<GankMeiZiListBean>>() {
                    @Override
                    public IDataSource<GankMeiZiListBean> apply(GankMeiZiListBean gankMeiZiListBean) throws Exception {
                        boolean hasNext = gankMeiZiListBean.getResults().size() >= size;
                        return new BaseDataSource<GankMeiZiListBean>(gankMeiZiListBean, hasNext);
                    }
                })
                .compose(new IoToMainScheduler<IDataSource<GankMeiZiListBean>>());
    }
}