package com.github.wally.mvp.ui.fragment;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.github.wally.base.util.ImageDisplayUtil;
import com.github.wally.base.util.StatusBarUtil;
import com.github.wally.mvp.R;
import com.github.wally.mvp.base.BaseMvpFragment;
import com.github.wally.mvp.bean.gank.DisplayMeiZiImageBean;
import com.github.wally.mvp.mvp.contract.GankMeiZiDetailContract;
import com.github.wally.mvp.mvp.presenter.GankMeiZiDetailPresenter;
import com.github.wally.mvp.util.ToolBarHelper;
import com.github.wally.mvp.widget.RotateCircleProgressBar;
import com.gyf.barlibrary.ImmersionBar;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Package: com.github.wally.mvp.ui.fragment
 * FileName: GankMeiZiDetailFragment
 * Date: on 2018/6/16  下午6:42
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankMeiZiDetailFragment extends BaseMvpFragment<GankMeiZiDetailContract.Presenter, GankMeiZiDetailContract.View> implements GankMeiZiDetailContract.View {
    private PhotoView mImageView;
    private Toolbar mToolbar;
    private RotateCircleProgressBar mLoadingView;

    private boolean isHideToolBar = false;

    @Override
    public void onDestroy() {
        super.onDestroy();
        StatusBarUtil.showStatusBar(getActivity());
    }

    @Override
    public int onLayoutId() {
        return R.layout.fragment_gank_meizi_detail;
    }

    @Override
    public void onFindView(View rootView) {
        super.onFindView(rootView);
        mImageView = findView(R.id.image_iv);
        mLoadingView = findView(R.id.loading_iv);
    }

    @Override
    public void onBindViewContent() {
        super.onBindViewContent();
        ToolBarHelper toolBarHelper = ToolBarHelper.newBuilder(getView(), R.id.tool_bar, new ToolBarHelper.ConfigCallbackAdapter() {
            @Override
            public void onConfigBefore(Toolbar toolbar) {
                ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            }
        })
                .withNavigationIconDrawable(R.drawable.ic_action_back)
                .withNavigationIconOnClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().onBackPressed();
                    }
                })
                .build();
        mToolbar = toolBarHelper.getToolbar();
        ImmersionBar.with(this).titleBar(mToolbar);
        //长按点击事件
        mImageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                toggleToolBar();
            }

            @Override
            public void onOutsidePhotoTap() {
            }
        });
    }

    private void toggleToolBar() {
        //隐藏
        if (!isHideToolBar) {
            mToolbar.animate()
                    .translationY(-mToolbar.getHeight())
                    .setInterpolator(new AccelerateInterpolator())
                    .start();
            StatusBarUtil.hideStatusBar(getActivity());
        } else {
            //显示
            mToolbar.animate()
                    .translationY(0)
                    .setInterpolator(new AccelerateInterpolator())
                    .start();
            StatusBarUtil.showStatusBar(getActivity());
        }
        isHideToolBar = !isHideToolBar;
    }

    @Override
    protected boolean setupSwipeBackEnable() {
        return true;
    }

    @Override
    protected GankMeiZiDetailContract.Presenter onCreatePresenter() {
        return new GankMeiZiDetailPresenter();
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        getPresenter().getMeiZiDetail(getArguments());
    }

    @Override
    public void showMeiZiDetail(DisplayMeiZiImageBean bean) {
        mLoadingView.setVisibility(View.VISIBLE);
        ImageDisplayUtil.display(getActivity(), bean.getUrl(), mImageView
                , new ImageDisplayUtil.DisplayCallbackAdapter() {
                    @Override
                    public void onReady() {
                        super.onReady();
                        mLoadingView.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void showError(Throwable throwable) {

    }
}
