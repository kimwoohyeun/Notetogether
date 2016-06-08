package com.example.y.notetogether.Activity.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.y.notetogether.Activity.DB.ContentCountDB;
import com.example.y.notetogether.Activity.DB.ContentDB;
import com.example.y.notetogether.Activity.DB.ContentMyroomDB;
import com.example.y.notetogether.Activity.DB.Contents;
import com.example.y.notetogether.Activity.Multie.RoomContents;
import com.example.y.notetogether.Activity.Multie.RoomSearchDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public void myroom(String sessionKey, Callback<ArrayList<ContentMyroomDB>> callback) throws IOException {
        Call<ArrayList<ContentMyroomDB>> call = service.myroom(sessionKey);
        call.enqueue(callback);
    }
    public void participateRoom(Group group, Callback<String> callback) throws IOException {
        Call<String> call = service.participateRoom(group.name, group.sessionkey);
        call.enqueue(callback);
    }
    public ArrayList<ContentDB> getRoomContents(){
        //call이라는 보따리를 만듬
        Call<ArrayList<ContentDB>> call = service.getRoomContents();
        //execute에서 서버에 정보를 요청하고 body에서 요청을 응답 받음  이것들은 call에 넣음
        try {
            // Log.e("Proxyasdf", String.valueOf(call.execute().body().get(0).getName()));//.body()));
            ArrayList<ContentDB> contents = call.execute().body();
            return contents;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<ContentCountDB> getRoomCount(){
        //call이라는 보따리를 만듬
        Call<ArrayList<ContentCountDB>> callcount = service.getRoomCount();
        //execute에서 서버에 정보를 요청하고 body에서 요청을 응답 받음  이것들은 call에 넣음
        try {
          // Log.e("Proxywoo", String.valueOf(callcount.execute().body()));//.body()));
            ArrayList<ContentCountDB> contents = callcount.execute().body();
            return contents;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
