package com.github.wally.gank.mvp.contract

import com.github.wally.base.base.IBaseModel
import com.github.wally.base.base.IBaseView
import com.github.wally.base.base.IPresenter
import com.github.wally.gank.bean.gank.GankSearchBean
import com.github.wally.gank.enums.GankSearchCategory
import com.github.wally.base.http.IDataSource

import io.reactivex.Observable

/**
 * Package: com.github.wally.gank.mvp.contract
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