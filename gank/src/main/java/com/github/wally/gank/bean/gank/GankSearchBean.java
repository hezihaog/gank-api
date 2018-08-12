package com.github.wally.gank.bean.gank;

import android.os.Parcel;

import java.util.ArrayList;

/**
 * Package: com.github.wally.gank.bean.gank
 * FileName: GankSearchBean
 * Date: on 2018/6/23  下午11:44
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankSearchBean extends GankBase {
    private ArrayList<Msg> results;

    public static class Msg implements android.os.Parcelable {
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


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.desc);
            dest.writeString(this.ganhuo_id);
            dest.writeString(this.publishedAt);
            dest.writeString(this.readability);
            dest.writeString(this.type);
            dest.writeString(this.url);
            dest.writeString(this.who);
        }

        public Msg() {
        }

        protected Msg(Parcel in) {
            this.desc = in.readString();
            this.ganhuo_id = in.readString();
            this.publishedAt = in.readString();
            this.readability = in.readString();
            this.type = in.readString();
            this.url = in.readString();
            this.who = in.readString();
        }

        public static final Creator<Msg> CREATOR = new Creator<Msg>() {
            @Override
            public Msg createFromParcel(Parcel source) {
                return new Msg(source);
            }

            @Override
            public Msg[] newArray(int size) {
                return new Msg[size];
            }
        };
    }

    public ArrayList<Msg> getResults() {
        return results;
    }

    public void setResults(ArrayList<Msg> results) {
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

    public GankSearchBean() {
    }

    protected GankSearchBean(Parcel in) {
        super(in);
        this.results = new ArrayList<Msg>();
        in.readList(this.results, Msg.class.getClassLoader());
    }

    public static final Creator<GankSearchBean> CREATOR = new Creator<GankSearchBean>() {
        @Override
        public GankSearchBean createFromParcel(Parcel source) {
            return new GankSearchBean(source);
        }

        @Override
        public GankSearchBean[] newArray(int size) {
            return new GankSearchBean[size];
        }
    };
}