package com.github.wally.gank.http

import android.util.Log
import com.github.wally.gank.constants.Constants
import com.github.wally.gank.http.api.GankApiService
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Package: com.github.wally.training.util
 * FileName: RequestManager
 * Date: on 2018/6/14  下午5:24
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class RetrofitManager private constructor() {
    private val mRetrofits by lazy {
        mutableMapOf<String, Retrofit>()
    }

    val gankApiService: GankApiService by lazy {
        getRetrofit(Constants.Api.GANK_DOMAIN)?.create(GankApiService::class.java)!!
    }

    /**
     * 自定义拦截器打Log
     */
    private//日志显示级别
    //新建log拦截器
    //定制OkHttp
    //OkHttp进行添加拦截器loggingInterceptor
    val okHttpClient: OkHttpClient
        get() {
            val level = HttpLoggingInterceptor.Level.BODY
            val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.d("hzh", "OkHttp ==== Message:\n$message") })
            loggingInterceptor.level = level
            val httpClientBuilder = OkHttpClient.Builder()
            httpClientBuilder.addInterceptor(loggingInterceptor)
            httpClientBuilder.connectTimeout(60L, TimeUnit.SECONDS)
                    .readTimeout(60L, TimeUnit.SECONDS)
                    .writeTimeout(60L, TimeUnit.SECONDS)
            return httpClientBuilder.build()
        }

    private object SingleHolder {
        val INSTANCE = RetrofitManager()
    }

    fun getRetrofit(baseUrl: String): Retrofit? {
        var retrofit: Retrofit? = mRetrofits[baseUrl]
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit
    }

    companion object {
        val instance: RetrofitManager
            get() = SingleHolder.INSTANCE
    }
}