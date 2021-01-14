package com.ms.kk.module.user;

import androidx.lifecycle.MutableLiveData;

import com.ms.kk.base.BaseViewModel;

public class UserInfoViewModel extends BaseViewModel<UserInfoRepository> {


    @Override
    protected void registerObserver() {
        addSource(repository.editResult, new SimpleViewModelObserver<Boolean>() {

            @Override
            protected void handleSuccess(Boolean data) {
                queryUserInfo();
            }

            @Override
            protected void handleError(String extra, int what) {
                setToast(extra);
            }
        });
    }

    public void editAvatar(String avatar) {
        repository.editAvatar(userInfo.get().getUid(), avatar);
    }
}
