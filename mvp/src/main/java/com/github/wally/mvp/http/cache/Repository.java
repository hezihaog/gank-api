package com.github.wally.mvp.http.cache;

import com.github.wally.mvp.bean.gank.GankMeiZiListBean;
import com.github.wally.mvp.bean.gank.GankSearchBean;
import com.github.wally.mvp.http.RetrofitManager;
import com.github.wally.mvp.http.api.GankApiService;

import java.io.File;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.DynamicKeyGroup;
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

    /**
     * 获取妹子图列表
     *
     * @param size 请求多少条
     * @param num  请求的页码
     * @return 数据源
     */
    public Observable<GankMeiZiListBean> getMeizi(int size, int num) {
        return cacheProviders.getMeizi(mGankApiService.getMeizi(size, num), new DynamicKey(num));
    }

    /**
     * 获取搜索分类列表
     *
     * @param category 分类的名字
     * @param count    请求多少条
     * @param page     请求的页码
     * @return 数据源
     */
    public Observable<GankSearchBean> getSearchCategoryList(String category, int count, int page) {
        return cacheProviders.getSearchCategoryList(mGankApiService.getSearchCategoryList(category, count, page), new DynamicKeyGroup(page, category));
    }
}