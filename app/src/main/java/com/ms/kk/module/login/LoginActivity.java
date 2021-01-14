package com.ms.kk.module.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ms.kk.R;
import com.ms.kk.base.BaseActivity;
import com.ms.kk.base.BaseRepository;
import com.ms.kk.base.BaseViewModel;
import com.ms.kk.base.Logger;
import com.ms.kk.databinding.ActivityLoginBinding;
import com.ms.kk.module.register.RegisterActivity;

public class LoginActivity extends BaseActivity<LoginViewModel> {

    private ActivityLoginBinding binding;
    public static final int CODE_LOGIN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initView();
        initViewModel();
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initViewModel() {
        super.initViewModel();
        viewModel.loginSuccess.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                setResult(RESULT_OK);
                finish();
            }
        });
        binding.setVm(viewModel);
    }

    public void onRegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void onFinish(View view) {
        finish();
    }
}