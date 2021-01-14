package com.ms.kk.module.movie.play;

import androidx.lifecycle.MutableLiveData;

import com.ms.kk.base.BaseRepository;
import com.ms.kk.base.RepositoryRespond;
import com.ms.kk.model.net.entity.respond.BaseEntity;
import com.ms.kk.model.net.entity.respond.DramaItem;
import com.ms.kk.model.net.entity.respond.MovieListItem;

import java.util.List;

public class MoviePlayRepository extends BaseRepository {
    MutableLiveData<RepositoryRespond<List<MovieListItem>>> list = new MutableLiveData<>();
    public void queryDramaList(int pid) {
        addSource(commonApi.queryPlayList(pid), new SimpleRepositoryObserver<List<MovieListItem>>() {
                    @Override
                    protected void handleSuccess(BaseEntity<List<MovieListItem>> data) {
                        super.handleSuccess(data);
                        if (data.getData()==null||data.getData().size()==0){
                            list.setValue(RepositoryRespond.createEmpty());
                        }else {
                            list.setValue(RepositoryRespond.createSuccess(data.getData()));
                        }

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
