package com.github.wally.gank.mvp.contract

import com.github.wally.base.base.IBaseModel
import com.github.wally.base.base.IBaseView
import com.github.wally.base.base.IPresenter
import com.github.wally.gank.bean.gank.GankMeiZiListBean
import com.github.wally.base.http.IDataSource

import io.reactivex.Observable

/**
 * Package: com.github.wally.gank.mvp.contract
 * FileName: GankMeiziContract
 * Date: on 2018/6/16  上午10:35
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
interface GankMeiZiListContract {
    interface View : IBaseView {
        fun showMeiZiList(meiziGankBean: IDataSource<GankMeiZiListBean>, isRefresh: Boolean)
    }

    interface Presenter : IPresenter<View> {
        fun getMeiZiList(page: Int, size: Int, isRefresh: Boolean)
    }

    interface Model : IBaseModel {
        fun requestMeiZiList(page: Int, size: Int): Observable<IDataSource<GankMeiZiListBean>>
    }
}