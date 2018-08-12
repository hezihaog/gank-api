package com.github.wally.gank.mvp.presenter

import com.github.wally.base.base.BasePresenter
import com.github.wally.gank.enums.GankSearchCategory
import com.github.wally.gank.mvp.contract.GankSearchCategoryListContract
import com.github.wally.gank.mvp.model.GankSearchCategoryListModel

/**
 * Package: com.github.wally.gank.mvp.presenter
 * FileName: GankSearchCategoryListPresenter
 * Date: on 2018/6/24  上午12:09
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class GankSearchCategoryListPresenter : BasePresenter<GankSearchCategoryListContract.View>(), GankSearchCategoryListContract.Presenter {
    private val mModel: GankSearchCategoryListContract.Model

    init {
        mModel = GankSearchCategoryListModel()
    }

    override fun getSearchCategoryList(page: Int, count: Int, category: GankSearchCategory, isRefresh: Boolean) {
        mModel.requestSearchCategoryList(page, count, category)
                .subscribe({ dataSource -> mRootView!!.showSearchCategoryList(dataSource, isRefresh) }, { throwable -> mRootView!!.showError(throwable) })
    }
}
