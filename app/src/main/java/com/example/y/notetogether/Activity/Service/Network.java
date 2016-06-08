package com.example.y.notetogether.Activity.Service;

import android.media.Image;

import com.example.y.notetogether.Activity.DB.Contents;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kimwoohyeun on 2016-05-18.
 */
public class Network {
    private static Network network;
    private UserProxy userProxy;
    private GroupProxy groupProxy;
    private ImageProxy imageProxy;
    private ContentsProxy contentsProxy;
    private Retrofit retrofit;
    public static Network getNetworkInstance() {
        if(network == null) {
            network = new Network();
        }
        return network;
    }
    public Network() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        retrofit = new Retrofit.Builder().baseUrl("http://52.79.112.106:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        userProxy = new UserProxy(retrofit);
        contentsProxy = new ContentsProxy(retrofit);
        groupProxy = new GroupProxy(retrofit);
        imageProxy = new ImageProxy(retrofit);
    }

    public UserProxy getRegisterProxy() {
        return new UserProxy(retrofit);
    }

    public UserProxy getUserProxy() {
        return userProxy;
    }
    public GroupProxy getGroupProxy(){
        return groupProxy;
    }
    public ContentsProxy RegisterContentsProxy() { return contentsProxy;}
    public ImageProxy getImageProxy() { return imageProxy;}
}