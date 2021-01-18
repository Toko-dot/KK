package com.ms.kk.module.search;

import androidx.lifecycle.MutableLiveData;

import com.ms.kk.base.BaseRepository;
import com.ms.kk.base.RepositoryRespond;
import com.ms.kk.model.net.entity.respond.BaseEntity;
import com.ms.kk.model.net.entity.respond.DramaItem;

import java.util.List;

public class SearchRepository extends BaseRepository {
    public MutableLiveData<RepositoryRespond<List<DramaItem>>> searchList = new MutableLiveData<>();

    public MutableLiveData<RepositoryRespond<String>> searchHot = new MutableLiveData<>();

    public void search(String search, int start) {
        searchList.setValue(RepositoryRespond.createInit());

        addSource(commonApi.search(search, start), new SimpleRepositoryObserver<List<DramaItem>>() {
            @Override
            protected void handleSuccess(BaseEntity<List<DramaItem>> data) {
                super.handleSuccess(data);
                if (data.getData() == null || data.getData().size() == 0) {
                    searchList.setValue(RepositoryRespond.createEmpty());
                } else {
                    searchList.setValue(RepositoryRespond.createSuccess(data.getData()));
                }
            }

            @Override
            protected void handleEmpty() {
                super.handleEmpty();
                searchList.setValue(RepositoryRespond.createEmpty());
            }

            @Override
            protected void handleError(int httpCode, String extra) {
                super.handleError(httpCode, extra);
                searchList.setValue(RepositoryRespond.createError(extra));
            }
        });
    }

    public void search(int uid, String search, int start) {
        searchList.setValue(RepositoryRespond.createInit());
        addSource(commonApi.search(uid, search, start), new SimpleRepositoryObserver<List<DramaItem>>() {



            @Override
            protected void handleSuccess(BaseEntity<List<DramaItem>> data) {
                super.handleSuccess(data);
                if (data.getData() == null || data.getData().size() == 0) {
                    searchList.setValue(RepositoryRespond.createEmpty());
                } else {
                    searchList.setValue(RepositoryRespond.createSuccess(data.getData()));
                }
            }

            @Override
            protected void handleEmpty() {
                super.handleEmpty();
                searchList.setValue(RepositoryRespond.createEmpty());
            }

            @Override
            protected void handleError(int httpCode, String extra) {
                super.handleError(httpCode, extra);
                searchList.setValue(RepositoryRespond.createError(extra));
            }
        });
    }

    public void querySearchHot(){
        addSource(commonApi.querySearchHot(),new SimpleRepositoryObserver<String>(){
            @Override
            protected void handleSuccess(BaseEntity<String> data) {
                super.handleSuccess(data);
                searchHot.setValue(RepositoryRespond.createSuccess(data.getData()));
            }
        });
    }
}
