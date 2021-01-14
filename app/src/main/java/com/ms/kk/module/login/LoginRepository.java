package com.ms.kk.module.login;
import androidx.lifecycle.MutableLiveData;
import com.google.gson.Gson;
import com.ms.kk.base.BaseRepository;
import com.ms.kk.base.RepositoryRespond;
import com.ms.kk.constant.MKey;
import com.ms.kk.model.net.entity.respond.BaseEntity;
import com.ms.kk.model.net.entity.respond.LoginInfo;
import com.tencent.mmkv.MMKV;
import static com.ms.kk.constant.MKey.KEY_USER_INFO;
public class LoginRepository extends BaseRepository {
    public MutableLiveData<RepositoryRespond<Boolean>> loginResult = new MutableLiveData<>();


    public void login(String account, String pwd) {
        addSource(commonApi.login(account, pwd), new SimpleRepositoryObserver<LoginInfo>() {
            @Override
            protected void handleSuccess(BaseEntity<LoginInfo> data) {
                switch (data.getCode()) {
                    case 200:
                        MMKV.defaultMMKV().putString(MKey.KEY_TOKEN, data.getData().getToken()).commit();
                        MMKV.defaultMMKV().putString(KEY_USER_INFO, new Gson().toJson(data.getData())).commit();
                        loginResult.setValue(RepositoryRespond.createSuccess(true));
                        break;
                    default:
                        loginResult.setValue(RepositoryRespond.createError(data.getMsg()));
                        break;
                }
            }

            @Override
            protected void handleError(int httpCode, String extra) {
                loginResult.setValue(RepositoryRespond.createError(extra, httpCode));
            }
        });
    }

}
