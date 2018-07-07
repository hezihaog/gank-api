package com.github.wally.mvp.mvp.contract

import android.os.Bundle

import com.github.wally.mvp.base.IBaseModel
import com.github.wally.mvp.base.IBaseView
import com.github.wally.mvp.base.IPresenter
import com.github.wally.mvp.bean.gank.DisplayMeiZiImageBean

import io.reactivex.Observable

/**
 * Package: com.github.wally.mvp.mvp.contract
 * FileName: GankMeiZiDetailContract
 * Date: on 2018/6/16  下午6:42
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
interface GankMeiZiDetailContract {
    interface View : IBaseView {
        fun showMeiZiDetail(bean: DisplayMeiZiImageBean)
    }

    interface Presenter : IPresenter<GankMeiZiDetailContract.View> {
        fun getMeiZiDetail(bundle: Bundle)
    }

    interface Model : IBaseModel {
        fun requestMeiZiDetail(bundle: Bundle): Observable<DisplayMeiZiImageBean>
    }
}