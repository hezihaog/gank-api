package com.github.wally.mvp.mvp.model;

import android.os.Bundle;

import com.github.wally.mvp.bean.gank.DisplayMeiZiImageBean;
import com.github.wally.mvp.constants.Constants;
import com.github.wally.mvp.http.IoToMainScheduler;
import com.github.wally.mvp.mvp.contract.GankMeiZiDetailContract;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Package: com.github.wally.mvp.mvp.model
 * FileName: GankMeiZiDetailModel
 * Date: on 2018/6/16  下午6:45
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankMeiZiDetailModel implements GankMeiZiDetailContract.Model {
    @Override
    public Observable<DisplayMeiZiImageBean> requestMeiZiDetail(final Bundle bundle) {
        return Observable.create(new ObservableOnSubscribe<DisplayMeiZiImageBean>() {
            @Override
            public void subscribe(ObservableEmitter<DisplayMeiZiImageBean> emitter) throws Exception {
                DisplayMeiZiImageBean bean = (DisplayMeiZiImageBean) bundle.get(Constants.Key.GANK_MEIZI_BEAN);
                if (bean != null) {
                    emitter.onNext(bean);
                    emitter.onComplete();
                } else {
                    emitter.onError(new NullPointerException("没有传递数据到详情页面"));
                }
            }
        })
                .compose(new IoToMainScheduler<DisplayMeiZiImageBean>());
    }
}
