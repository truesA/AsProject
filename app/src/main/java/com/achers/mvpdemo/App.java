package com.achers.mvpdemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.achers.mvpdemo.util.log.LogUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Achers on 2017/11/22.
 */

public  class App extends Application {

    private static final String TAG ="Degin_TAG";

    private static Context context;
    public static OkHttpClient client;
    private static Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;

        LogUtils.initParam(true);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.i(TAG, message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(10000L, TimeUnit.SECONDS)
                .readTimeout(10000L, TimeUnit.SECONDS)
                .writeTimeout(10000L, TimeUnit.SECONDS)
                .build();

    }


    /**
     * 获取retrofit 统一配置
     * @param baseurl
     * @return
     */
    public static Retrofit getRetrofit(String baseurl){
        retrofit =new Retrofit.Builder()
                .baseUrl(baseurl)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())//增加返回值为String的支持
                .addConverterFactory(GsonConverterFactory.create())//默认支持Gson解析
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }


    /**
     * 获取应用上下文对象
     */
    public static Context getAppContext(){
        return context;
    }
}
