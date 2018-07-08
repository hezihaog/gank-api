package com.github.wally.mvp.bean.gank;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Package: com.github.wally.mvp.base.gank
 * FileName: GankBase
 * Date: on 2018/6/16  上午9:48
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankBase implements Parcelable {
    private String error;

    public static final Creator<GankBase> CREATOR = new Creator<GankBase>() {
        @Override
        public GankBase createFromParcel(Parcel in) {
            return new GankBase(in);
        }

        @Override
        public GankBase[] newArray(int size) {
            return new GankBase[size];
        }
    };

    public boolean isError() {
        return "true".equals(error);
    }

    public boolean isSuccess() {
        return "false".equals(error);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.error);
    }

    public GankBase() {
    }

    protected GankBase(Parcel in) {
        this.error = in.readString();
    }

}