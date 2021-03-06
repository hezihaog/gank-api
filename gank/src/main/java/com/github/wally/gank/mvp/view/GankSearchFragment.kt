package com.github.wally.gank.mvp.view

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.github.wally.base.adapter.PageFragmentStateAdapter
import com.github.wally.base.base.BaseMvpFragment
import com.github.wally.base.base.BasePresenter
import com.github.wally.base.base.IPresenter
import com.github.wally.base.util.ToolBarHelper
import com.github.wally.gank.R
import com.github.wally.gank.enums.GankSearchCategory
import com.github.wally.gank.ext.findView
import com.github.wally.gank.mvp.contract.GankSearchContract
import com.github.wally.gank.mvp.presenter.GankSearchPresenter
import com.gyf.barlibrary.ImmersionBar
import io.reactivex.Observable

/**
 * Package: com.github.wally.gank.ui.fragment
 * FileName: SearchFragment
 * Date: on 2018/6/23  下午10:40
 * Auther: zihe
 * Descirbe:搜索页面
 * Email: hezihao@linghit.com
 */
class GankSearchFragment : BaseMvpFragment<GankSearchContract.View>(), GankSearchContract.View {
    private val mToolbar: Toolbar
        get() {
            return findView(R.id.tool_bar)
        }
    private val mTabLayout: TabLayout
        get() {
            return findView(R.id.tab_layout)
        }
    private val mViewPager: ViewPager
        get() {
            return findView(R.id.view_pager)
        }
    private val presenter: GankSearchContract.Presenter by lazy {
        GankSearchPresenter()
    }
    private var mTabTitles: MutableList<String>? = null
    private var mFragments: MutableList<Fragment>? = null
    private lateinit var mPageAdapter: PageFragmentStateAdapter

    override fun setupSwipeBackEnable(): Boolean {
        return true
    }

    override fun onLayoutId(): Int {
        return R.layout.fragment_gank_search
    }

    override fun onBindViewContent() {
        super.onBindViewContent()
        ToolBarHelper.newBuilder(view, R.id.tool_bar, object : ToolBarHelper.ConfigCallbackAdapter() {
            override fun onConfigBefore(toolbar: Toolbar) {
                super.onConfigBefore(toolbar)
                (activity as AppCompatActivity).setSupportActionBar(mToolbar)
            }
        })
                .withNavigationIconDrawable(R.drawable.ic_action_back)
                .withNavigationIconOnClick { activity!!.onBackPressed() }.build()
        ImmersionBar.with(this).titleBar(mToolbar)
        mPageAdapter = PageFragmentStateAdapter(childFragmentManager)
        mViewPager.adapter = mPageAdapter
        //Tab较多，要切换为可混动的模式
        mTabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        mTabLayout.setupWithViewPager(mViewPager)
    }

    override fun onLayoutAfter() {
        super.onLayoutAfter()
        presenter.requestSearchCategoryList()
    }

    override fun onCreatePresenter(): MutableList<IPresenter<GankSearchContract.View>> {
        return mutableListOf(presenter)
    }

    override fun showError(throwable: Throwable) {

    }

    override fun allocSearchCategory(categoryList: List<GankSearchCategory>) {
        val presenter = presenter as BasePresenter<*>?
        //顶部Tab数据
        val tabDisposable = Observable
                .fromIterable(categoryList)
                .map { gankSearchCategory -> gankSearchCategory.category }
                .subscribe { category ->
                    if (mTabTitles == null) {
                        mTabTitles = mutableListOf()
                    }
                    mTabTitles!!.add(category)
                }
        val fragmentDisposable = Observable.fromIterable(categoryList)
                .doOnTerminate {
                    //Title和Fragment分配好后，更新Page
                    mPageAdapter.setDatas(mFragments!!, mTabTitles!!)
                    mPageAdapter.notifyDataSetChanged()
                }
                .subscribe { gankSearchCategory ->
                    if (mFragments == null) {
                        mFragments = mutableListOf()
                    }
                    mFragments!!.add(GankSearchCategoryListFragment.newInstance(gankSearchCategory))
                }
        presenter!!.addSubscription(tabDisposable)
        presenter.addSubscription(fragmentDisposable)
    }
}