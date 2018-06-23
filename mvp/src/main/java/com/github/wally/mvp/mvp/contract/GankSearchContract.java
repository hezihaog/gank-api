package com.github.wally.mvp.mvp.contract;

import com.github.wally.mvp.base.IBaseModel;
import com.github.wally.mvp.base.IBaseView;
import com.github.wally.mvp.base.IPresenter;
import com.github.wally.mvp.enums.GankSearchCategory;

import java.util.List;

import io.reactivex.Observable;

/**
 * Package: com.github.wally.mvp.mvp.contract
 * FileName: GankSearchContract
 * Date: on 2018/6/23  下午10:40
 * Auther: zihe
 * Descirbe:搜索契约类
 * Email: hezihao@linghit.com
 */
public interface GankSearchContract {
    interface View extends IBaseView {
        /**
         * 分配搜索栏目给TabLayout
         *
         * @param categoryList 分类名集合
         */
        void allocSearchCategory(List<GankSearchCategory> categoryList);
    }

    interface Presenter extends IPresenter<GankSearchContract.View> {
        /**
         * 请求搜索分类列表
         */
        void requestSearchCategoryList();
    }

    interface Model extends IBaseModel {
        /**
         * 获取搜索分类列表
         */
        Observable<List<GankSearchCategory>> getSearchCategoryList();
    }
}