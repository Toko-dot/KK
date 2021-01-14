package com.ms.kk.module.register;

import androidx.lifecycle.MutableLiveData;

import com.ms.kk.base.ApiRespond;
import com.ms.kk.base.BaseRepository;
import com.ms.kk.base.RepositoryRespond;
import com.ms.kk.model.net.entity.respond.BaseEntity;

public class RegisterRepository extends BaseRepository {
    public MutableLiveData<RepositoryRespond<Boolean>> registerResult = new MutableLiveData<>();


    public void register(String account, String pwd) {
        addSource(commonApi.register(account, pwd), new RepositoryObserver<Object>() {

            @Override
            protected void handleSuccess(BaseEntity<Object> data) {
                registerResult.setValue(RepositoryRespond.createSuccess(true));
            }

            @Override
            protected void handleError(int httpCode, String extra) {
                registerResult.setValue(RepositoryRespond.createError(extra, httpCode));
            }

            @Override
            protected void handleEmpty() {
                registerResult.setValue(RepositoryRespond.createSuccess(true));
            }
        });
    }


}
