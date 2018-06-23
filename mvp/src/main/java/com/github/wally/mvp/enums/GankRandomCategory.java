package com.github.wally.mvp.enums;

/**
 * Package: com.github.wally.mvp.constants
 * FileName: GankRandomCategory
 * Date: on 2018/6/18  上午10:53
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public enum GankRandomCategory {
    MEI_ZI("福利"),
    ANDOID("Android"),
    IOS("IOS"),
    VIDEO("休息视频"),
    EXPAND_RESOURCES("拓展资源"),
    H5("前端");

    private String mCategory;

    GankRandomCategory(String category) {
        mCategory = category;
    }

    public String getCategory() {
        return mCategory;
    }
}