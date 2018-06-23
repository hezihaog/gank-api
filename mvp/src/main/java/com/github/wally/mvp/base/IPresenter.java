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
    void attachView(V view);

    void detachView();
}