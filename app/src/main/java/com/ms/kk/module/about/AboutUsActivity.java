package com.ms.kk.module.about;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.ms.kk.R;
import com.ms.kk.databinding.ActivityAboutUsBinding;

public class AboutUsActivity extends AppCompatActivity {

    private com.ms.kk.databinding.ActivityAboutUsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        binding = ((ActivityAboutUsBinding) DataBindingUtil.setContentView(this, R.layout.activity_about_us));
        binding.llHeader.tvTitle.setText("关于我们");
        binding.llHeader.igBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}