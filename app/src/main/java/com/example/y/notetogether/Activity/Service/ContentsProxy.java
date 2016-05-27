package com.example.y.notetogether.Activity.Service;

import android.util.Log;

import com.example.y.notetogether.Activity.DB.Contents;
import com.example.y.notetogether.Activity.DB.Dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by kimwoohyeun on 2016-05-22.
 */
public class ContentsProxy {
    private MemoService service;
    public ContentsProxy(Retrofit retrofit) {
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
    public void setContents(Contents contentses, Callback<String> callback) throws IOException {
        Call<String> call = service.setcontents(contentses.getTitle(),contentses.getContent(),contentses.getTime(),contentses.getColor());
        call.enqueue(callback);
    }
    public void setContentlist(ArrayList<Contents> contentses, Callback<String> callback) throws IOException {
        Call<String> call = service.setcontentlist(contentses);
        call.enqueue(callback);
    }
}