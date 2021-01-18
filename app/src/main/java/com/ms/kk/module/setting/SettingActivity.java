package com.ms.kk.module.setting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ms.kk.R;
import com.ms.kk.base.BaseActivity;
import com.ms.kk.base.BaseViewModel;
import com.ms.kk.constant.MKey;
import com.ms.kk.databinding.ActivitySettingBinding;
import com.ms.kk.module.AboutUsActivity;
import com.ms.kk.module.login.LoginActivity;
import com.ms.kk.utils.SystemUtils;
import com.tencent.mmkv.MMKV;

public class SettingActivity extends BaseActivity<SettingViewModel> {

    private ActivitySettingBinding binding;

    public final static int SETTING_CODE = 112;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        initView();
        initViewModel();
    }

    @Override
    public void initView() {
        super.initView();
        binding.llHeader.tvTitle.setText("设置");
        binding.llHeader.igBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initViewModel() {
        super.initViewModel();
        viewModel.version.set(SystemUtils.getVersionName(this));
        binding.setVm(viewModel);
    }

    public void onExit(View view) {
        MMKV.defaultMMKV().putString(MKey.KEY_USER_INFO, "").commit();
        MMKV.defaultMMKV().putString(MKey.KEY_TOKEN, "").commit();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, LoginActivity.CODE_LOGIN);
    }

    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }

    public void onAbout(View view) {
        startActivity(new Intent(this, AboutUsActivity.class));
    }

    public void onChat(View view) {
        SystemUtils.joinQQ(this,"2479024161");
    }
}