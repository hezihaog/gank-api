package com.github.wally.mvp.mvp.view

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import com.github.wally.mvp.R
import com.github.wally.mvp.adapter.PageFragmentStateAdapter
import com.github.wally.mvp.base.BaseMvpFragment
import com.github.wally.mvp.base.BasePresenter
import com.github.wally.mvp.enums.GankSearchCategory
import com.github.wally.mvp.mvp.contract.GankSearchContract
import com.github.wally.mvp.mvp.presenter.GankSearchPresenter
import com.github.wally.mvp.util.ToolBarHelper
import com.gyf.barlibrary.ImmersionBar
import io.reactivex.Observable
import java.util.*

/**
 * Package: com.github.wally.mvp.ui.fragment
 * FileName: SearchFragment
 * Date: on 2018/6/23  下午10:40
 * Auther: zihe
 * Descirbe:搜索页面
 * Email: hezihao@linghit.com
 */
class GankSearchFragment : BaseMvpFragment<GankSearchContract.Presenter, GankSearchContract.View>(), GankSearchContract.View {
    private var mToolbar: Toolbar? = null
    private var mTabLayout: TabLayout? = null
    private var mViewPager: ViewPager? = null

    private var mTabTitles: MutableList<String>? = null
    private var mFragments: MutableList<Fragment>? = null
    private var mPageAdapter: PageFragmentStateAdapter? = null

    override fun setupSwipeBackEnable(): Boolean {
        return true
    }

    override fun onLayoutId(): Int {
        return R.layout.fragment_gank_search
    }

    override fun onFindView(rootView: View) {
        super.onFindView(rootView)
        mToolbar = findView(R.id.tool_bar)
        mTabLayout = findView(R.id.tab_layout)
        mViewPager = findView(R.id.view_pager)
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
        ImmersionBar.with(this).titleBar(mToolbar!!)
        mPageAdapter = PageFragmentStateAdapter(childFragmentManager)
        mViewPager!!.adapter = mPageAdapter
        //Tab较多，要切换为可混动的模式
        mTabLayout!!.tabMode = TabLayout.MODE_SCROLLABLE
        mTabLayout!!.setupWithViewPager(mViewPager)
    }

    override fun onLayoutAfter() {
        super.onLayoutAfter()
        presenter!!.requestSearchCategoryList()
    }

    override fun onCreatePresenter(): GankSearchContract.Presenter {
        return GankSearchPresenter()
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
                        mTabTitles = ArrayList()
                    }
                    mTabTitles!!.add(category)
                }
        val fragmentDisposable = Observable.fromIterable(categoryList)
                .doOnTerminate {
                    //Title和Fragment分配好后，更新Page
                    mPageAdapter!!.setDatas(mFragments!!, mTabTitles!!)
                    mPageAdapter!!.notifyDataSetChanged()
                }
                .subscribe { gankSearchCategory ->
                    if (mFragments == null) {
                        mFragments = ArrayList()
                    }
                    mFragments!!.add(GankSearchCategoryListFragment.newInstance(gankSearchCategory))
                }
        presenter!!.addSubscription(tabDisposable)
        presenter.addSubscription(fragmentDisposable)
    }
}