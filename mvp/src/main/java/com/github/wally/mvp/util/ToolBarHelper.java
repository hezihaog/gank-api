package com.github.wally.mvp.util;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Package: com.github.wally.mvp.util
 * FileName: ToolBarHelper
 * Date: on 2018/6/23  下午2:38
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class ToolBarHelper {
    private View rootView;
    private ConfigCallback configCallback;
    private int toolBarId;
    private Drawable navigationIconDrawable;
    private View.OnClickListener navigationIconOnClick;
    private Toolbar toolbar;

    private ToolBarHelper() {
    }

    private ToolBarHelper(Builder builder) {
        this.rootView = builder.rootView;
        this.toolBarId = builder.toolBarId;
        this.navigationIconDrawable = builder.navigationIconDrawable;
        this.navigationIconOnClick = builder.navigationIconOnClick;
        this.configCallback = builder.configCallback;
    }

    private void apply() {
        Toolbar toolbar = this.rootView.findViewById(toolBarId);
        this.toolbar = toolbar;
        configCallback.onConfigBefore(toolbar);
        if (navigationIconDrawable != null) {
            toolbar.setNavigationIcon(navigationIconDrawable);
            toolbar.setNavigationOnClickListener(navigationIconOnClick);
        }
        configCallback.onConfigAfter(toolbar);
    }

    public static Builder newBuilder(View rootView, int toolBarId, ConfigCallback configCallback) {
        return new Builder(rootView, toolBarId, configCallback);
    }

    public static class Builder {
        /**
         * 视图总布局
         */
        private View rootView;
        /**
         * ToolBar的id
         */
        private int toolBarId;
        /**
         * 配置之前的一个回调，通常用于将ToolBar绑定在Activity的actionBar上
         */
        private ConfigCallback configCallback;
        /**
         * 左边的导航Icon图标
         */
        private Drawable navigationIconDrawable;
        /**
         * 左边导航Icon的点击事件
         */
        private View.OnClickListener navigationIconOnClick;

        public Builder(View rootView, int toolBarId, ConfigCallback configCallback) {
            this.rootView = rootView;
            this.toolBarId = toolBarId;
            this.configCallback = configCallback;
        }

        public Builder withNavigationIconDrawable(Drawable navigationIconDrawable) {
            this.navigationIconDrawable = navigationIconDrawable;
            return this;
        }

        public Builder withNavigationIconDrawable(int navigationIconDrawableId) {
            Drawable drawable = rootView.getContext().getResources().getDrawable(navigationIconDrawableId);
            return withNavigationIconDrawable(drawable);
        }

        public Builder withNavigationIconOnClick(View.OnClickListener navigationIconOnClick) {
            this.navigationIconOnClick = navigationIconOnClick;
            return this;
        }

        public ToolBarHelper build() {
            ToolBarHelper helper = new ToolBarHelper(this);
            helper.apply();
            return helper;
        }
    }

    public View getRootView() {
        return rootView;
    }

    public int getToolBarId() {
        return toolBarId;
    }

    public Drawable getNavigationIconDrawable() {
        return navigationIconDrawable;
    }

    public View.OnClickListener getNavigationIconOnClick() {
        return navigationIconOnClick;
    }

    public Toolbar getToolbar() {
        if (toolbar == null) {
            apply();
        }
        return toolbar;
    }

    /**
     * 配置回调
     */
    public interface ConfigCallback {
        /**
         * 配置开始前回调
         */
        void onConfigBefore(Toolbar toolbar);

        /**
         * 配置结束后回调
         */
        void onConfigAfter(Toolbar toolbar);
    }

    /**
     * 配置回调空实现
     */
    public static class ConfigCallbackAdapter implements ConfigCallback {

        @Override
        public void onConfigBefore(Toolbar toolbar) {

        }

        @Override
        public void onConfigAfter(Toolbar toolbar) {

        }
    }
}