package com.ms.kk.base;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.ms.kk.R;
import com.ms.kk.utils.SystemUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS;
import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;
import static android.view.WindowInsetsController.BEHAVIOR_SHOW_BARS_BY_TOUCH;
import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;
import static androidx.core.view.WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_SWIPE;

public abstract class BaseActivity<VM extends BaseViewModel<?>> extends AppCompatActivity {

    protected VM viewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        onFinishSetContentView();
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onFinishSetContentView();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        onFinishSetContentView();
    }

    protected void onFinishSetContentView() {
        View view = getWindow().getDecorView().getRootView().findViewById(android.R.id.content);
        WindowInsetsControllerCompat controller = ViewCompat.getWindowInsetsController(view);
        if (controller != null) {
            controller.show(WindowInsetsCompat.Type.statusBars());
            controller.setAppearanceLightStatusBars(true);
        }
    }


    public VM createViewModel() {
        Class<VM> cls = (Class<VM>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return (VM) new ViewModelProvider(this).get(cls);
    }

    public void initViewModel() {
        viewModel = createViewModel();
        viewModel.viewModelMediator.observe(this, new Observer<Object>() {
            @Override
            public void onChanged(Object o) {
                //注册监听
            }
        });

        viewModel.toast.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showToast(s);
            }
        });
    }

    public void initView() {

    }

    protected void showToast(String msg) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_item_toast, null);
        ((TextView) view.findViewById(R.id.tv_msg)).setText(msg);
        Toast toast = new Toast(this);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.viewModelMediator.removeObservers(this);
    }

    public void onFinish(View view) {
        finish();
    }

    protected boolean checkFilePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (SystemUtils.isShouldHideSoftKeyBoard(view, ev)) {
                SystemUtils.hideSoftInput(this,view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
