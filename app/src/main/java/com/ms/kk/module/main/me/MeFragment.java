package com.ms.kk.module.main.me;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ms.kk.R;
import com.ms.kk.base.BaseFragment;
import com.ms.kk.constant.MKey;
import com.ms.kk.databinding.FragmentMeBinding;
import com.ms.kk.module.login.LoginActivity;
import com.ms.kk.module.main.MainViewModel;
import com.ms.kk.module.user.UserInfoActivity;
import com.tencent.mmkv.MMKV;

import static com.ms.kk.module.login.LoginActivity.CODE_LOGIN;
import static com.ms.kk.module.user.UserInfoActivity.CODE_EDIT_USER;

/**
 *
 */
public class MeFragment extends BaseFragment<MainViewModel, MeViewModel> {

    private com.ms.kk.databinding.FragmentMeBinding binding;

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ((FragmentMeBinding) DataBindingUtil.inflate(inflater, R.layout.fragment_me, container, false));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initViewModel();
    }

    @Override
    protected void initView() {
        super.initView();
        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), LoginActivity.class), CODE_LOGIN);
            }
        });
        binding.igSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MMKV.defaultMMKV().putString(MKey.KEY_USER_INFO, "").commit();
//                MMKV.defaultMMKV().putString(MKey.KEY_TOKEN, "").commit();
//                pViewModel.queryUserInfo();
            }
        });

        binding.tvEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), UserInfoActivity.class), CODE_EDIT_USER);
            }
        });

        binding.rbSee.setChecked(true);
    }

    @Override
    protected void initViewModel() {
        super.initViewModel();
        binding.setPvm(pViewModel);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pViewModel.queryUserInfo();
    }
}