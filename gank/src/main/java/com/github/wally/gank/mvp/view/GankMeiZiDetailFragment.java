package com.github.wally.gank.mvp.view;

import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.github.wally.base.util.ImageDisplayUtil;
import com.github.wally.base.util.StatusBarUtil;
import com.github.wally.gank.R;
import com.github.wally.base.base.BaseMvpFragment;
import com.github.wally.base.base.BasePresenter;
import com.github.wally.gank.bean.gank.DisplayMeiZiImageBean;
import com.github.wally.gank.mvp.contract.GankMeiZiDetailContract;
import com.github.wally.gank.mvp.presenter.GankMeiZiDetailPresenter;
import com.github.wally.base.util.ImageDownloadUtil;
import com.github.wally.base.util.ToolBarHelper;
import com.github.wally.gank.widget.RotateCircleProgressBar;
import com.gyf.barlibrary.ImmersionBar;
import com.haoge.easyandroid.easy.EasyToast;

import java.io.File;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Package: com.github.wally.gank.ui.fragment
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
    private DisplayMeiZiImageBean mImageBean;

    @Override
    public void onDestroy() {
        StatusBarUtil.showStatusBar(getActivity());
        super.onDestroy();
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
        this.mImageBean = bean;
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

    /**
     * 保存图片到相册
     */
    private void saveImageToGallery() {
        if (mImageBean != null) {
            Disposable disposable = ImageDownloadUtil
                    .saveImageAndGetPathObservable(getActivity(), mImageBean.getUrl(), mImageBean.getCreatedAt())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            showLoading("保存中，请稍后...");
                        }
                    })
                    .doOnTerminate(new Action() {
                        @Override
                        public void run() throws Exception {
                            hideLoading();
                        }
                    })
                    .subscribe(new Consumer<Uri>() {
                        @Override
                        public void accept(Uri uri) throws Exception {
                            File appDir = new File(Environment.getExternalStorageDirectory(), "Meizhi");
                            String msg = String.format(getString(R.string.picture_has_save_to),
                                    appDir.getAbsolutePath());
                            EasyToast.Companion.newBuilder().build().show(msg);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            EasyToast.Companion.newBuilder().build().show("再试试...");
                        }
                    });
            ((BasePresenter)getPresenter()).addSubscription(disposable);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                return true;
            case R.id.action_save:
                saveImageToGallery();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
