package com.github.wally.mvp.mvp.presenter

import com.github.wally.base.base.BasePresenter
import com.github.wally.mvp.enums.GankRandomCategory
import com.github.wally.mvp.mvp.contract.RandomMeiZiListContract
import com.github.wally.mvp.mvp.model.GankRandomMeiZiListModel

/**
 * Package: com.github.wally.mvp.mvp.presenter
 * FileName: GankRandomMeiZiListPresenter
 * Date: on 2018/6/18  上午10:59
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class GankRandomMeiZiListPresenter : BasePresenter<RandomMeiZiListContract.View>(), RandomMeiZiListContract.Presenter {
    private val mModel: RandomMeiZiListContract.Model

    init {
        mModel = GankRandomMeiZiListModel()
    }

    override fun getRandomMeiZiList(category: GankRandomCategory, size: Int, isRefresh: Boolean) {
        val disposable = mModel.requestRandomMeiZiList(category, size)
                .doOnError { throwable -> mRootView!!.showError(throwable) }
                .subscribe { gankRandomListBeanDataSource -> mRootView!!.showRandomMeiZiList(gankRandomListBeanDataSource, isRefresh) }
        addSubscription(disposable)
    }
}
