package com.github.wally.mvp.base;

/**
 * Package: com.github.wally.mvp.base
 * FileName: IBaseView
 * Date: on 2018/6/14  下午11:55
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public interface IBaseView {
    void showLoading();

    void hideLoading();

    void showError(Throwable throwable);
}