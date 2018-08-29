package com.github.wally.gank.mvp.presenter

import android.os.Bundle
import com.github.wally.base.base.BasePresenter
import com.github.wally.gank.mvp.contract.GankMeiZiDetailContract
import com.github.wally.gank.mvp.model.GankMeiZiDetailModel

/**
 * Package: com.github.wally.gank.mvp.presenter
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
        mRootView.let {
            mModel.requestMeiZiDetail(bundle)
                    .doOnSubscribe { disposable -> addSubscription(disposable) }
                    .subscribe({ bean -> it!!.showMeiZiDetail(bean) },
                            { throwable -> it!!.showError(throwable) })
        }
    }
}
