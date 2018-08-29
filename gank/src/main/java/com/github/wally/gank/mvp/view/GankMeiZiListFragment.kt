package com.github.wally.gank.mvp.view

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View

import com.github.wally.base.RecyclerViewHelper
import com.github.wally.base.RecyclerViewScrollHelper
import com.github.wally.base.base.BaseMvpListFragment
import com.github.wally.base.http.IDataSource
import com.github.wally.base.util.PositionUtil
import com.github.wally.base.widget.recyclerview.manager.delegate.FastScrollDelegate
import com.github.wally.gank.R
import com.github.wally.gank.bean.gank.GankMeiZiListBean
import com.github.wally.gank.mvp.contract.GankMeiZiListContract
import com.github.wally.gank.mvp.presenter.GankMeiZiListPresenter
import com.github.wally.gank.viewbinder.GankMeiZiViewBinder

import me.drakeet.multitype.MultiTypeAdapter

/**
 * Package: com.github.wally.gank.ui.fragment
 * FileName: GankMeiziFragment
 * Date: on 2018/6/16  上午10:35
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class GankMeiZiListFragment : BaseMvpListFragment<GankMeiZiListContract.Presenter, GankMeiZiListContract.View>(), GankMeiZiListContract.View {
    companion object {
        private var KEY = "key_position"
    }

    private var mLastListPosition: Int = 0
    private lateinit var mFloatingActionButton: FloatingActionButton

    override fun onLayoutId(): Int {
        return R.layout.fragment_gank_mei_zi_list
    }

    override fun onFindView(rootView: View) {
        super.onFindView(rootView)
        mFloatingActionButton = findView(R.id.floating_action_btn)
    }

    override fun onCreatePresenter(): GankMeiZiListContract.Presenter {
        return GankMeiZiListPresenter()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY, mLastListPosition)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            mLastListPosition = savedInstanceState.getInt(KEY)
            recyclerView!!.scrollToPosition(mLastListPosition)
        }
    }

    override fun onBindViewContent() {
        super.onBindViewContent()
        mFloatingActionButton.setOnClickListener { recyclerViewHelper.smoothMoveToTop() }
    }

    override fun onLayoutAfter() {
        super.onLayoutAfter()
        recyclerViewHelper.startRefresh()
    }

    override fun onSetupRecyclerViewLayoutManager(): RecyclerView.LayoutManager {
        return object : StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) {
            override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?, position: Int) {
                FastScrollDelegate().smoothScrollToPosition(recyclerView, this, position)
            }
        }
    }

    override fun onRecyclerViewReady(recyclerView: RecyclerView?) {
        super.onRecyclerViewReady(recyclerView)
        val layoutManager = recyclerView!!.layoutManager as StaggeredGridLayoutManager
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
    }

    override fun onRegisterRecyclerViewTypeMapper(adapter: MultiTypeAdapter) {
        adapter.register<GankMeiZiListBean.MeiZi>(GankMeiZiListBean.MeiZi::class.java, GankMeiZiViewBinder())
    }

    override fun onRecyclerViewHelperReady(recyclerViewHelper: RecyclerViewHelper) {
        super.onRecyclerViewHelperReady(recyclerViewHelper)
        //滚动监听
        recyclerViewHelper!!.addScrollListener(object : RecyclerViewScrollHelper.SimpleScrollAdapter() {

            override fun onScrolledToUp() {
                super.onScrolledToUp()
                mFloatingActionButton!!.hide()
            }

            override fun onScrolledToDown() {
                super.onScrolledToDown()
                mFloatingActionButton!!.show()
            }
        })
        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (recyclerView != null) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //停止时，保存位置
                        mLastListPosition = PositionUtil.getCurrentPosition(recyclerView)
                        (recyclerView.layoutManager as StaggeredGridLayoutManager)
                                .invalidateSpanAssignments()
                    }
                }
            }
        })
    }

    override fun onSetupRecyclerViewHelper(refreshLayout: SwipeRefreshLayout, recyclerView: RecyclerView, adapter: MultiTypeAdapter): RecyclerViewHelper {
        //设置下拉刷新和加载更多
        return RecyclerViewHelper.create(refreshLayout, recyclerView, adapter, object : RecyclerViewHelper.OnLoadListener {
            override fun onSwipeRefresh(page: Int, isFirst: Boolean) {
                presenter.getMeiZiList(page, 15, true)
            }

            override fun onLoadMore(page: Int, isFirst: Boolean) {
                presenter.getMeiZiList(page, 15, false)
            }
        })
    }

    override fun showMeiZiList(meiziGankBean: IDataSource<GankMeiZiListBean>, isRefresh: Boolean) {
        //必须调用设置数据集的方法进行更新数据
        recyclerViewHelper!!.updateDataSource(isRefresh, meiziGankBean.data.results, meiziGankBean.hasNext())
    }

    override fun showError(throwable: Throwable) {

    }
}
