package com.github.wally.base.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.wally.base.util.WaitDialogHelper


/**
 * Package: com.github.wally.gank.base
 * FileName: BaseFragment
 * Date: on 2018/6/17  上午8:52
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
abstract class BaseMvpFragment<P : IPresenter<V>, V : IBaseView> : BaseRxFragment(), IBaseView {
    protected lateinit var presenter: P
    protected lateinit var mWaitDialogHelper: WaitDialogHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = onCreatePresenter()
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        mWaitDialogHelper = WaitDialogHelper.create(activity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = super.onCreateView(inflater, container, savedInstanceState)
        return attachToSwipeBack(layout)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.attachView(this as V)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.let {
            it.detachView()
        }
    }

    /**
     * 创建Presenter回调
     */
    protected abstract fun onCreatePresenter(): P

    override fun showLoading(msg: String?) {
        mWaitDialogHelper.let {
            it.showWaitDialog(msg)
        }
    }

    override fun hideLoading() {
        mWaitDialogHelper.let {
            it.hideWaitDialog()
        }
    }
}