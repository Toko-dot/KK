package com.ms.kk.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.widget.RadioGroup;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;

import com.ms.kk.R;
import com.ms.kk.base.BaseActivity;
import com.ms.kk.databinding.ActivityMainBinding;
import com.ms.kk.module.login.LoginActivity;
import com.ms.kk.module.main.discover.DiscoverFragment;
import com.ms.kk.module.main.home.HomeFragment;
import com.ms.kk.module.main.me.MeFragment;

public class MainActivity extends BaseActivity<MainViewModel> {

    private com.ms.kk.databinding.ActivityMainBinding binding;
    private HomeFragment homeFragment;
    private DiscoverFragment discoverFragment;
    private MeFragment meFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ((ActivityMainBinding) DataBindingUtil.setContentView(this, R.layout.activity_main));
        initView();
        initViewModel();
    }

    @Override
    public void initView() {
        super.initView();
        binding.rgMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        checkHome();
                        break;
                    case R.id.rb_discover:
                        checkDiscover();
                        break;
                    case R.id.rb_me:
                        checkMe();
                        break;
                }
            }
        });
        initFragment();
    }

    private void initFragment() {
        homeFragment = new HomeFragment();
        discoverFragment = new DiscoverFragment();
        meFragment = new MeFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_main, homeFragment)
                .add(R.id.fl_main, discoverFragment)
                .add(R.id.fl_main, meFragment)
                .setMaxLifecycle(homeFragment, Lifecycle.State.CREATED)
                .setMaxLifecycle(discoverFragment, Lifecycle.State.CREATED)
                .setMaxLifecycle(discoverFragment, Lifecycle.State.CREATED)
                .commit();
        binding.rbHome.setChecked(true);
        checkHome();
    }

    private void checkHome() {
        getSupportFragmentManager().beginTransaction()
                .hide(meFragment)
                .hide(discoverFragment)
                .show(homeFragment)
                .setMaxLifecycle(homeFragment, Lifecycle.State.RESUMED)
                .commit();
    }

    private void checkDiscover() {
        getSupportFragmentManager().beginTransaction()
                .hide(meFragment)
                .hide(homeFragment)
                .show(discoverFragment)
                .setMaxLifecycle(discoverFragment, Lifecycle.State.RESUMED)
                .commit();
    }

    private void checkMe() {
        getSupportFragmentManager().beginTransaction()
                .hide(homeFragment)
                .hide(discoverFragment)
                .show(meFragment)
                .setMaxLifecycle(meFragment, Lifecycle.State.RESUMED)
                .commit();
    }


    @Override
    public void initViewModel() {
        super.initViewModel();


    }
}