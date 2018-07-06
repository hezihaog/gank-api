package com.github.wally.mvp.mvp.model

import android.os.Bundle
import com.github.wally.mvp.bean.gank.DisplayMeiZiImageBean
import com.github.wally.mvp.constants.Constants
import com.github.wally.mvp.http.scheduler.SchedulerUtils
import com.github.wally.mvp.mvp.contract.GankMeiZiDetailContract
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

/**
 * Package: com.github.wally.mvp.mvp.model
 * FileName: GankMeiZiDetailModel
 * Date: on 2018/6/16  下午6:45
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class GankMeiZiDetailModel : GankMeiZiDetailContract.Model {
    override fun requestMeiZiDetail(bundle: Bundle): Observable<DisplayMeiZiImageBean> {
        return Observable.create(ObservableOnSubscribe<DisplayMeiZiImageBean> { emitter ->
            val bean = bundle.get(Constants.Key.GANK_MEIZI_BEAN) as DisplayMeiZiImageBean
            if (bean != null) {
                emitter.onNext(bean)
                emitter.onComplete()
            } else {
                emitter.onError(NullPointerException("没有传递数据到详情页面"))
            }
        })
                .compose(SchedulerUtils.ioToMain())
    }
}
