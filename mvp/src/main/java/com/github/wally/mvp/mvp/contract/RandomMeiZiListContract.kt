package com.github.wally.mvp.mvp.contract

import com.github.wally.base.base.IBaseModel
import com.github.wally.base.base.IBaseView
import com.github.wally.base.base.IPresenter
import com.github.wally.mvp.bean.gank.GankRandomListBean
import com.github.wally.mvp.enums.GankRandomCategory
import com.github.wally.base.http.IDataSource

import io.reactivex.Observable

/**
 * Package: com.github.wally.mvp.mvp.contract
 * FileName: RandomMeiZiContract
 * Date: on 2018/6/18  上午10:18
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
interface RandomMeiZiListContract {
    interface View : IBaseView {
        fun showRandomMeiZiList(bean: IDataSource<GankRandomListBean>, isRefresh: Boolean)
    }

    interface Presenter : IPresenter<RandomMeiZiListContract.View> {
        fun getRandomMeiZiList(category: GankRandomCategory, size: Int, isRefresh: Boolean)
    }

    interface Model : IBaseModel {
        fun requestRandomMeiZiList(category: GankRandomCategory, size: Int): Observable<IDataSource<GankRandomListBean>>
    }
}