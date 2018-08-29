package com.github.wally.gank.mvp.model

import android.os.Bundle
import com.github.wally.base.http.scheduler.SchedulerUtils
import com.github.wally.gank.bean.gank.DisplayMeiZiImageBean
import com.github.wally.gank.constants.Constants
import com.github.wally.gank.mvp.contract.GankMeiZiDetailContract
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

/**
 * Package: com.github.wally.gank.mvp.model
 * FileName: GankMeiZiDetailModel
 * Date: on 2018/6/16  下午6:45
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class GankMeiZiDetailModel : GankMeiZiDetailContract.Model {
    override fun requestMeiZiDetail(bundle: Bundle): Observable<DisplayMeiZiImageBean> {
        return Observable.create(ObservableOnSubscribe<DisplayMeiZiImageBean> {
            emitter ->
            val bean: DisplayMeiZiImageBean
            try {
                bean = bundle.get(Constants.Key.GANK_MEIZI_BEAN) as DisplayMeiZiImageBean
                emitter.onNext(bean)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        })
                .compose(SchedulerUtils.ioToMain())
    }
}