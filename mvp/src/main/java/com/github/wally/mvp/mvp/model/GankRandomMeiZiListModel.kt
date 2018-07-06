package com.github.wally.mvp.mvp.model

import com.github.wally.mvp.base.IBaseModel
import com.github.wally.mvp.bean.gank.GankRandomListBean
import com.github.wally.mvp.enums.GankRandomCategory
import com.github.wally.mvp.http.IDataSource
import com.github.wally.mvp.http.RetrofitManager
import com.github.wally.mvp.http.scheduler.SchedulerUtils
import com.github.wally.mvp.mvp.contract.RandomMeiZiListContract
import io.reactivex.Observable

/**
 * Package: com.github.wally.mvp.mvp.model
 * FileName: GankRandomMeiZiListModel
 * Date: on 2018/6/18  上午10:51
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class GankRandomMeiZiListModel : IBaseModel, RandomMeiZiListContract.Model {

    override fun requestRandomMeiZiList(category: GankRandomCategory, size: Int): Observable<IDataSource<GankRandomListBean>> {
        return RetrofitManager.getInstance()
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