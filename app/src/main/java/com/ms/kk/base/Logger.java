package com.ms.kk.base;


import android.util.Log;

import com.ms.kk.BuildConfig;

public class Logger {
    private  static  final String TAG="KK-LOG";
    public static void logD(String tag, String msg) {
        if (!BuildConfig.DEBUG)
            return;
        Log.d(tag, msg);
    }

    public static void logW(String tag, String msg) {
        if (!BuildConfig.DEBUG)
            return;
        Log.w(tag, msg);
    }

    public static void logE(String tag, String msg) {
        if (!BuildConfig.DEBUG)
            return;
        Log.e(tag, msg);
    }

    public static void logI(String tag, String msg) {
        if (!BuildConfig.DEBUG)
            return;
        Log.i(tag, msg);
    }
    public static void logD(Object msg) {
        logD(TAG, msg.toString());
    }
    public static void logD(String msg) {
        logD(TAG, msg);
    }

    public static void logE(String msg) {
        logE(TAG, msg);
    }

    public static void logW(String msg) {
        logW(TAG, msg);
    }

    public static void logI(String msg) {
        logI(TAG, msg);
    }

}
