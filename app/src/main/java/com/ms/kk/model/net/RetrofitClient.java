package com.ms.kk.model.net;

import com.ms.kk.base.LiveDataCallAdapterFactory;
import com.ms.kk.base.Logger;

import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ms.kk.constant.Constant.BASE_URL;
import static com.ms.kk.constant.Constant.BASE_URL_ONLINE;
import static com.ms.kk.constant.Constant.BASE_URL_TEST;

public class RetrofitClient {
    private static RetrofitClient instance;
    private Retrofit retrofit;

    private RetrofitClient() {
        init();
    }

    private void init() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkhttpClient())
                .build();

    }

    public <T> T getApi(Class<T> cls) {
        return retrofit.create(cls);
    }

    private OkHttpClient createOkhttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.logD(message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .callTimeout(5000, TimeUnit.MILLISECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new TokenInterceptor())
                .proxy(Proxy.NO_PROXY)
                .build();
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null)
                    instance = new RetrofitClient();
            }
        }
        return instance;
    }

}
