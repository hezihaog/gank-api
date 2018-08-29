package com.github.wally.base.base

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.github.wally.base.R
import com.github.wally.base.RecyclerViewHelper
import me.drakeet.multitype.MultiTypeAdapter

/**
 * Package: com.github.wally.gank.base
 * FileName: BaseMvpListFragment
 * Date: on 2018/6/18  上午12:16
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
abstract class BaseMvpListFragment<V : IView> : BaseMvpFragment<V>(), ListLayoutCallback {
    protected val refreshLayout: SwipeRefreshLayout
        get() {
            return getViewFinder().findView(R.id.base_refresh_layout)
        }
    protected val recyclerView: RecyclerView
        get() {
            return getViewFinder().findView(R.id.base_list)
        }
    protected val adapter: MultiTypeAdapter by lazy {
        MultiTypeAdapter()
    }
    protected lateinit var recyclerViewHelper: RecyclerViewHelper

    override fun onLayoutId(): Int {
        return R.layout.fragment_base_list
    }

    override fun onBindViewContent() {
        super.onBindViewContent()
        //配置RecyclerView
        setupRecyclerView(recyclerView)
        //配置加载帮助类，封装下拉刷新和加载更多
        setupRecyclerViewHelper()
        //通知子类加载帮助类已经初始化完毕，可做一些滚动监听
        onRecyclerViewHelperReady(recyclerViewHelper)
    }

    /**
     * 配置RecyclerView
     */
    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val layoutManager = onSetupRecyclerViewLayoutManager()
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        //配置多type的映射
        onRegisterRecyclerViewTypeMapper(adapter)
        //通知子类列表已经初始化完毕，可以对列表进行下一步自定义，例如增加分割线等
        onRecyclerViewReady(recyclerView)
    }

    /**
     * 子类复写返回列表的LayoutManager
     */
    protected abstract fun onSetupRecyclerViewLayoutManager(): RecyclerView.LayoutManager

    /**
     * 注册列表的类型映射
     *
     * @param adapter 列表适配器
     */
    protected abstract fun onRegisterRecyclerViewTypeMapper(adapter: MultiTypeAdapter)

    /**
     * 列表初始化完毕的回调
     */
    protected open fun onRecyclerViewReady(recyclerView: RecyclerView?) {

    }

    /**
     * 配置加载帮助类，封装下拉刷新和加载更多
     */
    private fun setupRecyclerViewHelper() {
        //设置下拉刷新和加载更多
        recyclerViewHelper = onSetupRecyclerViewHelper(refreshLayout, recyclerView, adapter)
    }

    protected abstract fun onSetupRecyclerViewHelper(refreshLayout: SwipeRefreshLayout,
                                                     recyclerView: RecyclerView,
                                                     adapter: MultiTypeAdapter): RecyclerViewHelper

    /**
     * 加载帮助类初始化完毕，子类复写可设置一些滚动监听
     */
    protected open fun onRecyclerViewHelperReady(recyclerViewHelper: RecyclerViewHelper) {

    }
}