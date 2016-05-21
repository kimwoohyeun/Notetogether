package com.example.y.notetogether.Activity;

/**
 * Created by kimwoohyeun on 2016-05-20.
 */

import android.util.Log;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserProxy {
    private MemoService service;
    public UserProxy(Retrofit retrofit) {
        service = retrofit.create(MemoService.class);
    }
    public void registerUser(User user, Callback<String> callback) throws IOException {
        Call<String> call = service.createUser(user.id, user.password);
        call.enqueue(callback);
    }
    public void login(User user, Callback<String> callback) throws IOException {
        Call<String> call = service.login(user.id, user.password);
        call.enqueue(callback);
    }
}

/*
public UserProxy(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://localhost:1000/").addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(MemoService.class);
    }
public List<Contents> getContents(){
        //call이라는 보따리를 만듬
        Call<List<Contents>> call = service.getContents();
        try{
            //execute에서 서버에 정보를 요청하고 body에서 요청을 응답 받음  이것들은 call에 넣음
            List<Contents> contents = call.execute().body();
            return contents;
        }catch (IOException e){
            Log.e("Proxy", e.getMessage());
        }
        return null;
    }
 */

