package com.github.wally.gank.mvp.model

import com.github.wally.gank.AppContext
import com.github.wally.gank.bean.gank.GankSearchBean
import com.github.wally.gank.enums.GankSearchCategory
import com.github.wally.base.http.BaseDataSource
import com.github.wally.base.http.IDataSource
import com.github.wally.base.http.scheduler.SchedulerUtils
import com.github.wally.gank.mvp.contract.GankSearchCategoryListContract
import io.reactivex.Observable

/**
 * Package: com.github.wally.gank.mvp.model
 * FileName: GankSearchCategoryListModel
 * Date: on 2018/7/6  上午11:40
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class GankSearchCategoryListModel : GankSearchCategoryListContract.Model {
    override fun requestSearchCategoryList(page: Int, count: Int, category: GankSearchCategory): Observable<IDataSource<GankSearchBean>> {
        return AppContext.instance!!
                .repository!!
                .getSearchCategoryList(category?.getCategory(), count, page)
                .map { gankSearchBean ->
                    BaseDataSource(gankSearchBean,
                            gankSearchBean.results!!.size >= count)
                }
                .compose(SchedulerUtils.ioToMain())
    }
}