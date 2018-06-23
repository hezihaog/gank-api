package com.github.wally.mvp.http;

import android.util.Log;

import com.github.wally.mvp.http.api.GankApiService;
import com.github.wally.mvp.constants.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Package: com.github.wally.training.util
 * FileName: RequestManager
 * Date: on 2018/6/14  下午5:24
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class RetrofitManager {
    private HashMap<String, Retrofit> mRetrofits = new HashMap<String, Retrofit>();
    private GankApiService mGankApiService;

    private RetrofitManager() {
    }

    private static final class SingleHolder {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return SingleHolder.INSTANCE;
    }

    public Retrofit getRetrofit(String baseUrl) {
        Retrofit retrofit = mRetrofits.get(baseUrl);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(getOkHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public GankApiService getGankApiService() {
        if (mGankApiService == null) {
            mGankApiService = getRetrofit(Constants.Api.GANK_DOMAIN).create(GankApiService.class);
        }
        return mGankApiService;
    }

    /**
     * 自定义拦截器打Log
     */
    private OkHttpClient getOkHttpClient() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("hzh", "OkHttp ==== Message:\n" + message);
            }
        });
        loggingInterceptor.setLevel(level);
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
                .Builder();
        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(loggingInterceptor);
        httpClientBuilder.connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .writeTimeout(60L, TimeUnit.SECONDS);
        return httpClientBuilder.build();
    }
}