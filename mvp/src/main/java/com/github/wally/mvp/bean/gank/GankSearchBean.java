package com.github.wally.mvp.bean.gank;

import java.util.ArrayList;

/**
 * Package: com.github.wally.mvp.bean.gank
 * FileName: GankSearchBean
 * Date: on 2018/6/23  下午11:44
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankSearchBean extends GankBase {
    private ArrayList<Msg> results;

    public static class Msg {
        /**
         * 解释
         */
        private String desc;
        /**
         * 干货Id
         */
        private String ganhuo_id;
        /**
         * 推送时间
         */
        private String publishedAt;
        /**
         * 是否可读
         */
        private String readability;
        /**
         * 类型
         */
        private String type;
        /**
         * 地址，福利类型则是图片地址，视频类型则是视频地址
         */
        private String url;
        /**
         * 发布者
         */
        private String who;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getGanhuo_id() {
            return ganhuo_id;
        }

        public void setGanhuo_id(String ganhuo_id) {
            this.ganhuo_id = ganhuo_id;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getReadability() {
            return readability;
        }

        public void setReadability(String readability) {
            this.readability = readability;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }

    public ArrayList<Msg> getResults() {
        return results;
    }

    public void setResults(ArrayList<Msg> results) {
        this.results = results;
    }
}