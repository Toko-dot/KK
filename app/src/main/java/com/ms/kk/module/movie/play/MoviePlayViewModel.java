package com.ms.kk.module.movie.play;

import android.text.TextUtils;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.ms.kk.base.BaseViewModel;
import com.ms.kk.model.net.entity.respond.Comment;
import com.ms.kk.model.net.entity.respond.DramaItem;
import com.ms.kk.model.net.entity.respond.MovieListItem;

import java.util.List;

public class MoviePlayViewModel extends BaseViewModel<MoviePlayRepository> {

    public List<MovieListItem> movieItemList;

    public DramaItem dramaItem;

    ObservableBoolean playListError = new ObservableBoolean();

    public MutableLiveData<Void> queryPlayListSuccess = new MutableLiveData<>();

    public List<Comment> commentItemList;

    public ObservableBoolean commentListError = new ObservableBoolean();

    public MutableLiveData<Void> queryCommentListSuccess = new MutableLiveData<>();

    public MutableLiveData<MovieListItem> currentMovie = new MutableLiveData<>();

    public ObservableField<String> postComment = new ObservableField<>();

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
                movieItemList = data;
                playListError.set(false);
                queryPlayListSuccess.setValue(null);
            }

            @Override
            protected void handleError(String extra, int what) {
                playListError.set(true);
            }

            @Override
            protected void handleEmpty() {
                playListError.set(true);
            }
        });


        addSource(repository.commentList, new ViewModelObserver<List<Comment>>() {
            @Override
            protected void handleInit() {

            }

            @Override
            protected void handleSuccess(List<Comment> data) {
                commentItemList = data;
                commentListError.set(false);
                queryCommentListSuccess.setValue(null);
            }

            @Override
            protected void handleError(String extra, int what) {
                commentListError.set(true);
            }

            @Override
            protected void handleEmpty() {
                commentListError.set(true);
            }
        });


        addSource(repository.addComment, new ViewModelObserver<Boolean>() {
            @Override
            protected void handleInit() {

            }

            @Override
            protected void handleSuccess(Boolean data) {
                queryCommentList();
                postComment.set(null);
            }

            @Override
            protected void handleError(String extra, int what) {
                setToast(extra);
            }

            @Override
            protected void handleEmpty() {

            }
        });
    }

    public void queryDramaList() {
        repository.queryDramaList(dramaItem.get_id());
    }

    public void queryCommentList() {
        if (currentMovie.getValue() == null) {
            return;
        }
        repository.queryCommentList(currentMovie.getValue().get_id(), 1);
    }

    public void addComment() {
        if (userInfo.get() == null) {
            setToast("请先登录");
            return;
        }
        if (currentMovie.getValue() == null) {
            setToast("请先选择评论的视频");
            return;
        }

        if (TextUtils.isEmpty(postComment.get())) {
            setToast("请先填写评论的内容");
            return;
        }

        repository.addComment(userInfo.get().getUid(), currentMovie.getValue().get_id(), postComment.get());
    }


}
