package com.github.wally.gank.http.api


import com.github.wally.gank.bean.gank.GankMeiZiListBean
import com.github.wally.gank.bean.gank.GankRandomListBean
import com.github.wally.gank.bean.gank.GankSearchBean
import com.github.wally.gank.constants.Constants

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Package: com.github.wally.gank.api
 * FileName: GankApiService
 * Date: on 2018/6/16  上午9:46
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
interface GankApiService {
    /**
     * 请求福利图片列表
     *
     * @param size 请求的数量
     * @param num  请求页码
     * @return 数据源
     */
    @GET("data/福利/{size}/{num}")
    fun getMeizi(@Path("size") size: Int = Constants.Config.size, @Path("num") num: Int = Constants.Config.page): Observable<GankMeiZiListBean>

    /**
     * 随机内容
     *
     * @param category 类别
     * @param size     请求的数量
     * @return 数据源
     */
    @GET("random/data/{category}/{size}")
    fun getRandomContent(@Path("category") category: String, @Path("size") size: Int = Constants.Config.size): Observable<GankRandomListBean>

    /**
     * 搜索特定类型的记录
     *
     * @param category 分类的名字
     * @param count    一页多少调
     * @param page     请求的页码
     * @return 数据源
     */
    @GET("search/query/listview/category/{category}/count/{count}/page/{page}")
    fun getSearchCategoryList(@Path("category") category: String, @Path("count") count: Int = Constants.Config.size, @Path("page") page: Int = Constants.Config.page): Observable<GankSearchBean>
}