package com.github.wally.base.base

/**
 * Package: com.github.wally.gank.base
 * FileName: IPresenter
 * Date: on 2018/6/14  下午11:56
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
interface IPresenter<V : IView> {
    /**
     * 给Presenter注释V层视图
     */
    fun attachView(view: V)

    /**
     * 给Presenter移除V层视图
     */
    fun detachView()
}