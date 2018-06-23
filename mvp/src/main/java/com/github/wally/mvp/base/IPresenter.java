package com.github.wally.mvp.base;

/**
 * Package: com.github.wally.mvp.base
 * FileName: IPresenter
 * Date: on 2018/6/14  下午11:56
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public interface IPresenter<V extends IBaseView> {
    /**
     * 给Presenter注释V层视图
     */
    void attachView(V view);

    /**
     * 给Presenter移除V层视图
     */
    void detachView();
}