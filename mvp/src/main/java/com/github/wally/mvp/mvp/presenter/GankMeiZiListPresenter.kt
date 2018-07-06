package com.github.wally.mvp.mvp.presenter

import com.github.wally.mvp.base.BasePresenter
import com.github.wally.mvp.mvp.contract.GankMeiZiListContract
import com.github.wally.mvp.mvp.model.GankMeiZiListModel

/**
 * Package: com.github.wally.mvp.mvp.presenter
 * FileName: GankMeiziListPresenter
 * Date: on 2018/6/16  上午10:41
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class GankMeiZiListPresenter : BasePresenter<GankMeiZiListContract.View>(), GankMeiZiListContract.Presenter {
    private val mModel: GankMeiZiListModel

    init {
        mModel = GankMeiZiListModel()
    }

    override fun getMeiZiList(page: Int, size: Int, isRefresh: Boolean) {
        val disposable = mModel.requestMeiZiList(page, size)
                .subscribe({ gankMeiZiListBeanIDataSource -> mRootView!!.showMeiZiList(gankMeiZiListBeanIDataSource, isRefresh) }, { throwable -> mRootView!!.showError(throwable) })
        addSubscription(disposable)
    }
}