package com.github.wally.gank.mvp.contract

import android.os.Bundle

import com.github.wally.base.base.IModel
import com.github.wally.base.base.IView
import com.github.wally.base.base.IPresenter
import com.github.wally.gank.bean.gank.DisplayMeiZiImageBean

import io.reactivex.Observable

/**
 * Package: com.github.wally.gank.mvp.contract
 * FileName: GankMeiZiDetailContract
 * Date: on 2018/6/16  下午6:42
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
interface GankMeiZiDetailContract {
    interface View : IView {
        fun showMeiZiDetail(bean: DisplayMeiZiImageBean)
    }

    interface Presenter : IPresenter<GankMeiZiDetailContract.View> {
        fun getMeiZiDetail(bundle: Bundle)
    }

    interface Model : IModel {
        fun requestMeiZiDetail(bundle: Bundle): Observable<DisplayMeiZiImageBean>
    }
}