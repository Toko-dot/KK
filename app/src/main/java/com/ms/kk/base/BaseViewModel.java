package com.ms.kk.base;

import android.text.TextUtils;
import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.ms.kk.constant.MKey;
import com.ms.kk.model.net.CommonApi;
import com.ms.kk.model.net.RetrofitClient;
import com.ms.kk.model.net.entity.respond.LoginInfo;
import com.tencent.mmkv.MMKV;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

public abstract class BaseViewModel<R extends BaseRepository> extends ViewModel {
    protected R repository;

    public MediatorLiveData<Object> viewModelMediator = new MediatorLiveData<>();

    public MutableLiveData<String> toast = new MutableLiveData<>();

    private ArrayList<LiveData<?>> sources = new ArrayList<>();

    public ObservableField<LoginInfo> userInfo = new ObservableField<>();

    public BaseViewModel() {
        init();
        queryUserInfo();
    }

    private void init() {
        initRepository();
        viewModelMediator.addSource(repository.apiMediator, new Observer<Object>() {
            @Override
            public void onChanged(Object o) {

            }
        });

        registerObserver();
    }


    protected abstract void registerObserver();


    public void setToast(String msg) {
        this.toast.setValue(msg);
    }

    public void queryUserInfo() {
        String json = MMKV.defaultMMKV().getString(MKey.KEY_USER_INFO, "");
        if (TextUtils.isEmpty(json)) {
            userInfo.set(null);
        } else {
            LoginInfo loginInfo = new Gson().fromJson(json, LoginInfo.class);
            userInfo.set(loginInfo);
        }

    }

    private void initRepository() {
        try {
            repository = createRepository();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    protected R createRepository() throws InstantiationException, IllegalAccessException {
        Class<R> cls = (Class<R>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        R instance = cls.newInstance();
        return instance;
    }

    protected <D> void addSource(LiveData<RepositoryRespond<D>> source, ViewModelObserver<D> observer) {
        sources.add(source);
        viewModelMediator.addSource(source, observer);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        if (sources.size() == 0)
            return;
        for (LiveData<?> source : sources) {
            viewModelMediator.removeSource(source);
        }
    }

    public static class SimpleViewModelObserver<D> extends ViewModelObserver<D> {

        @Override
        protected void handleInit() {

        }

        @Override
        protected void handleSuccess(D data) {

        }

        @Override
        protected void handleError(String extra, int what) {

        }

        @Override
        protected void handleEmpty() {

        }
    }

    public static abstract class ViewModelObserver<D> implements Observer<RepositoryRespond<D>> {


        @Override
        public void onChanged(RepositoryRespond<D> dRepositoryRespond) {
            switch (dRepositoryRespond.status) {
                case INIT:
                    handleInit();
                    break;
                case SUCCESS:
                    D data = dRepositoryRespond.data;
                    if (data == null) {
                        handleEmpty();
                    } else {
                        handleSuccess(data);
                    }

                    break;
                case ERROR:
                    handleError(dRepositoryRespond.extra, dRepositoryRespond.what);
                    break;
                case EMPTY:
                    handleEmpty();
                    break;
            }

        }

        protected abstract void handleInit();

        protected abstract void handleSuccess(D data);

        protected abstract void handleError(String extra, int what);

        protected abstract void handleEmpty();


    }


}
