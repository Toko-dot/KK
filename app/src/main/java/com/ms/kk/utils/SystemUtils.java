package com.ms.kk.utils;

import android.content.Context;

import com.ms.kk.App;

public class SystemUtils {
    public static int dp2px(Context context,float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;

        return (int) (density * dpValue + 0.5);
    }

}
