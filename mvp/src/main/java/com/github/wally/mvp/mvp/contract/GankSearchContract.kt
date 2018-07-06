package com.github.wally.mvp.mvp.contract

import com.github.wally.mvp.base.IBaseModel
import com.github.wally.mvp.base.IBaseView
import com.github.wally.mvp.base.IPresenter
import com.github.wally.mvp.enums.GankSearchCategory

import io.reactivex.Observable

/**
 * Package: com.github.wally.mvp.mvp.contract
 * FileName: GankSearchContract
 * Date: on 2018/6/23  下午10:40
 * Auther: zihe
 * Descirbe:搜索契约类
 * Email: hezihao@linghit.com
 */
interface GankSearchContract {
    interface View : IBaseView {
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

    interface Model : IBaseModel {
        /**
         * 获取搜索分类列表
         */
        val searchCategoryList: Observable<List<GankSearchCategory>>
    }
}