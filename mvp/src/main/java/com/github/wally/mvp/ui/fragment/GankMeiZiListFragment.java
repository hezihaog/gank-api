package com.github.wally.mvp.ui.fragment;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.github.wally.base.RecyclerViewHelper;
import com.github.wally.base.RecyclerViewScrollHelper;
import com.github.wally.base.widget.recyclerview.manager.FastScrollStaggeredGridLayoutManager;
import com.github.wally.mvp.R;
import com.github.wally.mvp.base.BaseMvpListFragment;
import com.github.wally.mvp.bean.gank.GankMeiZiListBean;
import com.github.wally.mvp.http.IDataSource;
import com.github.wally.mvp.mvp.contract.GankMeiZiListContract;
import com.github.wally.mvp.mvp.presenter.GankMeiZiListPresenter;
import com.github.wally.mvp.util.UIHelper;
import com.github.wally.mvp.viewbinder.GankMeiZiViewBinder;
import com.github.wally.mvp.widget.RecyclerViewItemDecoration;
import com.gyf.barlibrary.ImmersionBar;

import me.drakeet.multitype.MultiTypeAdapter;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Package: com.github.wally.mvp.ui.fragment
 * FileName: GankMeiziFragment
 * Date: on 2018/6/16  上午10:35
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankMeiZiListFragment extends BaseMvpListFragment<GankMeiZiListContract.Presenter, GankMeiZiListContract.View> implements GankMeiZiListContract.View {
    private FloatingActionButton mFloatingActionButton;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    public int onLayoutId() {
        return R.layout.fragment_gank_mei_zi_list;
    }

    @Override
    protected GankMeiZiListContract.Presenter onCreatePresenter() {
        return new GankMeiZiListPresenter();
    }

    @Override
    public void onFindView(View rootView) {
        super.onFindView(rootView);
        mDrawerLayout = findView(R.id.drawer_layout);
        mNavigationView = findView(R.id.navigation_view);
        mFloatingActionButton = findView(R.id.floating_action_btn);
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
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.random_mei_zi:
                        UIHelper.showRandomMeiZiList((SupportActivity) getActivity());
                        break;
                    default:
                        break;
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    @Override
    protected void setupToolBar(Toolbar toolbar) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        //透明状态栏适配ToolBar
        ImmersionBar.with(this).titleBar(toolbar);
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
    }

    @Override
    protected RecyclerViewHelper onSetupRecyclerViewHelper(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView, MultiTypeAdapter adapter) {
        //设置下拉刷新和加载更多
        return RecyclerViewHelper.create(refreshLayout, recyclerView, adapter, new RecyclerViewHelper.OnLoadListener() {
            @Override
            public void onSwipeRefresh(int page, boolean isFirst) {
                mPresenter.getMeiZiList(page, 15, true);
            }

            @Override
            public void onLoadMore(int page, boolean isFirst) {
                mPresenter.getMeiZiList(page, 15, false);
            }
        });
    }

    @Override
    public void showMeiZiList(IDataSource<GankMeiZiListBean> meiziGankBean, boolean isRefresh) {
        //必须调用设置数据集的方法进行更新数据
        getRecyclerViewHelper().updateDataSource(isRefresh, meiziGankBean.getData().getResults());
        getRecyclerViewHelper().setHasNext(meiziGankBean.hasNext());
    }

    @Override
    public void showError(Throwable throwable) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.tool_bar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                return true;
            case R.id.random_mei_zi:
                UIHelper.showRandomMeiZiList((SupportActivity) getActivity());
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onBackPressedSupport() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else {
            return super.onBackPressedSupport();
        }
    }
}
