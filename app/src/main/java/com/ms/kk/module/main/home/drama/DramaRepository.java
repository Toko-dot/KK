package com.ms.kk.module.main.home.drama;

import androidx.lifecycle.MutableLiveData;

import com.ms.kk.base.BaseRepository;
import com.ms.kk.base.RepositoryRespond;
import com.ms.kk.model.net.entity.respond.BaseEntity;
import com.ms.kk.model.net.entity.respond.DramaItem;

import java.util.List;

public class DramaRepository extends BaseRepository {
    MutableLiveData<RepositoryRespond<List<DramaItem>>> list = new MutableLiveData<>();

    public void queryDramaList(int tid, int page) {

        addSource(commonApi.queryDramaList(tid, page), new SimpleRepositoryObserver<List<DramaItem>>() {
                    @Override
                    protected void handleSuccess(BaseEntity<List<DramaItem>> data) {
                        super.handleSuccess(data);
                        list.setValue(RepositoryRespond.createSuccess(data.getData()));
                    }

                    @Override
                    protected void handleEmpty() {
                        super.handleEmpty();
                        list.setValue(RepositoryRespond.createEmpty());
                    }

                    @Override
                    protected void handleError(int httpCode, String extra) {
                        super.handleError(httpCode, extra);
                        list.setValue(RepositoryRespond.createError(extra, httpCode));
                    }
                }

        );

    }

}
