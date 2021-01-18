package com.ms.kk.module.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ms.kk.base.ApiRespond;
import com.ms.kk.base.BaseRepository;
import com.ms.kk.base.Logger;
import com.ms.kk.base.RepositoryRespond;
import com.ms.kk.model.net.entity.respond.BaseEntity;
import com.ms.kk.model.net.entity.respond.MovieListItem;
import com.ms.kk.model.net.entity.respond.Version;

import java.util.List;

public class MainRepository extends BaseRepository {
    public MutableLiveData<RepositoryRespond<Version>> version = new MutableLiveData<>();

    public void queryVersion() {
        addSource(commonApi.queryVersion(), new SimpleRepositoryObserver<Version>() {
            @Override
            protected void handleSuccess(BaseEntity<Version> data) {
                Version version = data.getData();
                if (version != null)
                    MainRepository.this.version.setValue(RepositoryRespond.createSuccess(version));
            }
        });
    }


}
