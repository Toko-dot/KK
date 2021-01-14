package com.ms.kk.utils;

import android.content.Context;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.ms.kk.App;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY;
import static android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT;

public class SystemUtils {
    public static int dp2px(Context context,float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;

        return (int) (density * dpValue + 0.5);
    }

    //隐藏键盘
    public static void hideSoftInput(Context context, IBinder windowToken) {
        InputMethodManager manager = (InputMethodManager)context.getSystemService(INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(windowToken, 0);
    }

    //隐藏键盘
    public static void showSoftInput(Context context) {
        InputMethodManager manager = (InputMethodManager)context.getSystemService(INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(0, HIDE_IMPLICIT_ONLY);
    }

    public static boolean isShouldHideSoftKeyBoard(View view, MotionEvent event) {
        if (view != null && (view instanceof EditText)) {
            int[] l = {0, 0};
            view.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + view.getHeight(), right = left + view.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    
}
