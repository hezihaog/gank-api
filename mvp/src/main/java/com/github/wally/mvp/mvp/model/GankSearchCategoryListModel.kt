package com.github.wally.mvp.mvp.model

import com.github.wally.mvp.AppContext
import com.github.wally.mvp.bean.gank.GankSearchBean
import com.github.wally.mvp.enums.GankSearchCategory
import com.github.wally.mvp.http.BaseDataSource
import com.github.wally.mvp.http.IDataSource
import com.github.wally.mvp.http.scheduler.SchedulerUtils
import com.github.wally.mvp.mvp.contract.GankSearchCategoryListContract
import io.reactivex.Observable

/**
 * Package: com.github.wally.mvp.mvp.model
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
                            gankSearchBean.results.size >= count)
                }
                .compose(SchedulerUtils.ioToMain())
    }
}