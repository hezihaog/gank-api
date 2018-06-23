package com.github.wally.mvp.mvp.model;

import com.github.wally.mvp.enums.GankSearchCategory;
import com.github.wally.mvp.mvp.contract.GankSearchContract;
import com.github.wally.mvp.util.SearchCategoryHelper;

import java.util.List;

import io.reactivex.Observable;

/**
 * Package: com.github.wally.mvp.mvp.model
 * FileName: GankSearchModel
 * Date: on 2018/6/23  下午11:03
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankSearchModel implements GankSearchContract.Model {
    @Override
    public Observable<List<GankSearchCategory>> getSearchCategoryList() {
        return SearchCategoryHelper.getSearchCategory();
    }
}
