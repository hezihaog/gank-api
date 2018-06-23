package com.github.wally.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import com.github.wally.base.RecyclerViewHelper;
import com.github.wally.base.widget.recyclerview.manager.FastScrollLinearLayoutManager;
import com.github.wally.mvp.base.BaseMvpListFragment;
import com.github.wally.mvp.bean.gank.GankSearchBean;
import com.github.wally.mvp.constants.Constants;
import com.github.wally.mvp.enums.GankSearchCategory;
import com.github.wally.mvp.http.IDataSource;
import com.github.wally.mvp.mvp.contract.GankSearchCategoryListContract;
import com.github.wally.mvp.mvp.presenter.GankSearchCategoryListPresenter;
import com.github.wally.mvp.viewbinder.GankSearchAllCategoryViewBinder;

import me.drakeet.multitype.ClassLinker;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Package: com.github.wally.mvp.ui.fragment
 * FileName: GankSearchCategoryListFragment
 * Date: on 2018/6/23  下午11:34
 * Auther: zihe
 * Descirbe:搜索页面不同的类型的搜索结果列表界面
 * Email: hezihao@linghit.com
 */
public class GankSearchCategoryListFragment extends BaseMvpListFragment<GankSearchCategoryListContract.Presenter,
        GankSearchCategoryListContract.View> implements GankSearchCategoryListContract.View {
    private static final String KEY_SEARCH_CATEGORY = "key_search_category";
    private GankSearchCategory mCategory;

    public static GankSearchCategoryListFragment newInstance(GankSearchCategory category) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_SEARCH_CATEGORY, category);
        GankSearchCategoryListFragment fragment = new GankSearchCategoryListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onLayoutBefore() {
        super.onLayoutBefore();
        mCategory = (GankSearchCategory) getArguments().getSerializable(KEY_SEARCH_CATEGORY);
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        getRecyclerViewHelper().startRefreshWithLoading();
    }

    @Override
    protected GankSearchCategoryListContract.Presenter onCreatePresenter() {
        return new GankSearchCategoryListPresenter();
    }

    @Override
    protected RecyclerView.LayoutManager onSetupRecyclerViewLayoutManager() {
        return new FastScrollLinearLayoutManager(getActivity());
    }

    @Override
    protected void onRecyclerViewReady(RecyclerView recyclerView) {
        super.onRecyclerViewReady(recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL));
    }

    @Override
    protected void onRegisterRecyclerViewTypeMapper(MultiTypeAdapter adapter) {
        adapter.register(GankSearchBean.Msg.class)
                .to(new GankSearchAllCategoryViewBinder())
                .withClassLinker(new ClassLinker<GankSearchBean.Msg>() {
                    @NonNull
                    @Override
                    public Class<? extends ItemViewBinder<GankSearchBean.Msg, ?>> index(int position, @NonNull GankSearchBean.Msg msg) {
//                if (GankSearchCategory.ALL.equals(msg.getType())) {
//                    return GankSearchAllCategoryViewBinder.class;
//                }
//                return null;
                        // TODO: 2018/6/24 后续加上类型分类的不同条目类
                        return GankSearchAllCategoryViewBinder.class;
                    }
                });
    }

    @Override
    protected RecyclerViewHelper onSetupRecyclerViewHelper(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView, MultiTypeAdapter adapter) {
        return RecyclerViewHelper.create(refreshLayout, recyclerView, adapter, new RecyclerViewHelper.OnLoadListener() {
            @Override
            public void onSwipeRefresh(int page, boolean isFirst) {
                mPresenter.getSearchCategoryList(page, Constants.Config.size, mCategory, true);
            }

            @Override
            public void onLoadMore(int page, boolean isFirst) {
                mPresenter.getSearchCategoryList(page, Constants.Config.size, mCategory, false);
            }
        });
    }

    @Override
    public void showSearchCategoryList(IDataSource<GankSearchBean> dataSource, boolean isRefresh) {
        getRecyclerViewHelper().setHasNext(dataSource.hasNext());
        getRecyclerViewHelper().updateDataSource(isRefresh, dataSource.getData().getResults());
    }

    @Override
    public void showError(Throwable throwable) {

    }
}