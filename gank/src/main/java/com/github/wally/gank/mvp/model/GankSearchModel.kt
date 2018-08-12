package com.github.wally.gank.mvp.model

import com.github.wally.gank.enums.GankSearchCategory
import com.github.wally.gank.mvp.contract.GankSearchContract
import com.github.wally.gank.util.SearchCategoryHelper
import io.reactivex.Observable

/**
 * Package: com.github.wally.gank.mvp.model
 * FileName: GankSearchModel
 * Date: on 2018/6/23  下午11:03
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class GankSearchModel : GankSearchContract.Model {
    override val searchCategoryList: Observable<ArrayList<GankSearchCategory>>
        get() = SearchCategoryHelper.searchCategory
}