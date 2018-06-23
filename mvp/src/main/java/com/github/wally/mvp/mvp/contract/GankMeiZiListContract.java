package com.github.wally.mvp.mvp.contract;

import com.github.wally.mvp.base.IBaseModel;
import com.github.wally.mvp.base.IBaseView;
import com.github.wally.mvp.base.IPresenter;
import com.github.wally.mvp.bean.gank.GankMeiZiListBean;
import com.github.wally.mvp.http.IDataSource;

import io.reactivex.Observable;

/**
 * Package: com.github.wally.mvp.mvp.contract
 * FileName: GankMeiziContract
 * Date: on 2018/6/16  上午10:35
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public interface GankMeiZiListContract {
    interface View extends IBaseView {
        void showMeiZiList(IDataSource<GankMeiZiListBean> meiziGankBean, boolean isRefresh);
    }

    interface Presenter extends IPresenter<View> {
        void getMeiZiList(int page, int size, boolean isRefresh);
    }

    interface Model extends IBaseModel {
        Observable<IDataSource<GankMeiZiListBean>> requestMeiZiList(int page, int size);
    }
}