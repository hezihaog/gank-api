package com.github.wally.gank.mvp.view

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.github.wally.base.RecyclerViewHelper
import com.github.wally.base.base.BaseMvpListFragment
import com.github.wally.base.base.IPresenter
import com.github.wally.base.http.IDataSource
import com.github.wally.base.widget.recyclerview.manager.delegate.FastScrollDelegate
import com.github.wally.gank.bean.gank.GankSearchBean
import com.github.wally.gank.constants.Constants
import com.github.wally.gank.enums.GankSearchCategory
import com.github.wally.gank.mvp.contract.GankSearchCategoryListContract
import com.github.wally.gank.mvp.presenter.GankSearchCategoryListPresenter
import com.github.wally.gank.viewbinder.GankSearchAllCategoryViewBinder
import me.drakeet.multitype.MultiTypeAdapter

/**
 * Package: com.github.wally.gank.ui.fragment
 * FileName: GankSearchCategoryListFragment
 * Date: on 2018/6/23  下午11:34
 * Auther: zihe
 * Descirbe:搜索页面不同的类型的搜索结果列表界面
 * Email: hezihao@linghit.com
 */
class GankSearchCategoryListFragment : BaseMvpListFragment<GankSearchCategoryListContract.View>(), GankSearchCategoryListContract.View {
    private val presenter:GankSearchCategoryListContract.Presenter by lazy {
        GankSearchCategoryListPresenter()
    }
    private val mCategory: GankSearchCategory by lazy {
        arguments!!.getSerializable(KEY_SEARCH_CATEGORY) as GankSearchCategory
    }

    override fun onLayoutAfter() {
        super.onLayoutAfter()
        recyclerViewHelper.startRefreshWithLoading()
    }

    override fun onCreatePresenter(): MutableList<IPresenter<GankSearchCategoryListContract.View>> {
        return mutableListOf(presenter)
    }

    override fun onSetupRecyclerViewLayoutManager(): RecyclerView.LayoutManager {
        return object : LinearLayoutManager(activity) {
            override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?, position: Int) {
                FastScrollDelegate().smoothScrollToPosition(recyclerView, this, position)
            }
        }
    }

    override fun onRecyclerViewReady(recyclerView: RecyclerView?) {
        super.onRecyclerViewReady(recyclerView)
        recyclerView!!.addItemDecoration(DividerItemDecoration(activity!!, RecyclerView.VERTICAL))
    }

    override fun onRegisterRecyclerViewTypeMapper(adapter: MultiTypeAdapter) {
        adapter.register(GankSearchBean.Msg::class.java)
                .to(GankSearchAllCategoryViewBinder())
                .withClassLinker { position, msg ->
                    //                if (GankSearchCategory.ALL.equals(msg.getType())) {
                    //                    return GankSearchAllCategoryViewBinder.class;
                    //                }
                    //                return null;
                    // TODO: 2018/6/24 后续加上类型分类的不同条目类
                    GankSearchAllCategoryViewBinder::class.java
                }
    }

    override fun onSetupRecyclerViewHelper(refreshLayout: SwipeRefreshLayout, recyclerView: RecyclerView, adapter: MultiTypeAdapter): RecyclerViewHelper {
        return RecyclerViewHelper.create(refreshLayout, recyclerView, adapter, object : RecyclerViewHelper.OnLoadListener {
            override fun onSwipeRefresh(page: Int, isFirst: Boolean) {
                presenter.getSearchCategoryList(page, Constants.Config.size, mCategory!!, true)
            }

            override fun onLoadMore(page: Int, isFirst: Boolean) {
                presenter.getSearchCategoryList(page, Constants.Config.size, mCategory!!, false)
            }
        })
    }

    override fun showSearchCategoryList(dataSource: IDataSource<GankSearchBean>, isRefresh: Boolean) {
        recyclerViewHelper.updateDataSource(isRefresh, dataSource.data.results, dataSource.hasNext())
    }

    override fun showError(throwable: Throwable) {

    }

    companion object {
        private val KEY_SEARCH_CATEGORY = "key_search_category"

        fun newInstance(category: GankSearchCategory): GankSearchCategoryListFragment {
            val args = Bundle()
            args.putSerializable(KEY_SEARCH_CATEGORY, category)
            val fragment = GankSearchCategoryListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}