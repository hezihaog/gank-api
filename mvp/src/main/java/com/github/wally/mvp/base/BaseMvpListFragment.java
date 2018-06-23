package com.github.wally.mvp.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.wally.base.RecyclerViewHelper;
import com.github.wally.mvp.R;
import com.gyf.barlibrary.ImmersionBar;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Package: com.github.wally.mvp.base
 * FileName: BaseMvpListFragment
 * Date: on 2018/6/18  上午12:16
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public abstract class BaseMvpListFragment<P extends IPresenter<V>, V extends IBaseView>
        extends BaseMvpFragment<P, V> implements ListLayoutCallback {
    private Toolbar mToolbar;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private MultiTypeAdapter mAdapter;
    private RecyclerViewHelper mRecyclerViewHelper;

    @Override
    public int onLayoutId() {
        return R.layout.fragment_base_list;
    }

    @Override
    public void onFindView(View rootView) {
        super.onFindView(rootView);
        mToolbar = findView(R.id.tool_bar);
        mRefreshLayout = findView(R.id.base_refresh_layout);
        mRecyclerView = findView(R.id.base_list);
    }

    @Override
    public void onBindViewContent() {
        super.onBindViewContent();
        //配置ToolBar
        setupToolBar(mToolbar);
        //配置RecyclerView
        setupRecyclerView(mRecyclerView);
        //配置加载帮助类，封装下拉刷新和加载更多
        setupRecyclerViewHelper();
        //通知子类加载帮助类已经初始化完毕，可做一些滚动监听
        onRecyclerViewHelperReady(mRecyclerViewHelper);
    }

    /**
     * 默认是设置返回键和返回功能，如不是该功能，请复写该方法
     */
    protected void setupToolBar(Toolbar toolbar) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        //透明状态栏适配ToolBar
        ImmersionBar.with(this).titleBar(toolbar);
    }

    /**
     * 配置RecyclerView
     */
    private void setupRecyclerView(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = onSetupRecyclerViewLayoutManager();
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MultiTypeAdapter();
        recyclerView.setAdapter(mAdapter);
        //配置多type的映射
        onRegisterRecyclerViewTypeMapper(mAdapter);
        //通知子类列表已经初始化完毕，可以对列表进行下一步自定义，例如增加分割线等
        onRecyclerViewReady(mRecyclerView);
    }

    /**
     * 子类复写返回列表的LayoutManager
     */
    protected abstract RecyclerView.LayoutManager onSetupRecyclerViewLayoutManager();

    /**
     * 注册列表的类型映射
     *
     * @param adapter 列表适配器
     */
    protected abstract void onRegisterRecyclerViewTypeMapper(MultiTypeAdapter adapter);

    /**
     * 列表初始化完毕的回调
     */
    protected void onRecyclerViewReady(RecyclerView recyclerView) {

    }

    /**
     * 配置加载帮助类，封装下拉刷新和加载更多
     */
    private void setupRecyclerViewHelper() {
        //设置下拉刷新和加载更多
        mRecyclerViewHelper = onSetupRecyclerViewHelper(mRefreshLayout, mRecyclerView, mAdapter);
    }

    protected abstract RecyclerViewHelper onSetupRecyclerViewHelper(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView, MultiTypeAdapter adapter);

    /**
     * 加载帮助类初始化完毕，子类复写可设置一些滚动监听
     */
    protected void onRecyclerViewHelperReady(RecyclerViewHelper recyclerViewHelper) {

    }

    public SwipeRefreshLayout getRefreshLayout() {
        return mRefreshLayout;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public MultiTypeAdapter getAdapter() {
        return mAdapter;
    }

    public RecyclerViewHelper getRecyclerViewHelper() {
        return mRecyclerViewHelper;
    }
}