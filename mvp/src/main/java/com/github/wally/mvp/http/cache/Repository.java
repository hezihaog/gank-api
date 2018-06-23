package com.github.wally.mvp.http.cache;

import com.github.wally.mvp.http.api.GankApiService;
import com.github.wally.mvp.bean.gank.GankMeiZiListBean;
import com.github.wally.mvp.http.RetrofitManager;

import java.io.File;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;

/**
 * Package: com.github.wally.mvp.http.cache
 * FileName: Repository
 * Date: on 2018/6/16  下午7:21
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class Repository {
    private final GankCacheProviders cacheProviders;
    private final GankApiService mGankApiService;

    public static Repository init(File cacheDir) {
        return new Repository(cacheDir);
    }

    public Repository(File cacheDir) {
        cacheProviders = new RxCache.Builder()
                .persistence(cacheDir, new GsonSpeaker())
                .using(GankCacheProviders.class);

        mGankApiService = RetrofitManager.getInstance().getGankApiService();
    }

    public Observable<GankMeiZiListBean> getMeizi(int size, int num) {
        return cacheProviders.getMeizi(mGankApiService.getMeizi(size, num), new DynamicKey(num));
    }
}