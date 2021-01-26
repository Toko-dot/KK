package com.ms.kk.module.movie.play;

import android.text.TextUtils;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.ms.kk.base.BaseViewModel;
import com.ms.kk.model.net.entity.respond.Comment;
import com.ms.kk.model.net.entity.respond.DramaItem;
import com.ms.kk.model.net.entity.respond.LoginInfo;
import com.ms.kk.model.net.entity.respond.MovieListItem;

import java.util.List;

public class MoviePlayViewModel extends BaseViewModel<MoviePlayRepository> {


    public int commentCount = 0;

    public int rStart = -1;

    public List<MovieListItem> movieItemList;

    public DramaItem dramaItem;

    public ObservableBoolean playListError = new ObservableBoolean();

    public MutableLiveData<Void> queryPlayListSuccess = new MutableLiveData<>();

    public List<Comment> commentItemList;

    public ObservableBoolean commentListError = new ObservableBoolean();

    public ObservableBoolean commentListEmpty = new ObservableBoolean();

    public ObservableBoolean noMoreComment = new ObservableBoolean();

    public MutableLiveData<MovieListItem> currentMovie = new MutableLiveData<>();

    public ObservableField<String> postComment = new ObservableField<>();

    public MutableLiveData<Void> finishRequestCommentList = new MutableLiveData<>();


    public MutableLiveData<Void> refreshComment = new MutableLiveData<>();

    public MutableLiveData<Void> loadMoreComment = new MutableLiveData<>();


    public MoviePlayViewModel(DramaItem dramaItem) {
        this.dramaItem = dramaItem;
        queryDramaList();

    }

    @Override
    protected void registerObserver() {
        addSource(repository.list, new SimpleViewModelObserver<List<MovieListItem>>() {


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


        addSource(repository.commentList, new SimpleViewModelObserver<List<Comment>>() {
            @Override
            protected void handleInit() {
                commentListError.set(false);
                commentListEmpty.set(false);
                noMoreComment.set(false);
            }

            @Override
            protected void handleSuccess(List<Comment> data) {
                if (rStart == 0) {
                    commentItemList = data;
                    refreshComment.setValue(null);
                    commentCount = data.size();
                } else {
                    commentItemList = data;
                    loadMoreComment.setValue(null);
                    commentCount += data.size();
                }
            }

            @Override
            protected void handleError(String extra, int what) {
                if (rStart == 0) {
                    commentItemList = null;

                    commentListError.set(true);

                    refreshComment.setValue(null);

                    commentCount = 0;
                }

            }

            @Override
            protected void handleEmpty() {
                if (rStart == 0) {

                    commentItemList = null;

                    commentCount = 0;

                    commentListEmpty.set(true);

                    refreshComment.setValue(null);
                } else {
                    noMoreComment.set(true);
                }

            }

            @Override
            protected void handleRequestEnd() {
                finishRequestCommentList.setValue(null);
                rStart = -1;
            }
        });


        addSource(repository.addComment, new SimpleViewModelObserver<Boolean>() {

            @Override
            protected void handleSuccess(Boolean data) {
                queryCommentList(0);
                postComment.set(null);
            }

            @Override
            protected void handleError(String extra, int what) {
                setToast(extra);
            }
        });
    }

    public void queryDramaList() {
        repository.queryDramaList(dramaItem.get_id());
    }

    public void queryCommentList(int start) {
        if (rStart != -1 || currentMovie.getValue() == null) {
            finishRequestCommentList.setValue(null);
            return;
        }
        rStart = start;
        repository.queryCommentList(currentMovie.getValue().get_id(), start);
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

    public void addFeedBack(String qa) {
        LoginInfo loginInfo = userInfo.get();
        repository.addFeedBack(loginInfo == null ? -1 : loginInfo.getUid(), qa);
    }


}
