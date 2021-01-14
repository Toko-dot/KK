package com.ms.kk.module.register;

import android.text.TextUtils;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.ms.kk.base.BaseViewModel;
import com.ms.kk.base.RepositoryRespond;

public class RegisterViewModel extends BaseViewModel<RegisterRepository> {
    public ObservableField<String> account = new ObservableField<String>() {
        @Override
        public void set(String value) {
            super.set(value);
            if (!TextUtils.isEmpty(value) && !TextUtils.isEmpty(pwd.get())) {
                canLogin.set(true);
            } else {
                canLogin.set(false);
            }
        }
    };
    public ObservableField<String> pwd = new ObservableField<String>() {
        @Override
        public void set(String value) {
            super.set(value);
            if (!TextUtils.isEmpty(value) && !TextUtils.isEmpty(account.get())) {
                canLogin.set(true);
            } else {
                canLogin.set(false);
            }
        }
    };

    public ObservableBoolean canLogin = new ObservableBoolean();

    public ObservableBoolean registerSuccess = new ObservableBoolean(false);



    @Override
    protected void registerObserver() {
        addSource(repository.registerResult, new SimpleViewModelObserver<Boolean>() {
            @Override
            protected void handleInit() {

            }

            @Override
            protected void handleSuccess(Boolean data) {
                registerSuccess.set(true);
            }

            @Override
            protected void handleError(String extra, int what) {
                super.handleError(extra, what);
                setToast(extra);
            }
        });
    }

    public void register() {
        String account = this.account.get();
        String pwd = this.pwd.get();
        if (pwd.length() < 6) {
            setToast("密码最小6位！");
            return;
        }
        repository.register(account, pwd);
    }
}
