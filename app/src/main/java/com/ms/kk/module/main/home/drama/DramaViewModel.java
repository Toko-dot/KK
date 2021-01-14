package com.ms.kk.module.main.home.drama;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;

import com.ms.kk.base.BaseViewModel;
import com.ms.kk.model.net.entity.respond.DramaItem;

import java.util.List;

public class DramaViewModel extends BaseViewModel<DramaRepository> {
    private int tid;
    public int page = 1;
    public int rPage = 1;

    public List<DramaItem> dramaItemList;

    public ObservableBoolean empty = new ObservableBoolean();

    public MutableLiveData<Void> finishRefresh = new MutableLiveData<>();

    public MutableLiveData<Void> finishLoadMore = new MutableLiveData<>();

    public DramaViewModel(int tid) {
        this.tid = tid;
        queryDramaList(1);
    }

    @Override
    protected void registerObserver() {
        addSource(repository.list, new SimpleViewModelObserver<List<DramaItem>>() {

            @Override
            protected void handleSuccess(List<DramaItem> data) {
                dramaItemList = data;

                if (rPage > 1) {
                    finishLoadMore.setValue(null);
                } else {
                    empty.set(false);
                    finishRefresh.setValue(null);
                }

                page = rPage;
            }

            @Override
            protected void handleError(String extra, int what) {

                if (rPage > 1) {
                    finishLoadMore.setValue(null);
                } else {
                    empty.set(true);
                    finishRefresh.setValue(null);
                }
                rPage = page;
            }

            @Override
            protected void handleEmpty() {

                if (rPage > 1) {
                    finishLoadMore.setValue(null);
                } else {
                    empty.set(true);
                    finishRefresh.setValue(null);
                }
                rPage = page;
            }
        });
    }

    public void queryDramaList(int page) {
        if (page > 1 && page == rPage) {
            finishLoadMore.setValue(null);
            return;
        }
        rPage = page;
        repository.queryDramaList(tid, page);
    }

}
