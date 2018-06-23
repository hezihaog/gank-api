package com.github.wally.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.bumptech.glide.Glide;
import com.github.wally.mvp.R;
import com.github.wally.mvp.base.BaseMvpFragment;
import com.github.wally.mvp.base.BasePresenter;
import com.github.wally.mvp.bean.gank.DisplayMeiZiImageBean;
import com.github.wally.mvp.http.IoToMainScheduler;
import com.github.wally.mvp.mvp.contract.GankMeiZiDetailContract;
import com.github.wally.mvp.mvp.presenter.GankMeiZiDetailPresenter;
import com.gyf.barlibrary.ImmersionBar;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
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
    private boolean isHideToolBar = false;

    @Override
    public int onLayoutId() {
        return R.layout.fragment_gank_meizi_detail;
    }

    @Override
    public void onFindView(View rootView) {
        super.onFindView(rootView);
        mToolbar = getView().findViewById(R.id.tool_bar);
        mImageView = getView().findViewById(R.id.image_iv);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Disposable disposable = Observable
                .timer(500, TimeUnit.MILLISECONDS)
                .compose(new IoToMainScheduler<Long>())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        toggleToolBar();
                    }
                });
        ((BasePresenter)mPresenter).addSubscription(disposable);
    }

    @Override
    public void onBindViewContent() {
        super.onBindViewContent();
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
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
        if (!isHideToolBar) {
            mToolbar.animate()
                    .translationY(-mToolbar.getHeight())
                    .setInterpolator(new AccelerateInterpolator())
                    .start();
        } else {
            mToolbar.animate()
                    .translationY(0)
                    .setInterpolator(new AccelerateInterpolator())
                    .start();
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
        mPresenter.getMeiZiDetail(getArguments());
    }

    @Override
    public void showMeiZiDetail(DisplayMeiZiImageBean bean) {
        Glide.with(getActivity()).load(bean.getUrl()).into(mImageView);
    }

    @Override
    public void showError(Throwable throwable) {

    }
}
