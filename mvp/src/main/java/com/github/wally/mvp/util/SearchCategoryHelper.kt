package com.github.wally.mvp.util

import com.github.wally.mvp.enums.GankSearchCategory
import io.reactivex.Observable
import io.reactivex.functions.BiConsumer
import java.util.*
import java.util.concurrent.Callable

/**
 * Package: com.github.wally.mvp.util
 * FileName: SearchCategoryHelper
 * Date: on 2018/6/23  下午10:44
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
object SearchCategoryHelper {

    /**
     * 组装搜索类型列表
     */
    val searchCategory: Observable<ArrayList<GankSearchCategory>>
        get() = Observable.just(GankSearchCategory.ALL,
                GankSearchCategory.ANDROID, GankSearchCategory.iOS,
                GankSearchCategory.VIDEO, GankSearchCategory.MEIZI,
                GankSearchCategory.EXPAND_RESOURCE, GankSearchCategory.H5,
                GankSearchCategory.RECOMMEND, GankSearchCategory.APP)
                .collect(Callable { ArrayList<GankSearchCategory>() }
                        , BiConsumer<ArrayList<GankSearchCategory>, GankSearchCategory>
                { categoryList, category -> categoryList.add(category) })
                .toObservable()
}