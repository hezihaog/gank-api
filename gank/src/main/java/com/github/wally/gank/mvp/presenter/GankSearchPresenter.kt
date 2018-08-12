package com.github.wally.gank.mvp.presenter

import com.github.wally.base.base.BasePresenter
import com.github.wally.gank.mvp.contract.GankSearchContract
import com.github.wally.gank.mvp.model.GankSearchModel

/**
 * Package: com.github.wally.gank.mvp.presenter
 * FileName: GankSearchPresenter
 * Date: on 2018/6/23  下午11:05
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class GankSearchPresenter : BasePresenter<GankSearchContract.View>(), GankSearchContract.Presenter {
    private val mModel: GankSearchContract.Model

    init {
        mModel = GankSearchModel()
    }

    override fun requestSearchCategoryList() {
        val disposable = mModel.searchCategoryList
                .subscribe({ categoryList -> mRootView!!.allocSearchCategory(categoryList) },
                        { throwable -> mRootView!!.showError(throwable) }, { })
        addSubscription(disposable)
    }
}