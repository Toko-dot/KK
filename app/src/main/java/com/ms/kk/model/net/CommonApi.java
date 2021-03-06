package com.ms.kk.model.net;

import androidx.lifecycle.LiveData;

import com.ms.kk.base.ApiRespond;
import com.ms.kk.model.net.entity.respond.Comment;
import com.ms.kk.model.net.entity.respond.DramaItem;
import com.ms.kk.model.net.entity.respond.MovieListItem;
import com.ms.kk.model.net.entity.respond.Type;
import com.ms.kk.model.net.entity.respond.BaseEntity;
import com.ms.kk.model.net.entity.respond.LoginInfo;
import com.ms.kk.model.net.entity.respond.Version;

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
    LiveData<ApiRespond<BaseEntity<List<DramaItem>>>> queryDramaList(@Field("tid") int tid, @Field("start") int start);


    @POST("movie/query/list")
    @FormUrlEncoded
    LiveData<ApiRespond<BaseEntity<List<MovieListItem>>>> queryPlayList(@Field("pid") int pid);


    @POST("comment/add")
    @FormUrlEncoded
    LiveData<ApiRespond<BaseEntity<Object>>> addComment(@Field("uid") int uid, @Field("mid") int mid, @Field("text") String text);


    @POST("comment/query/list")
    @FormUrlEncoded
    LiveData<ApiRespond<BaseEntity<List<Comment>>>> queryCommentList(@Field("mid") int mid, @Field("start") int start);

    @POST("drama/search")
    @FormUrlEncoded
    LiveData<ApiRespond<BaseEntity<List<DramaItem>>>> search(@Field("search") String tid, @Field("start") int start);


    @POST("drama/searchWithUid")
    @FormUrlEncoded
    LiveData<ApiRespond<BaseEntity<List<DramaItem>>>> search(@Field("uid") int uid,@Field("search") String tid, @Field("start") int start);


    @POST("drama/search/hot")
    LiveData<ApiRespond<BaseEntity<String>>> querySearchHot();

    @POST("version/query")
    LiveData<ApiRespond<BaseEntity<Version>>> queryVersion();


    @POST("drama/add")
    @FormUrlEncoded
    LiveData<ApiRespond<BaseEntity<Object>>> addDrama(@Field("type") String type, @Field("name") String name, @Field("thumb") String thumb,@Field("brief")String brief);


    @POST("movie/add")
    @FormUrlEncoded
    LiveData<ApiRespond<BaseEntity<Object>>> addMovie(@Field("pName") String pName, @Field("name") String name, @Field("play") String play,@Field("thumb")String thumb);


    @POST("feedback/add")
    @FormUrlEncoded
    LiveData<ApiRespond<BaseEntity<Object>>> addFeedBack(@Field("uid") int uid, @Field("qa") String qa);


}
