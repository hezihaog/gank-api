package com.github.wally.mvp.bean.gank;

import com.github.wally.mvp.bean.Base;

/**
 * Package: com.github.wally.mvp.base.gank
 * FileName: GankBase
 * Date: on 2018/6/16  上午9:48
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankBase extends Base {
    private String error;

    public boolean isError() {
        return "true".equals(error);
    }

    public boolean isSuccess() {
        return "false".equals(error);
    }
}