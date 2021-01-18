package com.ms.kk.module.setting;

import androidx.lifecycle.MutableLiveData;

import com.ms.kk.base.BaseRepository;
import com.ms.kk.base.RepositoryRespond;
import com.ms.kk.model.net.entity.respond.BaseEntity;

public class SettingRepository extends BaseRepository {
    MutableLiveData<RepositoryRespond<Boolean>> addResult=new MutableLiveData<>();

    public void addDrama(String type, String name, String thumb, String brief) {
        addSource(commonApi.addDrama(type, name, thumb, brief), new RepositoryObserver<Object>() {
            @Override
            protected void handleSuccess(BaseEntity<Object> data) {

            }

            @Override
            protected void handleError(int httpCode, String extra) {

            }

            @Override
            protected void handleEmpty() {

            }
        });
    }

    public void addMovie(String pName, String name, String play) {
        addSource(commonApi.addMovie(pName, name, play, null), new RepositoryObserver<Object>() {
            @Override
            protected void handleSuccess(BaseEntity<Object> data) {
                addResult.setValue(RepositoryRespond.createSuccess(true));
            }

            @Override
            protected void handleError(int httpCode, String extra) {

            }

            @Override
            protected void handleEmpty() {

            }
        });
    }
}
