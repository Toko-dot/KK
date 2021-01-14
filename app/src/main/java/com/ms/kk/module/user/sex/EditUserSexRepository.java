package com.ms.kk.module.user.sex;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.ms.kk.base.BaseRepository;
import com.ms.kk.base.RepositoryRespond;
import com.ms.kk.constant.MKey;
import com.ms.kk.model.net.entity.respond.BaseEntity;
import com.ms.kk.model.net.entity.respond.LoginInfo;
import com.tencent.mmkv.MMKV;

public class EditUserSexRepository extends BaseRepository {
    public MutableLiveData<RepositoryRespond<Boolean>> editResult = new MutableLiveData<>();

    public void editSex(int uid, int sex) {
        addSource(commonApi.updateSex(uid, sex), new SimpleRepositoryObserver<Object>() {
            @Override
            protected void handleSuccess(BaseEntity<Object> data) {
                super.handleSuccess(data);
                String json = MMKV.defaultMMKV().getString(MKey.KEY_USER_INFO, "");
                LoginInfo loginInfo = new Gson().fromJson(json, LoginInfo.class);
                loginInfo.setSex(sex);
                String toJson = new Gson().toJson(loginInfo);
                MMKV.defaultMMKV().putString(MKey.KEY_USER_INFO, toJson);
                editResult.setValue(RepositoryRespond.createSuccess(true));
            }

            @Override
            protected void handleEmpty() {
                super.handleEmpty();
                String json = MMKV.defaultMMKV().getString(MKey.KEY_USER_INFO, "");
                LoginInfo loginInfo = new Gson().fromJson(json, LoginInfo.class);
                loginInfo.setSex(sex);
                String toJson = new Gson().toJson(loginInfo);
                MMKV.defaultMMKV().putString(MKey.KEY_USER_INFO, toJson);
                editResult.setValue(RepositoryRespond.createSuccess(true));
            }

            @Override
            protected void handleError(int httpCode, String extra) {
                super.handleError(httpCode, extra);
                editResult.setValue(RepositoryRespond.createError(extra));
            }
        });
    }
}
