package com.github.wally.mvp.http.cache;

import com.github.wally.mvp.bean.gank.GankMeiZiListBean;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.LifeCache;


/**
 * Package: com.github.wally.mvp.http.cache
 * FileName: GankCacheProviders
 * Date: on 2018/6/16  下午7:15
 * Auther: zihe
 * Descirbe:RxCache提供者
 * Email: hezihao@linghit.com
 */
public interface GankCacheProviders {
    /**
     * 缓存妹子图片列表
     *
     * @param origin      调用网络的数据源，在没有缓存的情况下会调用
     * @param numQuerieds 因为存在分页，这里需要传入一个DynamicKey的实例，标识他是一个动态变化的参数，并且只能传一个，否则多页也只会缓存一页
     */
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<GankMeiZiListBean> getMeizi(Observable<GankMeiZiListBean> origin, DynamicKey numQuerieds);
}