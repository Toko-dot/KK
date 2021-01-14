package com.ms.kk.module.user.sex;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.os.Bundle;

import com.ms.kk.R;
import com.ms.kk.base.BaseActivity;
import com.ms.kk.databinding.ActivityEditUserSexBinding;

public class EditUserSexActivity extends BaseActivity<EditUserSexViewModel> {

    private com.ms.kk.databinding.ActivityEditUserSexBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ((ActivityEditUserSexBinding) DataBindingUtil.setContentView(this, R.layout.activity_edit_user_sex));
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
        viewModel.editSuccess.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                setResult(RESULT_OK);
                finish();
            }
        });

        binding.setVm(viewModel);
    }
}