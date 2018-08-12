package com.github.wally.gank.mvp.view;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.wally.base.RecyclerViewHelper;
import com.github.wally.base.RecyclerViewScrollHelper;
import com.github.wally.base.base.BaseMvpListFragment;
import com.github.wally.base.http.IDataSource;
import com.github.wally.base.util.ToolBarHelper;
import com.github.wally.base.widget.recyclerview.manager.delegate.FastScrollDelegate;
import com.github.wally.gank.R;
import com.github.wally.gank.bean.gank.GankRandomListBean;
import com.github.wally.gank.enums.GankRandomCategory;
import com.github.wally.gank.mvp.contract.RandomMeiZiListContract;
import com.github.wally.gank.mvp.presenter.GankRandomMeiZiListPresenter;
import com.github.wally.gank.viewbinder.GankRandomMeiZiViewBinder;
import com.gyf.barlibrary.ImmersionBar;

import me.drakeet.multitype.ClassLinker;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Package: com.github.wally.gank.ui.fragment
 * FileName: RandomMeiZiFragment
 * Date: on 2018/6/18  上午10:18
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class RandomMeiZiFragment extends BaseMvpListFragment<RandomMeiZiListContract.Presenter, RandomMeiZiListContract.View>
        implements RandomMeiZiListContract.View {
    private FloatingActionButton mRefreshBtn;

    @Override
    protected boolean setupSwipeBackEnable() {
        return true;
    }

    @Override
    public int onLayoutId() {
        return R.layout.fragment_gank_random_mei_zi_list;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        getRecyclerViewHelper().startRefresh();
    }

    @Override
    public void onFindView(View rootView) {
        super.onFindView(rootView);
        mRefreshBtn = findView(R.id.refresh_action_btn);
    }

    @Override
    public void onBindViewContent() {
        super.onBindViewContent();
        ToolBarHelper toolBarHelper = ToolBarHelper.newBuilder(getView(), R.id.tool_bar, new ToolBarHelper.ConfigCallbackAdapter() {
            @Override
            public void onConfigBefore(Toolbar toolbar) {
                super.onConfigBefore(toolbar);
                ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            }
        })
                .withNavigationIconDrawable(R.drawable.ic_action_back)
                .withNavigationIconOnClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().onBackPressed();
                    }
                })
                .build();
        Toolbar toolbar = toolBarHelper.getToolbar();
        ImmersionBar.with(this).titleBar(toolbar);
        mRefreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRecyclerViewHelper().moveToTop();
                getRecyclerViewHelper().startRefreshWithLoading();
            }
        });
    }

    @Override
    protected RecyclerView.LayoutManager onSetupRecyclerViewLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                new FastScrollDelegate().smoothScrollToPosition(recyclerView, this, position);
            }
        };
    }

    @Override
    protected void onRegisterRecyclerViewTypeMapper(MultiTypeAdapter adapter) {
        adapter.register(GankRandomListBean.MeiZi.class)
                .to(new GankRandomMeiZiViewBinder())
                .withClassLinker(new ClassLinker<GankRandomListBean.MeiZi>() {

                    @NonNull
                    @Override
                    public Class<? extends ItemViewBinder<GankRandomListBean.MeiZi, ?>> index(int position, @NonNull GankRandomListBean.MeiZi meiZi) {
                        if (GankRandomCategory.MEI_ZI.getCategory().equals(meiZi.getType())) {
                            return GankRandomMeiZiViewBinder.class;
                        }
                        return null;
                    }
                });
    }

    @Override
    protected RecyclerViewHelper onSetupRecyclerViewHelper(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView, MultiTypeAdapter adapter) {
        return RecyclerViewHelper.create(refreshLayout, recyclerView, adapter, new RecyclerViewHelper.OnLoadListener() {
            @Override
            public void onSwipeRefresh(int page, boolean isFirst) {
                getPresenter().getRandomMeiZiList(GankRandomCategory.MEI_ZI, 15, true);
            }

            @Override
            public void onLoadMore(int page, boolean isFirst) {
                getPresenter().getRandomMeiZiList(GankRandomCategory.MEI_ZI, 15, false);
            }
        });
    }

    @Override
    protected void onRecyclerViewHelperReady(RecyclerViewHelper recyclerViewHelper) {
        super.onRecyclerViewHelperReady(recyclerViewHelper);
        recyclerViewHelper.addScrollListener(new RecyclerViewScrollHelper.SimpleScrollAdapter() {
            @Override
            public void onScrolledToUp() {
                super.onScrolledToUp();
                mRefreshBtn.hide();
            }

            @Override
            public void onScrolledToDown() {
                super.onScrolledToDown();
                mRefreshBtn.show();
            }
        });
        getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (getRecyclerView() != null) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //停止时，保存位置
                        ((StaggeredGridLayoutManager) getRecyclerView().getLayoutManager())
                                .invalidateSpanAssignments();
                    }
                }
            }
        });
    }

    @Override
    protected RandomMeiZiListContract.Presenter onCreatePresenter() {
        return new GankRandomMeiZiListPresenter();
    }

    @Override
    protected void onRecyclerViewReady(RecyclerView recyclerView) {
        super.onRecyclerViewReady(recyclerView);
        StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
    }

    @Override
    public void showError(Throwable throwable) {

    }

    @Override
    public void showRandomMeiZiList(IDataSource<GankRandomListBean> bean, boolean isRefresh) {
        getRecyclerViewHelper().updateDataSource(isRefresh, bean.getData().getResults(), bean.hasNext());
    }
}