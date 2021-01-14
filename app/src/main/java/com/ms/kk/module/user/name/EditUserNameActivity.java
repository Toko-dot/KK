package com.ms.kk.module.user.name;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.os.Bundle;

import com.ms.kk.R;
import com.ms.kk.base.BaseActivity;
import com.ms.kk.databinding.ActivityEditUserNameBinding;

public class EditUserNameActivity extends BaseActivity<EditUserNameViewModel> {

    private com.ms.kk.databinding.ActivityEditUserNameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ((ActivityEditUserNameBinding) DataBindingUtil.setContentView(this, R.layout.activity_edit_user_name));
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