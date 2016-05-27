package com.example.y.notetogether.Activity.Service;

/**
 * Created by kimwoohyeun on 2016-04-02.
 */
import com.example.y.notetogether.Activity.DB.Contents;
import com.example.y.notetogether.Activity.Login.LoginResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface MemoService {
    @FormUrlEncoded
    @POST("/user/create")
    public Call<String> createUser(@Field("email") String id, @Field("password") String pwd);

    @FormUrlEncoded
    @POST("/user/login")
    public Call<LoginResponse> login(@Field("email") String id, @Field("password") String pwd);

    @FormUrlEncoded
    @POST("/user/sessionLogin")
    public Call<User> loginBySession(@Field("sessionKey") String sessionKey);

    @FormUrlEncoded
    @POST("/user/sessionLogout")
    public Call<String> logoutBySession(@Field("sessionKey") String sessionKey);

    @FormUrlEncoded
    @POST("/group/makeroom")
    public Call<String> makeRoom(@Field("roomName") String roomName,@Field("sessionKey") String sessionKey);

    @FormUrlEncoded
    @POST("/memo/backup")
    public Call<String> setcontents(@Field("title") String title, @Field("content") String content,@Field("time") String time,@Field("color") int color);

    @FormUrlEncoded
    @POST("/memo/backup")
    public Call<String> setcontentlist(@Field("contentlist") ArrayList<Contents> contentses);

    @GET("/Memo/contents")
    Call<List<Contents>> getContents();



}
