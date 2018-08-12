package com.github.wally.gank.enums;

/**
 * Package: com.github.wally.gank.enums
 * FileName: SearchCategory
 * Date: on 2018/6/23  下午10:45
 * Auther: zihe
 * Descirbe:搜索类型分类枚举
 * Email: hezihao@linghit.com
 */
public enum GankSearchCategory {
    ALL("all"),
    ANDROID("Android"),
    iOS("iOS"),
    VIDEO("休息视频"),
    MEIZI("福利"),
    EXPAND_RESOURCE("拓展资源"),
    H5("前端"),
    RECOMMEND("瞎推荐"),
    APP("App");

    private String mCategory;

    GankSearchCategory(String category) {
        mCategory = category;
    }

    public String getCategory() {
        return mCategory;
    }
}