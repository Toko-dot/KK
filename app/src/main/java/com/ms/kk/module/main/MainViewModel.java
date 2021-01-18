package com.ms.kk.module.main;

import android.text.TextUtils;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.ms.kk.base.BaseViewModel;
import com.ms.kk.constant.MKey;
import com.ms.kk.model.net.entity.respond.LoginInfo;
import com.ms.kk.model.net.entity.respond.Version;
import com.tencent.mmkv.MMKV;

import retrofit2.http.POST;

public class MainViewModel extends BaseViewModel<MainRepository> {

    public MutableLiveData<Version> version = new MutableLiveData<>();


    public MainViewModel() {
        super();
        repository.queryVersion();
    }

    @Override
    protected void registerObserver() {
        addSource(repository.version, new SimpleViewModelObserver<Version>() {
            @Override
            protected void handleSuccess(Version data) {
                super.handleSuccess(data);
                version.setValue(data);
            }
        });
    }


}
