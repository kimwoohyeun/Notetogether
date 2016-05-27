package com.example.y.notetogether.Activity.Service;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by kimwoohyeun on 2016-05-26.
 */
public class GroupProxy {
    private MemoService service;
    public GroupProxy(Retrofit retrofit) {
        service = retrofit.create(MemoService.class);
    }
    public void makeRoom(Group group, Callback<String> callback) throws IOException {
        Call<String> call = service.makeRoom(group.name, group.sessionkey);
        call.enqueue(callback);
    }
}
