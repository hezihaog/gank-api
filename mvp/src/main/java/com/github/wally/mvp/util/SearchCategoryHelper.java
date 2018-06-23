package com.github.wally.mvp.util;

import com.github.wally.mvp.enums.GankSearchCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.functions.BiConsumer;

/**
 * Package: com.github.wally.mvp.util
 * FileName: SearchCategoryHelper
 * Date: on 2018/6/23  下午10:44
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class SearchCategoryHelper {
    private SearchCategoryHelper() {
    }

    /**
     * 组装搜索类型列表
     */
    public static Observable<List<GankSearchCategory>> getSearchCategory() {
        return Observable.just(GankSearchCategory.ALL,
                GankSearchCategory.ANDROID, GankSearchCategory.iOS,
                GankSearchCategory.VIDEO, GankSearchCategory.MEIZI,
                GankSearchCategory.EXPAND_RESOURCE, GankSearchCategory.H5,
                GankSearchCategory.RECOMMEND, GankSearchCategory.APP)
                .collect(new Callable<List<GankSearchCategory>>() {
                    @Override
                    public List<GankSearchCategory> call() throws Exception {
                        return new ArrayList<GankSearchCategory>();
                    }
                }, new BiConsumer<List<GankSearchCategory>, GankSearchCategory>() {
                    @Override
                    public void accept(List<GankSearchCategory> categoryList, GankSearchCategory category) throws Exception {
                        categoryList.add(category);
                    }
                }).toObservable();
    }
}