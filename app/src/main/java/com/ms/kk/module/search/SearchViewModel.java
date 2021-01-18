package com.ms.kk.module.search;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.ms.kk.base.BaseViewModel;
import com.ms.kk.model.net.entity.respond.DramaItem;
import com.ms.kk.model.net.entity.respond.LoginInfo;

import java.util.List;

public class SearchViewModel extends BaseViewModel<SearchRepository> {
    public ObservableField<String> postSearch = new ObservableField<>();
    public ObservableField<String> searchHot = new ObservableField<>();
    public int dramaComment = 0;
    public int rStart = -1;

    public List<DramaItem> dramaItemList;

    public ObservableBoolean empty = new ObservableBoolean();

    public ObservableBoolean error = new ObservableBoolean();

    public ObservableBoolean noMore = new ObservableBoolean();

    public MutableLiveData<Void> finishRequestList = new MutableLiveData<>();

    public MutableLiveData<Void> finishLoadMore = new MutableLiveData<>();

    public MutableLiveData<Void> refreshList = new MutableLiveData<>();

    public MutableLiveData<Void> loadMoreList = new MutableLiveData<>();

    public SearchViewModel() {
        repository.querySearchHot();
    }

    @Override
    protected void registerObserver() {
        addSource(repository.searchHot, new SimpleViewModelObserver<String>() {
            @Override
            protected void handleSuccess(String data) {
                super.handleSuccess(data);
                searchHot.set(data);
            }
        });

        addSource(repository.searchList, new SimpleViewModelObserver<List<DramaItem>>() {
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
                    dramaComment += data.size();
                } else {
                    refreshList.setValue(null);
                    dramaComment = data.size();
                }
            }

            @Override
            protected void handleError(String extra, int what) {
                if (rStart == 0) {
                    error.set(true);
                }
            }

            @Override
            protected void handleEmpty() {
                if (rStart == 0) {
                    dramaItemList = null;
                    dramaComment = 0;
                    refreshList.setValue(null);
                    empty.set(true);
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


    public void search(int start) {
        if (rStart != -1) {
            finishLoadMore.setValue(null);
            return;
        }
        rStart = start;
        LoginInfo loginInfo = userInfo.get();
        if (loginInfo == null) {
            repository.search(postSearch.get(), start);
        } else {
            repository.search(loginInfo.getUid(), postSearch.get(), start);
        }
    }
}
