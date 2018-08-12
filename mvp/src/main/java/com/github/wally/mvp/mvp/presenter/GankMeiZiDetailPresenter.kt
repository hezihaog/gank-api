package com.github.wally.mvp.mvp.presenter

import android.os.Bundle
import com.github.wally.base.base.BasePresenter
import com.github.wally.mvp.mvp.contract.GankMeiZiDetailContract
import com.github.wally.mvp.mvp.model.GankMeiZiDetailModel

/**
 * Package: com.github.wally.mvp.mvp.presenter
 * FileName: GankMeiZiDetailPresenter
 * Date: on 2018/6/16  下午6:45
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class GankMeiZiDetailPresenter : BasePresenter<GankMeiZiDetailContract.View>(), GankMeiZiDetailContract.Presenter {
    private val mModel: GankMeiZiDetailContract.Model

    init {
        mModel = GankMeiZiDetailModel()
    }

    override fun getMeiZiDetail(bundle: Bundle) {
        mModel.requestMeiZiDetail(bundle)
                .doOnSubscribe { disposable -> addSubscription(disposable) }
                .subscribe({ bean -> mRootView!!.showMeiZiDetail(bean) }, { throwable -> mRootView!!.showError(throwable) })
    }
}
