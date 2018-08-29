package com.github.wally.gank.mvp.view

import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.github.wally.base.base.BaseRxFragment
import com.github.wally.base.util.ToolBarHelper
import com.github.wally.gank.R
import com.github.wally.gank.ext.findView
import com.github.wally.gank.util.UIHelper
import com.gyf.barlibrary.ImmersionBar
import me.yokeyword.fragmentation.SupportActivity

/**
 * Package: com.github.wally.gank.ui.fragment
 * FileName: MainFragment
 * Date: on 2018/6/23  下午2:24
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class MainFragment : BaseRxFragment() {
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mNavigationView: NavigationView

    override fun onLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun onFindView(rootView: View) {
        super.onFindView(rootView)
        mDrawerLayout = findView(R.id.drawer_layout)
        mNavigationView = findView(R.id.navigation_view)
    }

    override fun onBindViewContent() {
        super.onBindViewContent()
        val toolBarHelper = ToolBarHelper.newBuilder(view, R.id.tool_bar, object : ToolBarHelper.ConfigCallbackAdapter() {
            override fun onConfigBefore(toolbar: Toolbar) {
                super.onConfigBefore(toolbar)
                (activity as AppCompatActivity).setSupportActionBar(toolbar)
            }
        })
                .withNavigationIconDrawable(R.drawable.ic_action_menu)
                .withNavigationIconOnClick { mDrawerLayout.openDrawer(GravityCompat.START) }
                .build()
        //透明状态栏适配ToolBar
        ImmersionBar.with(this@MainFragment).titleBar(toolBarHelper.toolbar)
        mNavigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.random_mei_zi -> UIHelper.showRandomMeiZiList((activity as SupportActivity?)!!)
                else -> {
                }
            }
            mDrawerLayout.closeDrawer(GravityCompat.START)
            false
        }
        loadRootFragment(R.id.main_content, GankMeiZiListFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.tool_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item.let {
            when (it!!.itemId) {
                R.id.menu_search -> {
                    UIHelper.showSearchCategory((activity as SupportActivity?)!!)
                    return true
                }
                R.id.random_mei_zi -> {
                    UIHelper.showRandomMeiZiList((activity as SupportActivity?)!!)
                    return true
                }
                else -> {
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressedSupport(): Boolean {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START)
            return true
        } else {
            return super.onBackPressedSupport()
        }
    }
}