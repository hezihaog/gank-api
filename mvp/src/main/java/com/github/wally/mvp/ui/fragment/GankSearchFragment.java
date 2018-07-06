package com.github.wally.mvp.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.wally.mvp.R;
import com.github.wally.mvp.adapter.PageFragmentStateAdapter;
import com.github.wally.mvp.base.BaseMvpFragment;
import com.github.wally.mvp.base.BasePresenter;
import com.github.wally.mvp.enums.GankSearchCategory;
import com.github.wally.mvp.mvp.contract.GankSearchContract;
import com.github.wally.mvp.mvp.presenter.GankSearchPresenter;
import com.github.wally.mvp.util.ToolBarHelper;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Package: com.github.wally.mvp.ui.fragment
 * FileName: SearchFragment
 * Date: on 2018/6/23  下午10:40
 * Auther: zihe
 * Descirbe:搜索页面
 * Email: hezihao@linghit.com
 */
public class GankSearchFragment extends BaseMvpFragment<GankSearchContract.Presenter, GankSearchContract.View>
        implements GankSearchContract.View {
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<String> mTabTitles;
    private List<Fragment> mFragments;
    private PageFragmentStateAdapter mPageAdapter;

    @Override
    protected boolean setupSwipeBackEnable() {
        return true;
    }

    @Override
    public int onLayoutId() {
        return R.layout.fragment_gank_search;
    }

    @Override
    public void onFindView(View rootView) {
        super.onFindView(rootView);
        mToolbar = findView(R.id.tool_bar);
        mTabLayout = findView(R.id.tab_layout);
        mViewPager = findView(R.id.view_pager);
    }

    @Override
    public void onBindViewContent() {
        super.onBindViewContent();
        ToolBarHelper.newBuilder(getView(), R.id.tool_bar, new ToolBarHelper.ConfigCallbackAdapter() {
            @Override
            public void onConfigBefore(Toolbar toolbar) {
                super.onConfigBefore(toolbar);
                ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            }
        })
                .withNavigationIconDrawable(R.drawable.ic_action_back)
                .withNavigationIconOnClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().onBackPressed();
                    }
                }).build();
        ImmersionBar.with(this).titleBar(mToolbar);
        mPageAdapter = new PageFragmentStateAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPageAdapter);
        //Tab较多，要切换为可混动的模式
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        mPresenter.requestSearchCategoryList();
    }

    @Override
    protected GankSearchContract.Presenter onCreatePresenter() {
        return new GankSearchPresenter();
    }

    @Override
    public void showError(Throwable throwable) {

    }

    @Override
    public void allocSearchCategory(final List<GankSearchCategory> categoryList) {
        BasePresenter presenter = (BasePresenter) getPresenter();
        //顶部Tab数据
        Disposable tabDisposable = Observable
                .fromIterable(categoryList)
                .map(new Function<GankSearchCategory, String>() {
                    @Override
                    public String apply(GankSearchCategory gankSearchCategory) throws Exception {
                        return gankSearchCategory.getCategory();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String category) throws Exception {
                        if (mTabTitles == null) {
                            mTabTitles = new ArrayList<String>();
                        }
                        mTabTitles.add(category);
                    }
                });
        Disposable fragmentDisposable = Observable.fromIterable(categoryList)
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        //Title和Fragment分配好后，更新Page
                        mPageAdapter.setDatas(mFragments, mTabTitles);
                        mPageAdapter.notifyDataSetChanged();
                    }
                })
                .subscribe(new Consumer<GankSearchCategory>() {
                    @Override
                    public void accept(GankSearchCategory gankSearchCategory) throws Exception {
                        if (mFragments == null) {
                            mFragments = new ArrayList<Fragment>();
                        }
                        mFragments.add(GankSearchCategoryListFragment.newInstance(gankSearchCategory));
                    }
                });
        presenter.addSubscription(tabDisposable);
        presenter.addSubscription(fragmentDisposable);
    }
}