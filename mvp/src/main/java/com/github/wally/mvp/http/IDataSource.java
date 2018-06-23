package com.github.wally.mvp.http;

/**
 * Package: com.github.wally.mvp.http
 * FileName: IDataSource
 * Date: on 2018/6/18  下午12:49
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public interface IDataSource<T> {
    T getData();

    boolean hasNext();
}