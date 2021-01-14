package com.ms.kk.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.ParameterizedType;

public abstract class BaseFragment<PVM extends BaseViewModel<?>, VM extends BaseViewModel<?>> extends Fragment {
    protected PVM pViewModel;
    protected VM viewModel;

    protected void initView() {

    }

    protected void initViewModel() {
        pViewModel = createPViewModel();
        viewModel = createViewModel();
        viewModel.viewModelMediator.observe(this, new Observer<Object>() {
            @Override
            public void onChanged(Object o) {

            }
        });
    }
    protected PVM createPViewModel() {
        Class<VM> cls = (Class<VM>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return (PVM) new ViewModelProvider(getActivity()).get(cls);
    }

    protected VM createViewModel() {
        Class<VM> cls = (Class<VM>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        return (VM) new ViewModelProvider(this).get(cls);
    }
}
