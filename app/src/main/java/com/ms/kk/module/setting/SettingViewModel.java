package com.ms.kk.module.setting;

import androidx.databinding.ObservableField;

import com.ms.kk.base.BaseViewModel;

public class SettingViewModel extends BaseViewModel<SettingRepository> {
    public ObservableField<String> version=new ObservableField<>();

    public SettingViewModel(){

    }

    @Override
    protected void registerObserver() {

    }
}
