package com.ms.kk.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.ms.kk.model.net.CommonApi;
import com.ms.kk.model.net.RetrofitClient;
import com.ms.kk.model.net.entity.respond.BaseEntity;
import com.ms.kk.model.net.entity.respond.MovieListItem;

import java.util.List;

public class BaseRepository {

    public  MediatorLiveData<Object> apiMediator = new MediatorLiveData<>();

    protected CommonApi commonApi = RetrofitClient.getInstance().getApi(CommonApi.class);

    public <D> void addSource(LiveData<ApiRespond<BaseEntity<D>>> source, RepositoryObserver<D> observer) {
        observer.setSource(source);
        apiMediator.addSource(source, observer);
    }

    public  class SimpleRepositoryObserver<D> extends RepositoryObserver<D> {


        @Override
        protected void handleError(int httpCode, String extra) {

        }

        @Override
        protected void handleSuccess(BaseEntity<D> data) {

        }

        @Override
        protected void handleEmpty() {

        }
    }


    public  abstract class RepositoryObserver<D> implements Observer<ApiRespond<BaseEntity<D>>> {
        private LiveData<?> source;

        public void setSource(LiveData<?> source) {
            this.source = source;
        }

        @Override
        public void onChanged(ApiRespond<BaseEntity<D>> baseEntityApiRespond) {
            switch (baseEntityApiRespond.status) {
                case SUCCESS:
                    BaseEntity<D> data = baseEntityApiRespond.data;
                    if (data == null) {
                        handleEmpty();
                    } else {
                        handleSuccess(data);
                    }
                    break;
                case ERROR:
                    handleError(baseEntityApiRespond.httpCode,baseEntityApiRespond.extra);
                    break;
            }
            end();
        }
        protected abstract void handleSuccess(BaseEntity<D> data);


        protected abstract void handleError(int httpCode, String extra);


        protected abstract void handleEmpty();


        protected void end() {
            apiMediator.removeSource(source);
        }




    }

}
