package com.ms.kk.module.main.home;

import androidx.lifecycle.MutableLiveData;

import com.ms.kk.base.BaseRepository;
import com.ms.kk.base.RepositoryRespond;
import com.ms.kk.model.net.entity.respond.Type;
import com.ms.kk.model.net.entity.respond.BaseEntity;

import java.util.List;

public class HomeRepository extends BaseRepository {
    MutableLiveData<RepositoryRespond<List<Type>>> typeList = new MutableLiveData<>();

    public void queryTypeList() {
        addSource(commonApi.queryType(), new RepositoryObserver<List<Type>>() {
            @Override
            protected void handleSuccess(BaseEntity<List<Type>> data) {
                typeList.setValue(RepositoryRespond.createSuccess(data.getData()));
            }

            @Override
            protected void handleError(int httpCode, String extra) {
                typeList.setValue(RepositoryRespond.createError(extra, httpCode));
            }

            @Override
            protected void handleEmpty() {
                typeList.setValue(RepositoryRespond.createEmpty());
            }
        });
    }


}
