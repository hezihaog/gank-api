package com.github.wally.gank.http.cache

import com.github.wally.gank.bean.gank.GankMeiZiListBean
import com.github.wally.gank.bean.gank.GankSearchBean
import com.github.wally.gank.http.RetrofitManager
import com.github.wally.gank.http.api.GankApiService
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.DynamicKeyGroup
import io.rx_cache2.internal.RxCache
import io.victoralbertos.jolyglot.GsonSpeaker
import java.io.File

/**
 * Package: com.github.wally.gank.http.cache
 * FileName: Repository
 * Date: on 2018/6/16  下午7:21
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class Repository(cacheDir: File) {
    private val cacheProviders: GankCacheProviders
    private val mGankApiService: GankApiService?

    init {
        cacheProviders = RxCache.Builder()
                .persistence(cacheDir, GsonSpeaker())
                .using(GankCacheProviders::class.java)
        mGankApiService = RetrofitManager.instance.gankApiService
    }

    /**
     * 获取妹子图列表
     *
     * @param size 请求多少条
     * @param num  请求的页码
     * @return 数据源
     */
    fun getMeizi(size: Int, num: Int): Observable<GankMeiZiListBean> {
        return cacheProviders.getMeizi(mGankApiService!!.getMeizi(size, num), DynamicKey(num))
    }

    /**
     * 获取搜索分类列表
     *
     * @param category 分类的名字
     * @param count    请求多少条
     * @param page     请求的页码
     * @return 数据源
     */
    fun getSearchCategoryList(category: String, count: Int, page: Int): Observable<GankSearchBean> {
        return cacheProviders.getSearchCategoryList(mGankApiService!!.getSearchCategoryList(category, count, page), DynamicKeyGroup(page, category))
    }

    companion object {
        fun init(cacheDir: File): Repository {
            return Repository(cacheDir)
        }
    }
}