package com.example.y.notetogether.Activity.Service;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by kimwoohyeun on 2016-06-06.
 */
public class ImageProxy {
    private MemoService service;
    public ImageProxy(Retrofit retrofit) {
        service = retrofit.create(MemoService.class);
    }
    public void upload( MultipartBody.Part file, Callback<ResponseBody> callback) throws IOException {
        Call<ResponseBody> call = service.upload( file);
        call.enqueue(callback);
    }
}
