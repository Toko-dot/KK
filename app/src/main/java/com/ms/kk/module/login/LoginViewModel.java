package com.ms.kk.module.login;

import android.text.TextUtils;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MediatorLiveData;

import com.ms.kk.base.BaseViewModel;

public class LoginViewModel extends BaseViewModel<LoginRepository> {

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

    public MediatorLiveData<Void> loginSuccess = new MediatorLiveData<>();

    public LoginViewModel() {

    }

    @Override
    protected void registerObserver() {
        addSource(repository.loginResult, new SimpleViewModelObserver<Boolean>() {

            @Override
            protected void handleSuccess(Boolean data) {
                loginSuccess.setValue(null);
            }

            @Override
            protected void handleError(String extra, int what) {
                super.handleError(extra, what);
                setToast(extra);
            }
        });
    }

    public void login() {
        repository.login(account.get(), pwd.get());
    }
}
