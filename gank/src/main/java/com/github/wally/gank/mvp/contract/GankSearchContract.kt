package com.github.wally.gank.mvp.contract

import com.github.wally.base.base.IModel
import com.github.wally.base.base.IView
import com.github.wally.base.base.IPresenter
import com.github.wally.gank.enums.GankSearchCategory

import io.reactivex.Observable

/**
 * Package: com.github.wally.gank.mvp.contract
 * FileName: GankSearchContract
 * Date: on 2018/6/23  下午10:40
 * Auther: zihe
 * Descirbe:搜索契约类
 * Email: hezihao@linghit.com
 */
interface GankSearchContract {
    interface View : IView {
        /**
         * 分配搜索栏目给TabLayout
         *
         * @param categoryList 分类名集合
         */
        fun allocSearchCategory(categoryList: List<GankSearchCategory>)
    }

    interface Presenter : IPresenter<GankSearchContract.View> {
        /**
         * 请求搜索分类列表
         */
        fun requestSearchCategoryList()
    }

    interface Model : IModel {
        /**
         * 获取搜索分类列表
         */
        val searchCategoryList: Observable<ArrayList<GankSearchCategory>>
    }
}