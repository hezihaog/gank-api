package com.github.wally.mvp.bean.gank;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Package: com.github.wally.mvp.base.gank
 * FileName: MeiziGankBean
 * Date: on 2018/6/16  上午9:51
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankMeiZiListBean extends GankBase {
    private ArrayList<MeiZi> results;

    public static final class MeiZi implements android.os.Parcelable {
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

        public MeiZi() {
        }

        protected MeiZi(Parcel in) {
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

        public static final Creator<MeiZi> CREATOR = new Creator<MeiZi>() {
            @Override
            public MeiZi createFromParcel(Parcel source) {
                return new MeiZi(source);
            }

            @Override
            public MeiZi[] newArray(int size) {
                return new MeiZi[size];
            }
        };
    }

    public ArrayList<MeiZi> getResults() {
        return results;
    }

    public void setResults(ArrayList<MeiZi> results) {
        this.results = results;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeList(this.results);
    }

    public GankMeiZiListBean() {
    }

    protected GankMeiZiListBean(Parcel in) {
        super(in);
        this.results = new ArrayList<MeiZi>();
        in.readList(this.results, MeiZi.class.getClassLoader());
    }

    public static final Creator<GankMeiZiListBean> CREATOR = new Creator<GankMeiZiListBean>() {
        @Override
        public GankMeiZiListBean createFromParcel(Parcel source) {
            return new GankMeiZiListBean(source);
        }

        @Override
        public GankMeiZiListBean[] newArray(int size) {
            return new GankMeiZiListBean[size];
        }
    };
}