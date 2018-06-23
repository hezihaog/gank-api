package com.github.wally.mvp.http.api;


import com.github.wally.mvp.bean.gank.GankMeiZiListBean;
import com.github.wally.mvp.bean.gank.GankRandomListBean;
import com.github.wally.mvp.bean.gank.GankSearchBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Package: com.github.wally.mvp.api
 * FileName: GankApiService
 * Date: on 2018/6/16  上午9:46
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public interface GankApiService {
    /**
     * 请求福利图片列表
     *
     * @param size 请求的数量
     * @param num  请求页码
     * @return 数据源
     */
    @GET("data/福利/{size}/{num}")
    Observable<GankMeiZiListBean> getMeizi(@Path("size") int size, @Path("num") int num);

    /**
     * 随机内容
     *
     * @param category 类别
     * @param size     请求的数量
     * @return 数据源
     */
    @GET("random/data/{category}/{size}")
    Observable<GankRandomListBean> getRandomContent(@Path("category") String category, @Path("size") int size);

    /**
     * 搜索特定类型的记录
     *
     * @param category 分类的名字
     * @param count    一页多少调
     * @param page     请求的页码
     * @return 数据源
     */
    @GET("search/query/listview/category/{category}/count/{count}/page/{page}")
    Observable<GankSearchBean> getSearchCategoryList(@Path("category") String category, @Path("count") int count, @Path("page") int page);
}