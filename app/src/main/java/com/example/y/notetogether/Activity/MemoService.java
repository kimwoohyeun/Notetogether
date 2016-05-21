package com.example.y.notetogether.Activity;

/**
 * Created by kimwoohyeun on 2016-04-02.
 */
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface MemoService {
    @FormUrlEncoded
    @POST("/user/create")
    public Call<String> createUser(@Field("id") String id, @Field("password") String pwd);
    @GET("/Memo/contents")
    Call<List<Contents>> getContents();
    @FormUrlEncoded
    @POST("/user/login")
    public Call<String> login(@Field("id") String id, @Field("password") String pwd);

}
