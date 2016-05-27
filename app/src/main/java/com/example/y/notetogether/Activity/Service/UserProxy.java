package com.example.y.notetogether.Activity.Service;

/**
 * Created by kimwoohyeun on 2016-05-20.
 */

import android.util.Log;

import com.example.y.notetogether.Activity.DB.Contents;
import com.example.y.notetogether.Activity.Login.LoginResponse;
import com.example.y.notetogether.Activity.Service.MemoService;
import com.example.y.notetogether.Activity.Service.User;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class UserProxy {
    private MemoService service;
    public UserProxy(Retrofit retrofit) {
        service = retrofit.create(MemoService.class);
    }
    public void registerUser(User user, Callback<String> callback) throws IOException {
        Call<String> call = service.createUser(user.email, user.password);
        call.enqueue(callback);
    }
    public void login(User user, Callback<LoginResponse> callback) throws IOException {
        Call<LoginResponse> call = service.login(user.email, user.password);
        call.enqueue(callback);
    }

    public void loginBySession(String sessionKey, Callback<User> callback) throws IOException {
        Call<User> call = service.loginBySession(sessionKey);
        call.enqueue(callback);
    }
    public void logoutBySession(String sessionKey, Callback<String> callback) throws IOException {
        Call<String> call = service.logoutBySession(sessionKey);
        call.enqueue(callback);
    }


}
