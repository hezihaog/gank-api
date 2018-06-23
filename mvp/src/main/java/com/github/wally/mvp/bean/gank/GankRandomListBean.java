package com.github.wally.mvp.bean.gank;

import com.github.wally.mvp.bean.Base;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Package: com.github.wally.mvp.bean.gank
 * FileName: GankRandomMeiZiListBean
 * Date: on 2018/6/18  上午10:27
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankRandomListBean extends GankBase {
    private ArrayList<MeiZi> results;

    public static class MeiZi extends Base {
        @SerializedName(value = "_id")
        private String id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private String used;
        private String who;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
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

        public String getUsed() {
            return used;
        }

        public void setUsed(String used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }

    public ArrayList<MeiZi> getResults() {
        return results;
    }

    public void setResults(ArrayList<MeiZi> results) {
        this.results = results;
    }
}