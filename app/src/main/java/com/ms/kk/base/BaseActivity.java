package com.ms.kk.base;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.ms.kk.R;
import com.ms.kk.module.login.LoginActivity;
import com.ms.kk.utils.SystemUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public abstract class BaseActivity<VM extends BaseViewModel<?>> extends AppCompatActivity {

    protected VM viewModel;

    private ExecutorService executor;

    private File apkFile;
    private AlertDialog downLoadDialog;
    private TextView tv_progress;
    private SeekBar sb_progress;
    private DownLoadApkHandler downLoadApkHandler;
    private String apkUrl;

    private static class DownLoadApkHandler extends Handler {
        private WeakReference<BaseActivity> activity;

        public DownLoadApkHandler(BaseActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    activity.get().updateDownloadApk(msg.arg1, msg.arg2);
                    break;
                case 1:
                    activity.get().installApk();
                    break;
                case -1:
                    activity.get().downloadApkError();
                    break;

            }

        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        downLoadApkHandler = new DownLoadApkHandler(this);
        executor = Executors.newSingleThreadExecutor();
        apkFile = new File(BaseActivity.this.getExternalCacheDir(), "kk.apk");
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
            if (isSoftKeyBoardShow() && SystemUtils.isShouldHideSoftKeyBoard(view, ev) && isHideSoftInput(ev)) {
                SystemUtils.hideSoftInput(this, view.getWindowToken());
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    protected boolean isSoftKeyBoardShow() {
        Rect rect = new Rect();
        getWindow().getDecorView().findViewById(android.R.id.content).getWindowVisibleDisplayFrame(rect);
        final int screenHeight = getWindow().getDecorView().getRootView().getHeight();
        final int heightDifference = screenHeight - rect.bottom;
        boolean visible = heightDifference > screenHeight / 3;
        return visible;
    }

    protected boolean isHideSoftInput(MotionEvent ev) {
        return true;
    }


    public void showDownloadDialog(String apk) {
        this.apkUrl = apk;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = LayoutInflater.from(this).inflate(R.layout.layout_apk_download, null);

        tv_progress = ((TextView) view.findViewById(R.id.tv_progress));

        sb_progress = ((SeekBar) view.findViewById(R.id.sb_progress));

        builder.setView(view);

        builder.setCancelable(false);

        downLoadDialog = builder.show();

        downLoadApk();
    }

    private void downLoadApk() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(apkUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.connect();

                    int contentLength = httpURLConnection.getContentLength();

                    InputStream inputStream = httpURLConnection.getInputStream();


                    FileOutputStream outputStream = new FileOutputStream(apkFile);

                    int record = 0;

                    int len = 0;

                    byte[] bytes = new byte[1024];


                    while ((len = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, len);
                        record += len;
                        Message message = Message.obtain();
                        message.what = 0;
                        message.arg1 = record;
                        message.arg2 = contentLength;
                        downLoadApkHandler.sendMessage(message);
                    }
                    downLoadApkHandler.sendEmptyMessage(1);
                    Logger.logD("结束：");
                } catch (IOException e) {
                    e.printStackTrace();
                    downLoadApkHandler.sendEmptyMessage(-1);
                }
            }
        });
    }

    private void downloadApkError() {
        downLoadDialog.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("更新出错，重新更新？");
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDownloadDialog(apkUrl);
            }
        });
        builder.show();
    }

    private void installApk() {
        downLoadDialog.dismiss();
        SystemUtils.installApk(this, apkFile);
    }

    private void updateDownloadApk(int current, int total) {
        tv_progress.setText(current + "/" + total);
        sb_progress.setProgress(current * 100 / total);
    }
}
