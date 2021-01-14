package com.ms.kk.model.net;

import android.text.TextUtils;


import com.tencent.mmkv.MMKV;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.ms.kk.constant.MKey.KEY_TOKEN;


public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = MMKV.defaultMMKV().getString(KEY_TOKEN, "");
        Request.Builder newBuilder = chain.request().newBuilder();
        if (!TextUtils.isEmpty(token))
            newBuilder.addHeader("token", token);
        return chain.proceed(newBuilder.build());
    }
}
