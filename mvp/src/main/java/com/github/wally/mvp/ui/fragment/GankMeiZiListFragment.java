package com.github.wally.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.github.wally.base.RecyclerViewHelper;
import com.github.wally.base.RecyclerViewScrollHelper;
import com.github.wally.base.util.PositionUtil;
import com.github.wally.base.widget.recyclerview.manager.FastScrollStaggeredGridLayoutManager;
import com.github.wally.mvp.R;
import com.github.wally.mvp.base.BaseMvpListFragment;
import com.github.wally.mvp.bean.gank.GankMeiZiListBean;
import com.github.wally.mvp.http.IDataSource;
import com.github.wally.mvp.mvp.contract.GankMeiZiListContract;
import com.github.wally.mvp.mvp.presenter.GankMeiZiListPresenter;
import com.github.wally.mvp.viewbinder.GankMeiZiViewBinder;
import com.github.wally.mvp.widget.RecyclerViewItemDecoration;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Package: com.github.wally.mvp.ui.fragment
 * FileName: GankMeiziFragment
 * Date: on 2018/6/16  上午10:35
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankMeiZiListFragment extends BaseMvpListFragment<GankMeiZiListContract.Presenter, GankMeiZiListContract.View> implements GankMeiZiListContract.View {
    private int mLastListPosition;
    private FloatingActionButton mFloatingActionButton;

    @Override
    public int onLayoutId() {
        return R.layout.fragment_gank_mei_zi_list;
    }

    @Override
    public void onFindView(View rootView) {
        super.onFindView(rootView);
        mFloatingActionButton = findView(R.id.floating_action_btn);
    }

    @Override
    protected GankMeiZiListContract.Presenter onCreatePresenter() {
        return new GankMeiZiListPresenter();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("key_position", mLastListPosition);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mLastListPosition = savedInstanceState.getInt("key_position");
            getRecyclerView().scrollToPosition(mLastListPosition);
        }
    }

    @Override
    public void onBindViewContent() {
        super.onBindViewContent();
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRecyclerViewHelper().moveToTop();
            }
        });
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        getRecyclerViewHelper().startRefresh();
    }

    @Override
    protected RecyclerView.LayoutManager onSetupRecyclerViewLayoutManager() {
        return new FastScrollStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    @Override
    protected void onRecyclerViewReady(RecyclerView recyclerView) {
        super.onRecyclerViewReady(recyclerView);
        recyclerView.addItemDecoration(new RecyclerViewItemDecoration(12));
    }

    @Override
    protected void onRegisterRecyclerViewTypeMapper(MultiTypeAdapter adapter) {
        adapter.register(GankMeiZiListBean.MeiZi.class, new GankMeiZiViewBinder());
    }

    @Override
    protected void onRecyclerViewHelperReady(RecyclerViewHelper recyclerViewHelper) {
        super.onRecyclerViewHelperReady(recyclerViewHelper);
        //滚动监听
        recyclerViewHelper.addScrollListener(new RecyclerViewScrollHelper.SimpleScrollAdapter() {

            @Override
            public void onScrolledToUp() {
                super.onScrolledToUp();
                mFloatingActionButton.hide();
            }

            @Override
            public void onScrolledToDown() {
                super.onScrolledToDown();
                mFloatingActionButton.show();
            }
        });
        getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (getRecyclerView() != null) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //停止时，保存位置
                        mLastListPosition = PositionUtil.getCurrentPosition(getRecyclerView());
                    }
                }
            }
        });
    }

    @Override
    protected RecyclerViewHelper onSetupRecyclerViewHelper(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView, MultiTypeAdapter adapter) {
        //设置下拉刷新和加载更多
        return RecyclerViewHelper.create(refreshLayout, recyclerView, adapter, new RecyclerViewHelper.OnLoadListener() {
            @Override
            public void onSwipeRefresh(int page, boolean isFirst) {
                getPresenter().getMeiZiList(page, 15, true);
            }

            @Override
            public void onLoadMore(int page, boolean isFirst) {
                getPresenter().getMeiZiList(page, 15, false);
            }
        });
    }

    @Override
    public void showMeiZiList(IDataSource<GankMeiZiListBean> meiziGankBean, boolean isRefresh) {
        //必须调用设置数据集的方法进行更新数据
        getRecyclerViewHelper().updateDataSource(isRefresh, meiziGankBean.getData().getResults(), meiziGankBean.hasNext());
    }

    @Override
    public void showError(Throwable throwable) {

    }
}
