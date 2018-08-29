package com.github.wally.gank.mvp.model

import com.github.wally.base.base.IModel
import com.github.wally.base.http.BaseDataSource
import com.github.wally.base.http.IDataSource
import com.github.wally.base.http.scheduler.SchedulerUtils
import com.github.wally.gank.bean.gank.GankRandomListBean
import com.github.wally.gank.enums.GankRandomCategory
import com.github.wally.gank.http.RetrofitManager
import com.github.wally.gank.mvp.contract.RandomMeiZiListContract
import io.reactivex.Observable

/**
 * Package: com.github.wally.gank.mvp.model
 * FileName: GankRandomMeiZiListModel
 * Date: on 2018/6/18  上午10:51
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class GankRandomMeiZiListModel : IModel, RandomMeiZiListContract.Model {

    override fun requestRandomMeiZiList(category: GankRandomCategory, size: Int): Observable<IDataSource<GankRandomListBean>> {
        return RetrofitManager.instance
                .gankApiService
                .getRandomContent(category.category, size)
                .map { gankRandomListBean ->
                    //随机妹子图是没有下一页的，所以直接将hasNext设置为false
                    BaseDataSource(gankRandomListBean,
                            false)
                }
                .compose(SchedulerUtils.ioToMain())
    }
}