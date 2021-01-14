package com.ms.kk.module.user.sex;

import android.text.TextUtils;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.ms.kk.base.BaseViewModel;

public class EditUserSexViewModel extends BaseViewModel<EditUserSexRepository> {
    public ObservableField<String> sex = new ObservableField<String>() {
        @Override
        public void set(String value) {
            super.set(value);
            if (TextUtils.isEmpty(value)) {
                canEdit.set(false);
            } else {
                canEdit.set(true);
            }
        }
    };

    public ObservableBoolean canEdit = new ObservableBoolean();

    public MutableLiveData<Void> editSuccess = new MutableLiveData<>();

    @Override
    protected void registerObserver() {
        addSource(repository.editResult, new SimpleViewModelObserver<Boolean>() {


            @Override
            protected void handleSuccess(Boolean data) {
                editSuccess.setValue(null);
            }

            @Override
            protected void handleError(String extra, int what) {
                setToast(extra);
            }
        });
    }

    public void editSex() {
        repository.editSex(userInfo.get().getUid(), sex.get().endsWith("女")?0:1);
    }
}
