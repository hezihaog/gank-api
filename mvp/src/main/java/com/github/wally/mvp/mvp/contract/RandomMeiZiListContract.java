package com.github.wally.mvp.mvp.contract;

import com.github.wally.mvp.base.IBaseModel;
import com.github.wally.mvp.base.IBaseView;
import com.github.wally.mvp.base.IPresenter;
import com.github.wally.mvp.bean.gank.GankRandomListBean;
import com.github.wally.mvp.constants.GankRandomCategory;
import com.github.wally.mvp.http.IDataSource;

import io.reactivex.Observable;

/**
 * Package: com.github.wally.mvp.mvp.contract
 * FileName: RandomMeiZiContract
 * Date: on 2018/6/18  上午10:18
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public interface RandomMeiZiListContract {
    interface View extends IBaseView {
        void showRandomMeiZiList(IDataSource<GankRandomListBean> bean, boolean isRefresh);
    }

    interface Presenter extends IPresenter<RandomMeiZiListContract.View> {
        void getRandomMeiZiList(GankRandomCategory category, int size, boolean isRefresh);
    }

    interface Model extends IBaseModel {
        Observable<IDataSource<GankRandomListBean>> requestRandomMeiZiList(GankRandomCategory category, int size);
    }
}