package com.github.wally.mvp.http;

/**
 * Package: com.github.wally.mvp.http
 * FileName: BaseDataSource
 * Date: on 2018/6/23  上午8:21
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class BaseDataSource<T> implements IDataSource<T> {
    private T mData;
    private boolean mHasNext = true;

    public BaseDataSource(T data) {
        this.mData = data;
    }

    public BaseDataSource(T data, boolean hasNext) {
        this.mData = data;
        this.mHasNext = hasNext;
    }

    @Override
    public T getData() {
        return mData;
    }

    public void setHasNext(boolean hasNext) {
        this.mHasNext = hasNext;
    }

    @Override
    public boolean hasNext() {
        return mHasNext;
    }
}