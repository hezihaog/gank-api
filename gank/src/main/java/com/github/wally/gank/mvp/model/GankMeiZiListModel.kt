package com.github.wally.gank.mvp.model

import com.github.wally.base.http.BaseDataSource
import com.github.wally.base.http.IDataSource
import com.github.wally.base.http.scheduler.SchedulerUtils
import com.github.wally.gank.AppContext
import com.github.wally.gank.bean.gank.GankMeiZiListBean
import com.github.wally.gank.mvp.contract.GankMeiZiListContract
import io.reactivex.Observable

/**
 * Package: com.github.wally.gank.mvp.model
 * FileName: GankMeiziModel
 * Date: on 2018/6/16  上午10:39
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class GankMeiZiListModel : GankMeiZiListContract.Model {

    override fun requestMeiZiList(page: Int, size: Int): Observable<IDataSource<GankMeiZiListBean>> {
        //这里直接用RxCache，会自动判断内存缓存、磁盘缓存，没有才去调用网络接口
        return AppContext.instance.repository
                .getMeizi(size, page)
                .map { gankMeiZiListBean ->
                    val hasNext = gankMeiZiListBean.results!!.size >= size
                    BaseDataSource(gankMeiZiListBean, hasNext)
                }
                .compose(SchedulerUtils.ioToMain())
    }
}