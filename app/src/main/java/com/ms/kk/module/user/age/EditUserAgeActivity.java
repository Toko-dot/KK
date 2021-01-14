package com.ms.kk.module.user.age;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.os.Bundle;

import com.ms.kk.R;
import com.ms.kk.base.BaseActivity;
import com.ms.kk.databinding.ActivityEditUserAgeBinding;

public class EditUserAgeActivity extends BaseActivity<EditUserAgeViewModel> {

    private com.ms.kk.databinding.ActivityEditUserAgeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ((ActivityEditUserAgeBinding) DataBindingUtil.setContentView(this, R.layout.activity_edit_user_age));
        initView();
        initViewModel();
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