package com.example.y.notetogether.Activity;
import java.util.logging.Level;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kimwoohyeun on 2016-05-18.
 */
public class Network {
    private static Network network;
    private UserProxy userProxy;
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
    }

    public UserProxy getRegisterProxy() {
        return new UserProxy(retrofit);
    }

    public UserProxy getUserProxy() {
        return userProxy;
    }
}