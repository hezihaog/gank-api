package com.github.wally.mvp.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.wally.mvp.util.WaitDialogHelper;

/**
 * Package: com.github.wally.mvp.base
 * FileName: BaseFragment
 * Date: on 2018/6/17  上午8:52
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public abstract class BaseMvpFragment<P extends IPresenter<V>, V extends IBaseView>
        extends BaseRxFragment implements IBaseView {
    protected P mPresenter;
    private WaitDialogHelper mWaitDialogHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = onCreatePresenter();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mWaitDialogHelper = WaitDialogHelper.create(activity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);
        //设置为true才能回调onCreateOptionsMenu
        setHasOptionsMenu(true);
        return attachToSwipeBack(layout);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.attachView((V) this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    /**
     * 创建Presenter回调
     */
    protected abstract P onCreatePresenter();

    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public void showLoading() {
        if (mWaitDialogHelper != null) {
            mWaitDialogHelper.showWaitDialog();
        }
    }

    @Override
    public void hideLoading() {
        if (mWaitDialogHelper != null) {
            mWaitDialogHelper.hideWaitDialog();
        }
    }
}