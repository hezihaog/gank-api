package com.github.wally.gank.mvp.view

import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.AccelerateInterpolator
import com.github.chrisbanes.photoview.PhotoView
import com.github.wally.base.base.BaseMvpFragment
import com.github.wally.base.base.BasePresenter
import com.github.wally.base.util.ImageDisplayUtil
import com.github.wally.base.util.ImageDownloadUtil
import com.github.wally.base.util.ToolBarHelper
import com.github.wally.gank.R
import com.github.wally.gank.bean.gank.DisplayMeiZiImageBean
import com.github.wally.gank.ext.findView
import com.github.wally.gank.ext.hideStatusBar
import com.github.wally.gank.ext.showStatusBar
import com.github.wally.gank.mvp.contract.GankMeiZiDetailContract
import com.github.wally.gank.mvp.presenter.GankMeiZiDetailPresenter
import com.github.wally.gank.widget.RotateCircleProgressBar
import com.gyf.barlibrary.ImmersionBar
import com.haoge.easyandroid.easy.EasyToast
import java.io.File

/**
 * Package: com.github.wally.gank.ui.fragment
 * FileName: GankMeiZiDetailFragment
 * Date: on 2018/6/16  下午6:42
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class GankMeiZiDetailFragment : BaseMvpFragment<GankMeiZiDetailContract.Presenter, GankMeiZiDetailContract.View>(), GankMeiZiDetailContract.View {
    private lateinit var mImageView: PhotoView
    private lateinit var mToolbar: Toolbar
    private lateinit var mLoadingView: RotateCircleProgressBar

    private var isHideToolBar = false
    private var mImageBean: DisplayMeiZiImageBean? = null

    override fun onDestroy() {
        showStatusBar()
        super.onDestroy()
    }

    override fun onLayoutId(): Int {
        return R.layout.fragment_gank_meizi_detail
    }

    override fun onFindView(rootView: View) {
        super.onFindView(rootView)
        mImageView = findView(R.id.image_iv)
        mLoadingView = findView(R.id.loading_iv)
    }

    override fun onBindViewContent() {
        super.onBindViewContent()
        val toolBarHelper = ToolBarHelper.newBuilder(view, R.id.tool_bar, object : ToolBarHelper.ConfigCallbackAdapter() {
            override fun onConfigBefore(toolbar: Toolbar) {
                (activity as AppCompatActivity).setSupportActionBar(toolbar)
            }
        })
                .withNavigationIconDrawable(R.drawable.ic_action_back)
                .withNavigationIconOnClick { activity!!.onBackPressed() }
                .build()
        mToolbar = toolBarHelper.toolbar
        ImmersionBar.with(this).titleBar(mToolbar!!)
        //长按点击事件
        mImageView.setOnPhotoTapListener { view, x, y -> toggleToolBar() }
    }

    private fun toggleToolBar() {
        //隐藏
        if (!isHideToolBar) {
            mToolbar.animate()
                    .translationY((-mToolbar.height).toFloat())
                    .setInterpolator(AccelerateInterpolator())
                    .start()
            hideStatusBar()
        } else {
            //显示
            mToolbar.animate()
                    .translationY(0f)
                    .setInterpolator(AccelerateInterpolator())
                    .start()
            showStatusBar()
        }
        isHideToolBar = !isHideToolBar
    }

    override fun setupSwipeBackEnable(): Boolean {
        return true
    }

    override fun onCreatePresenter(): GankMeiZiDetailContract.Presenter {
        return GankMeiZiDetailPresenter()
    }

    override fun onLayoutAfter() {
        super.onLayoutAfter()
        presenter.getMeiZiDetail(arguments!!)
    }

    override fun showMeiZiDetail(bean: DisplayMeiZiImageBean) {
        this.mImageBean = bean
        mLoadingView.visibility = View.VISIBLE
        ImageDisplayUtil.display(activity, bean.url, mImageView, object : ImageDisplayUtil.DisplayCallbackAdapter() {
            override fun onReady() {
                super.onReady()
                mLoadingView.visibility = View.GONE
            }
        })
    }

    override fun showError(throwable: Throwable) {

    }

    /**
     * 保存图片到相册
     */
    private fun saveImageToGallery() {
        mImageBean.let { it ->
            if (it?.url != null && it.createdAt != null) {
                val disposable = ImageDownloadUtil
                        .saveImageAndGetPathObservable(activity, mImageBean!!.url, mImageBean!!.createdAt)
                        .doOnSubscribe { showLoading("保存中，请稍后...") }
                        .doOnTerminate { hideLoading() }
                        .subscribe({
                            val appDir = File(Environment.getExternalStorageDirectory(), "Meizhi")
                            val msg = String.format(getString(R.string.picture_has_save_to),
                                    appDir.absolutePath)
                            EasyToast.newBuilder().build().show(msg)
                        }, { EasyToast.newBuilder().build().show("再试试...") })
                (presenter as BasePresenter<*>).addSubscription(disposable)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.let {
            it!!.inflate(R.menu.menu_detail, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_share -> return true
            R.id.action_save -> {
                saveImageToGallery()
                return true
            }
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
