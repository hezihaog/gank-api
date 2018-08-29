package com.github.wally.gank.mvp.view

import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.github.wally.base.RecyclerViewHelper
import com.github.wally.base.RecyclerViewScrollHelper
import com.github.wally.base.base.BaseMvpListFragment
import com.github.wally.base.http.IDataSource
import com.github.wally.base.util.ToolBarHelper
import com.github.wally.base.widget.recyclerview.manager.delegate.FastScrollDelegate
import com.github.wally.gank.R
import com.github.wally.gank.bean.gank.GankRandomListBean
import com.github.wally.gank.enums.GankRandomCategory
import com.github.wally.gank.ext.findView
import com.github.wally.gank.mvp.contract.RandomMeiZiListContract
import com.github.wally.gank.mvp.presenter.GankRandomMeiZiListPresenter
import com.github.wally.gank.viewbinder.GankRandomMeiZiViewBinder
import com.gyf.barlibrary.ImmersionBar
import me.drakeet.multitype.MultiTypeAdapter


/**
 * Package: com.github.wally.gank.ui.fragment
 * FileName: RandomMeiZiFragment
 * Date: on 2018/6/18  上午10:18
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class RandomMeiZiFragment : BaseMvpListFragment<RandomMeiZiListContract.Presenter, RandomMeiZiListContract.View>(), RandomMeiZiListContract.View {
    private lateinit var mRefreshBtn: FloatingActionButton

    override fun setupSwipeBackEnable(): Boolean {
        return true
    }

    override fun onLayoutId(): Int {
        return R.layout.fragment_gank_random_mei_zi_list
    }

    override fun onLayoutAfter() {
        super.onLayoutAfter()
        recyclerViewHelper.startRefresh()
    }

    override fun onFindView(rootView: View) {
        super.onFindView(rootView)
        mRefreshBtn = findView(R.id.refresh_action_btn)
    }

    override fun onBindViewContent() {
        super.onBindViewContent()
        val toolBarHelper = ToolBarHelper.newBuilder(view, R.id.tool_bar, object : ToolBarHelper.ConfigCallbackAdapter() {
            override fun onConfigBefore(toolbar: Toolbar) {
                super.onConfigBefore(toolbar)
                (activity as AppCompatActivity).setSupportActionBar(toolbar)
            }
        })
                .withNavigationIconDrawable(R.drawable.ic_action_back)
                .withNavigationIconOnClick { activity!!.onBackPressed() }
                .build()
        val toolbar = toolBarHelper.toolbar
        ImmersionBar.with(this).titleBar(toolbar)
        mRefreshBtn!!.setOnClickListener {
            recyclerViewHelper.moveToTop()
            recyclerViewHelper.startRefreshWithLoading()
        }
    }

    override fun onSetupRecyclerViewLayoutManager(): RecyclerView.LayoutManager {
        return object : StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) {
            override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?, position: Int) {
                FastScrollDelegate().smoothScrollToPosition(recyclerView, this, position)
            }
        }
    }

    override fun onRegisterRecyclerViewTypeMapper(adapter: MultiTypeAdapter) {
        adapter.register(GankRandomListBean.MeiZi::class.java)
                .to(GankRandomMeiZiViewBinder())
                .withClassLinker { position, meiZi ->
                    if (GankRandomCategory.MEI_ZI.category == meiZi.type) {
                        GankRandomMeiZiViewBinder::class.java
                    } else {
                        return@withClassLinker null!!
                    }
                }
    }

    override fun onSetupRecyclerViewHelper(refreshLayout: SwipeRefreshLayout, recyclerView: RecyclerView, adapter: MultiTypeAdapter): RecyclerViewHelper {
        return RecyclerViewHelper.create(refreshLayout, recyclerView, adapter, object : RecyclerViewHelper.OnLoadListener {
            override fun onSwipeRefresh(page: Int, isFirst: Boolean) {
                presenter.getRandomMeiZiList(GankRandomCategory.MEI_ZI, 15, true)
            }

            override fun onLoadMore(page: Int, isFirst: Boolean) {
                presenter.getRandomMeiZiList(GankRandomCategory.MEI_ZI, 15, false)
            }
        })
    }

    override fun onRecyclerViewHelperReady(recyclerViewHelper: RecyclerViewHelper) {
        super.onRecyclerViewHelperReady(recyclerViewHelper)
        recyclerViewHelper.addScrollListener(object : RecyclerViewScrollHelper.SimpleScrollAdapter() {
            override fun onScrolledToUp() {
                super.onScrolledToUp()
                mRefreshBtn.hide()
            }

            override fun onScrolledToDown() {
                super.onScrolledToDown()
                mRefreshBtn.show()
            }
        })
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                recyclerView.let {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //停止时，保存位置
                        (it!!.getLayoutManager() as StaggeredGridLayoutManager)
                                .invalidateSpanAssignments()
                    }
                }
            }
        })
    }

    override fun onCreatePresenter(): RandomMeiZiListContract.Presenter {
        return GankRandomMeiZiListPresenter()
    }

    override fun onRecyclerViewReady(recyclerView: RecyclerView?) {
        super.onRecyclerViewReady(recyclerView)
        recyclerView.let {
            val layoutManager = it!!.layoutManager as StaggeredGridLayoutManager
            layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        }
    }

    override fun showError(throwable: Throwable) {

    }

    override fun showRandomMeiZiList(bean: IDataSource<GankRandomListBean>, isRefresh: Boolean) {
        recyclerViewHelper.updateDataSource(isRefresh, bean.data.results, bean.hasNext())
    }
}