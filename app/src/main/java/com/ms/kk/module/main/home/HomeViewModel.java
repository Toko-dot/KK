package com.ms.kk.module.main.home;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;

import com.ms.kk.base.BaseViewModel;
import com.ms.kk.model.net.entity.respond.Type;

import java.util.List;

public class HomeViewModel extends BaseViewModel<HomeRepository> {
    public List<Type> typeList;

    public MutableLiveData<Void> queryTypeSuccess = new MutableLiveData<>();

    public ObservableBoolean error = new ObservableBoolean();

    public HomeViewModel(){
        queryTypeList();
    }

    @Override
    protected void registerObserver() {
        addSource(repository.typeList, new SimpleViewModelObserver<List<Type>>() {

            @Override
            protected void handleSuccess(List<Type> data) {
                typeList = data;
                queryTypeSuccess.setValue(null);
                error.set(false);
            }

            @Override
            protected void handleError(String extra, int what) {
                error.set(true);
            }

            @Override
            protected void handleEmpty() {
                error.set(true);
            }
        });
    }

    public void queryTypeList(){
        repository.queryTypeList();
    }
}
