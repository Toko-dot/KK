package com.ms.kk.module.user;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.ms.kk.base.BaseRepository;
import com.ms.kk.base.RepositoryRespond;
import com.ms.kk.constant.MKey;
import com.ms.kk.model.net.entity.respond.BaseEntity;
import com.ms.kk.model.net.entity.respond.LoginInfo;
import com.tencent.mmkv.MMKV;

public class UserInfoRepository extends BaseRepository {
    public MutableLiveData<RepositoryRespond<Boolean>> editResult = new MutableLiveData<>();

    public void editAvatar(int uid, String avatar) {
        addSource(commonApi.updateAvatar(uid, avatar), new SimpleRepositoryObserver<String>() {
            @Override
            protected void handleSuccess(BaseEntity<String> data) {
                super.handleSuccess(data);
                String json = MMKV.defaultMMKV().getString(MKey.KEY_USER_INFO, "");
                LoginInfo loginInfo = new Gson().fromJson(json, LoginInfo.class);
                loginInfo.setAvatar(data.getData());
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
