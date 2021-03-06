package com.example.y.notetogether.Activity.Service;

/**
 * Created by kimwoohyeun on 2016-04-02.
 */
import com.example.y.notetogether.Activity.DB.ContentCountDB;
import com.example.y.notetogether.Activity.DB.ContentDB;
import com.example.y.notetogether.Activity.DB.ContentMyroomDB;
import com.example.y.notetogether.Activity.DB.Contents;
import com.example.y.notetogether.Activity.DB.Contentsss;
import com.example.y.notetogether.Activity.Login.LoginResponse;
import com.example.y.notetogether.Activity.Multie.RoomContents;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


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
    @POST("/group/myRoom")
    public Call<ArrayList<ContentMyroomDB>> myroom(@Field("sessionKey") String sessionKey);

    @FormUrlEncoded
    @POST("/group/makeroom")
    public Call<String> makeRoom(@Field("roomName") String roomName,@Field("sessionKey") String sessionKey);

    @FormUrlEncoded
    @POST("/group/participateroom")
    public Call<String> participateRoom(@Field("roomName") String roomName,@Field("sessionKey") String sessionKey);

    @Multipart
    @POST("/upload")
    Call<ResponseBody> upload(@Part MultipartBody.Part file);

    @GET("/group/searchroom")
    Call<ArrayList<ContentDB>> getRoomContents();

    @GET("/group/searchcount")
    Call<ArrayList<ContentCountDB>> getRoomCount();

    @FormUrlEncoded
    @POST("/memo/backup")
    public Call<String> setcontents(@Field("title") String title, @Field("content") String content,@Field("time") String time,@Field("color") int color);

    @FormUrlEncoded
    @POST("/memo/backup")
    public Call<String> setcontentlist(@Field("contentlist") JsonObject contentses);

    @GET("/Memo/contents")
    Call<List<Contents>> getContents();



}
