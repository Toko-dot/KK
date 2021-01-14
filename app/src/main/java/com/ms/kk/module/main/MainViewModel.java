package com.ms.kk.module.main;

import android.text.TextUtils;

import androidx.databinding.ObservableField;

import com.google.gson.Gson;
import com.ms.kk.base.BaseViewModel;
import com.ms.kk.constant.MKey;
import com.ms.kk.model.net.entity.respond.LoginInfo;
import com.tencent.mmkv.MMKV;

import retrofit2.http.POST;

public class MainViewModel extends BaseViewModel<MainRepository> {



    public MainViewModel() {
        super();
    }

    @Override
    protected void registerObserver() {

    }


}
