package com.github.wally.mvp.http;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Package: com.github.wally.mvp.http
 * FileName: IoToMainScheduler
 * Date: on 2018/6/17  下午10:50
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class IoToMainScheduler<T> implements ObservableTransformer<T, T> {
    public IoToMainScheduler() {
    }

    @Override
    public ObservableSource<T> apply(Observable upstream) {
        return upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}