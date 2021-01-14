package com.ms.kk.module.movie.play;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;

import com.ms.kk.base.BaseViewModel;
import com.ms.kk.model.net.entity.respond.DramaItem;
import com.ms.kk.model.net.entity.respond.MovieListItem;

import java.util.List;

public class MoviePlayViewModel extends BaseViewModel<MoviePlayRepository> {

    public List<MovieListItem> dramaItemList;

    public DramaItem dramaItem;

    ObservableBoolean error = new ObservableBoolean();

    public MutableLiveData<Void> querySuccess=new MutableLiveData<>();

    public MoviePlayViewModel(DramaItem dramaItem) {
        this.dramaItem = dramaItem;
        queryDramaList();
    }

    @Override
    protected void registerObserver() {
        addSource(repository.list, new ViewModelObserver<List<MovieListItem>>() {
            @Override
            protected void handleInit() {

            }

            @Override
            protected void handleSuccess(List<MovieListItem> data) {
                dramaItemList=data;
                error.set(false);
                querySuccess.setValue(null);
            }

            @Override
            protected void handleError(String extra, int what) {
                error.set(true);
            }

            @Override
            protected void handleEmpty() {
                error.set(true);
            }
        });
    }

    public void queryDramaList() {
        repository.queryDramaList(dramaItem.get_id());
    }
}
