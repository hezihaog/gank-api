package com.github.wally.mvp.mvp.contract

import com.github.wally.mvp.base.IBaseModel
import com.github.wally.mvp.base.IBaseView
import com.github.wally.mvp.base.IPresenter
import com.github.wally.mvp.bean.gank.GankSearchBean
import com.github.wally.mvp.enums.GankSearchCategory
import com.github.wally.mvp.http.IDataSource

import io.reactivex.Observable

/**
 * Package: com.github.wally.mvp.mvp.contract
 * FileName: GankSearchCategoryListContract
 * Date: on 2018/6/23  下午11:49
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
interface GankSearchCategoryListContract {
    interface View : IBaseView {
        /**
         * 展示特定类型的记录列表
         *
         * @param dataSource 数据bean
         * @param isRefresh  是否是刷新
         */
        fun showSearchCategoryList(dataSource: IDataSource<GankSearchBean>, isRefresh: Boolean)
    }

    interface Presenter : IPresenter<GankSearchCategoryListContract.View> {
        /**
         * 获取特定类型的搜索记录列表
         *
         * @param page      请求的页码
         * @param count     一页多少条
         * @param category  分类类型枚举
         * @param isRefresh 是否是刷新
         */
        fun getSearchCategoryList(page: Int, count: Int, category: GankSearchCategory, isRefresh: Boolean)
    }

    interface Model : IBaseModel {
        /**
         * 请求特定类型的搜索记录列表
         *
         * @param page     请求的页码
         * @param count    一页多少条
         * @param category 分类类型枚举
         */
        fun requestSearchCategoryList(page: Int, count: Int, category: GankSearchCategory): Observable<IDataSource<GankSearchBean>>
    }
}