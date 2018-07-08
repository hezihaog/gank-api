package com.github.wally.mvp.bean.gank;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Package: com.github.wally.mvp.bean.gank
 * FileName: DisplayMeiZiImageBean
 * Date: on 2018/6/18  上午11:27
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class DisplayMeiZiImageBean implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.createdAt);
        dest.writeString(this.desc);
        dest.writeString(this.publishedAt);
        dest.writeString(this.source);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeString(this.used);
        dest.writeString(this.who);
    }

    public DisplayMeiZiImageBean() {
    }

    protected DisplayMeiZiImageBean(Parcel in) {
        this.id = in.readString();
        this.createdAt = in.readString();
        this.desc = in.readString();
        this.publishedAt = in.readString();
        this.source = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.used = in.readString();
        this.who = in.readString();
    }

    public static final Parcelable.Creator<DisplayMeiZiImageBean> CREATOR = new Parcelable.Creator<DisplayMeiZiImageBean>() {
        @Override
        public DisplayMeiZiImageBean createFromParcel(Parcel source) {
            return new DisplayMeiZiImageBean(source);
        }

        @Override
        public DisplayMeiZiImageBean[] newArray(int size) {
            return new DisplayMeiZiImageBean[size];
        }
    };
}