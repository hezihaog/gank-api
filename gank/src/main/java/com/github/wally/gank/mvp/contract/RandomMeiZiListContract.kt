package com.github.wally.gank.mvp.contract

import com.github.wally.base.base.IModel
import com.github.wally.base.base.IView
import com.github.wally.base.base.IPresenter
import com.github.wally.gank.bean.gank.GankRandomListBean
import com.github.wally.gank.enums.GankRandomCategory
import com.github.wally.base.http.IDataSource

import io.reactivex.Observable

/**
 * Package: com.github.wally.gank.mvp.contract
 * FileName: RandomMeiZiContract
 * Date: on 2018/6/18  上午10:18
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
interface RandomMeiZiListContract {
    interface View : IView {
        fun showRandomMeiZiList(bean: IDataSource<GankRandomListBean>, isRefresh: Boolean)
    }

    interface Presenter : IPresenter<RandomMeiZiListContract.View> {
        fun getRandomMeiZiList(category: GankRandomCategory, size: Int, isRefresh: Boolean)
    }

    interface Model : IModel {
        fun requestRandomMeiZiList(category: GankRandomCategory, size: Int): Observable<IDataSource<GankRandomListBean>>
    }
}