package com.ms.kk.module.movie.play;

import androidx.lifecycle.MutableLiveData;

import com.ms.kk.base.BaseRepository;
import com.ms.kk.base.RepositoryRespond;
import com.ms.kk.model.net.entity.respond.BaseEntity;
import com.ms.kk.model.net.entity.respond.Comment;
import com.ms.kk.model.net.entity.respond.DramaItem;
import com.ms.kk.model.net.entity.respond.MovieListItem;

import java.util.List;

public class MoviePlayRepository extends BaseRepository {
    MutableLiveData<RepositoryRespond<List<MovieListItem>>> list = new MutableLiveData<>();
    MutableLiveData<RepositoryRespond<Boolean>> addComment=new MutableLiveData<>();
    MutableLiveData<RepositoryRespond<List<Comment>>> commentList = new MutableLiveData<>();

    public void addComment(int uid,int mid,String text){
        addSource(commonApi.addComment(uid, mid, text), new SimpleRepositoryObserver<Object>() {
            @Override
            protected void handleSuccess(BaseEntity<Object> data) {
                addComment.setValue(RepositoryRespond.createSuccess(true));
            }

            @Override
            protected void handleError(int httpCode, String extra) {
                addComment.setValue(RepositoryRespond.createError(extra,httpCode));
            }

            @Override
            protected void handleEmpty() {
                super.handleEmpty();
                addComment.setValue(RepositoryRespond.createError("评论失败"));
            }
        });
    }


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


    public void queryCommentList(int mid,int page) {
        addSource(commonApi.queryCommentList(mid,page), new SimpleRepositoryObserver<List<Comment>>() {
                    @Override
                    protected void handleSuccess(BaseEntity<List<Comment>> data) {
                        super.handleSuccess(data);
                        if (data.getData()==null||data.getData().size()==0){
                            commentList.setValue(RepositoryRespond.createEmpty());
                        }else {
                            commentList.setValue(RepositoryRespond.createSuccess(data.getData()));
                        }

                    }

                    @Override
                    protected void handleEmpty() {
                        super.handleEmpty();
                        commentList.setValue(RepositoryRespond.createEmpty());
                    }

                    @Override
                    protected void handleError(int httpCode, String extra) {
                        super.handleError(httpCode, extra);
                        commentList.setValue(RepositoryRespond.createError(extra, httpCode));
                    }
                }
        );
    }

}
