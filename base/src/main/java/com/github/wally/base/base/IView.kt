package com.github.wally.base.base

/**
 * Package: com.github.wally.gank.base
 * FileName: IBaseView
 * Date: on 2018/6/14  下午11:55
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
interface IView {
    /**
     * 显示加载中
     */
    fun showLoading(msg: String? = "")

    /**
     * 隐藏加载中
     */
    fun hideLoading()

    /**
     * 显示错误
     */
    fun showError(throwable: Throwable)
}