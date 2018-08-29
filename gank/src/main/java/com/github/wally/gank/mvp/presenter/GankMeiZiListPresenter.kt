package com.github.wally.gank.mvp.presenter

import com.github.wally.base.base.BasePresenter
import com.github.wally.gank.mvp.contract.GankMeiZiListContract
import com.github.wally.gank.mvp.model.GankMeiZiListModel

/**
 * Package: com.github.wally.gank.mvp.presenter
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
        mRootView.let {
            val disposable = mModel.requestMeiZiList(page, size)
                    .subscribe({ gankMeiZiListBeanIDataSource -> it!!.showMeiZiList(gankMeiZiListBeanIDataSource, isRefresh) }
                            , { throwable -> it!!.showError(throwable) })
            addSubscription(disposable)
        }
    }
}