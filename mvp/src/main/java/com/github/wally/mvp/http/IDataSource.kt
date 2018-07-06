package com.github.wally.mvp.http

/**
 * Package: com.github.wally.mvp.http
 * FileName: IDataSource
 * Date: on 2018/6/18  下午12:49
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
interface IDataSource<T> {
    /**
     * 获取列表数据
     */
    val data: T

    /**
     * 是否下一页
     */
    operator fun hasNext(): Boolean
}