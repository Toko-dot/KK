package com.ms.kk.module.setting;

import androidx.databinding.ObservableField;

import com.ms.kk.base.BaseViewModel;

import java.util.List;

public class SettingViewModel extends BaseViewModel<SettingRepository> {
    public ObservableField<String> version = new ObservableField<>();
    private int index;
    private List<SettingActivity.MovieSource> play;

    public SettingViewModel() {

    }

    @Override
    protected void registerObserver() {
        addSource(repository.addResult, new ViewModelObserver<Boolean>() {
            @Override
            protected void handleInit() {

            }

            @Override
            protected void handleSuccess(Boolean data) {
                index++;
                addMovie(play,index);
            }

            @Override
            protected void handleError(String extra, int what) {

            }

            @Override
            protected void handleEmpty() {

            }

            @Override
            protected void handleRequestEnd() {

            }
        });
    }

    public void addDrama(String type, String name, String thumb, String brief) {
        repository.addDrama(type, name, thumb, brief);
    }

    public void addMovie(List<SettingActivity.MovieSource> play, int index) {
        this.index=index;
        this.play=play;
        if (this.index<play.size()){
            repository.addMovie(play.get(this.index).getpName(), play.get(this.index).getName(), play.get(this.index).getPlay());
        }
    }
}
