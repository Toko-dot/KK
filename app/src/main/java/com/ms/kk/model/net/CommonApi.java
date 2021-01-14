package com.ms.kk.model.net;

import androidx.lifecycle.LiveData;

import com.ms.kk.base.ApiRespond;
import com.ms.kk.model.net.entity.respond.Comment;
import com.ms.kk.model.net.entity.respond.DramaItem;
import com.ms.kk.model.net.entity.respond.MovieListItem;
import com.ms.kk.model.net.entity.respond.Type;
import com.ms.kk.model.net.entity.respond.BaseEntity;
import com.ms.kk.model.net.entity.respond.LoginInfo;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CommonApi {

    @POST("user/register")
    @FormUrlEncoded
    LiveData<ApiRespond<BaseEntity<Object>>> register(@Field("account") String account, @Field("pwd") String pwd);

    @POST("user/login")
    @FormUrlEncoded
    LiveData<ApiRespond<BaseEntity<LoginInfo>>> login(@Field("account") String account, @Field("pwd") String pwd);

    @POST("user/update/avatar")
    @FormUrlEncoded
    LiveData<ApiRespond<BaseEntity<String>>> updateAvatar(@Field("uid") int uid, @Field("avatar") String avatar);


    @POST("user/update/name")
    @FormUrlEncoded
    LiveData<ApiRespond<BaseEntity<Object>>> updateName(@Field("uid") int uid, @Field("name") String name);


    @POST("user/update/age")
    @FormUrlEncoded
    LiveData<ApiRespond<BaseEntity<Object>>> updateAge(@Field("uid") int uid, @Field("age") int avatar);


    @POST("user/update/sex")
    @FormUrlEncoded
    LiveData<ApiRespond<BaseEntity<Object>>> updateSex(@Field("uid") int uid, @Field("sex") int sex);


    @POST("type/list")
    LiveData<ApiRespond<BaseEntity<List<Type>>>> queryType();

    @POST("drama/query/list")
    @FormUrlEncoded
    LiveData<ApiRespond<BaseEntity<List<DramaItem>>>> queryDramaList(@Field("tid") int tid, @Field("page") int page);


    @POST("movie/query/list")
    @FormUrlEncoded
    LiveData<ApiRespond<BaseEntity<List<MovieListItem>>>> queryPlayList(@Field("pid") int pid);


    @POST("comment/add")
    @FormUrlEncoded
    LiveData<ApiRespond<BaseEntity<Object>>> addComment(@Field("uid") int uid, @Field("mid") int mid, @Field("text") String text);


    @POST("comment/query/list")
    @FormUrlEncoded
    LiveData<ApiRespond<BaseEntity<List<Comment>>>> queryCommentList(@Field("mid") int mid, @Field("page") int page);
}
