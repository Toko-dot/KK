package com.ms.kk.module.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;

import com.ms.kk.R;
import com.ms.kk.base.BaseActivity;
import com.ms.kk.databinding.ActivityRegisterBinding;

public class RegisterActivity extends BaseActivity<RegisterViewModel> {

    private com.ms.kk.databinding.ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ((ActivityRegisterBinding) DataBindingUtil.setContentView(this, R.layout.activity_register));
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

        binding.setVm(viewModel);
    }

    public void onFinish(View view) {
        finish();
    }
}