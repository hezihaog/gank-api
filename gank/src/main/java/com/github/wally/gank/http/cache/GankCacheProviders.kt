package com.github.wally.gank.http.cache

import com.github.wally.gank.bean.gank.GankMeiZiListBean
import com.github.wally.gank.bean.gank.GankSearchBean
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.DynamicKeyGroup
import io.rx_cache2.LifeCache
import java.util.concurrent.TimeUnit


/**
 * Package: com.github.wally.gank.http.cache
 * FileName: GankCacheProviders
 * Date: on 2018/6/16  下午7:15
 * Auther: zihe
 * Descirbe:RxCache提供者
 * Email: hezihao@linghit.com
 */
interface GankCacheProviders {
    /**
     * 缓存妹子图片列表
     *
     * @param origin      调用网络的数据源，在没有缓存的情况下会调用
     * @param numQuerieds 因为存在分页，这里需要传入一个DynamicKey的实例，标识他是一个动态变化的参数，并且只能传一个，否则多页也只会缓存一页
     */
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    fun getMeizi(origin: Observable<GankMeiZiListBean>, numQuerieds: DynamicKey): Observable<GankMeiZiListBean>

    /**
     * 缓存搜索类型列表
     *
     * @param origin                 如果没有缓存，则会调用网络的数据源
     * @param numAndCategoryQuerieds 每次页码会变动，这里还会变动的还有分类，当有2个动态Key时，需要声明为Group组，第一个参数是页码，第二个就是分类了
     */
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    fun getSearchCategoryList(origin: Observable<GankSearchBean>, numAndCategoryQuerieds: DynamicKeyGroup): Observable<GankSearchBean>
}