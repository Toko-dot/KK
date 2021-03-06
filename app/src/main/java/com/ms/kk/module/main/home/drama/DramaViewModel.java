package com.ms.kk.module.main.home.drama;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;

import com.ms.kk.base.BaseViewModel;
import com.ms.kk.model.net.entity.respond.DramaItem;

import java.util.List;

public class DramaViewModel extends BaseViewModel<DramaRepository> {
    private int tid;
    public int dramaCount = 0;
    public int rStart = -1;

    public List<DramaItem> dramaItemList;

    public ObservableBoolean empty = new ObservableBoolean();

    public ObservableBoolean error = new ObservableBoolean();

    public ObservableBoolean noMore = new ObservableBoolean();

    public MutableLiveData<Void> finishRequestList = new MutableLiveData<>();

    public MutableLiveData<Void> refreshList = new MutableLiveData<>();

    public MutableLiveData<Void> loadMoreList = new MutableLiveData<>();

    public DramaViewModel(int tid) {
        this.tid = tid;
        queryDramaList(0);
    }

    @Override
    protected void registerObserver() {
        addSource(repository.list, new SimpleViewModelObserver<List<DramaItem>>() {
            @Override
            protected void handleInit() {
                super.handleInit();
                empty.set(false);
                error.set(false);
                noMore.set(false);
            }

            @Override
            protected void handleSuccess(List<DramaItem> data) {
                dramaItemList = data;
                if (rStart > 0) {
                    loadMoreList.setValue(null);
                    dramaCount += data.size();
                } else {
                    refreshList.setValue(null);
                    dramaCount = data.size();
                }
            }

            @Override
            protected void handleError(String extra, int what) {
                if (rStart == 0) {//刷新错误
                    error.set(true);
                    refreshList.setValue(null);
                    dramaCount = 0;
                }
            }

            @Override
            protected void handleEmpty() {
                if (rStart == 0) {//没有数据
                    empty.set(true);
                    refreshList.setValue(null);
                    dramaCount = 0;
                } else {
                    noMore.set(true);
                }
            }

            @Override
            protected void handleRequestEnd() {
                super.handleRequestEnd();
                finishRequestList.setValue(null);
                rStart = -1;
            }
        });
    }

    public void queryDramaList(int start) {
        if (rStart != -1) {
            finishRequestList.setValue(null);
            return;
        }
        rStart = start;
        repository.queryDramaList(tid, start);
    }

}
