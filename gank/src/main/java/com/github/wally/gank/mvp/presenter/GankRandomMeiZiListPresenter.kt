package com.github.wally.gank.mvp.presenter

import com.github.wally.base.base.BasePresenter
import com.github.wally.gank.enums.GankRandomCategory
import com.github.wally.gank.mvp.contract.RandomMeiZiListContract
import com.github.wally.gank.mvp.model.GankRandomMeiZiListModel

/**
 * Package: com.github.wally.gank.mvp.presenter
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
        mRootView.let {
            val disposable = mModel.requestRandomMeiZiList(category, size)
                    .doOnError { throwable -> it!!.showError(throwable) }
                    .subscribe { gankRandomListBeanDataSource -> it!!.showRandomMeiZiList(gankRandomListBeanDataSource, isRefresh) }
            addSubscription(disposable)
        }
    }
}
