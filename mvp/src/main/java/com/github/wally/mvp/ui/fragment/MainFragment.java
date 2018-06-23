package com.github.wally.mvp.ui.fragment;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.github.wally.mvp.R;
import com.github.wally.mvp.base.BaseRxFragment;
import com.github.wally.mvp.util.ToolBarHelper;
import com.github.wally.mvp.util.UIHelper;
import com.gyf.barlibrary.ImmersionBar;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Package: com.github.wally.mvp.ui.fragment
 * FileName: MainFragment
 * Date: on 2018/6/23  下午2:24
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class MainFragment extends BaseRxFragment {
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    public int onLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onFindView(View rootView) {
        super.onFindView(rootView);
        mDrawerLayout = findView(R.id.drawer_layout);
        mNavigationView = findView(R.id.navigation_view);
    }

    @Override
    public void onBindViewContent() {
        super.onBindViewContent();
        ToolBarHelper toolBarHelper = ToolBarHelper.newBuilder(getView(), R.id.tool_bar)
                .withNavigationIconDrawable(R.drawable.ic_action_menu)
                .withNavigationIconOnClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDrawerLayout.openDrawer(GravityCompat.START);
                    }
                })
                .build();
        Toolbar toolbar = toolBarHelper.getToolbar();
        //透明状态栏适配ToolBar
        ImmersionBar.with(this).titleBar(toolbar);
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
        loadRootFragment(R.id.main_content, new GankMeiZiListFragment());
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