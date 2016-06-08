package com.example.y.notetogether.Activity.FileupLoad;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.y.notetogether.Activity.Service.MemoService;
import com.example.y.notetogether.Activity.Service.Network;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kimwoohyeun on 2016-06-06.
 */
public class uploadFile {
    private Network network;
    private void uploadFile(Uri fileUri) {
        network = Network.getNetworkInstance();

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = new File(fileUri.getPath());

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("picture", file.getName(), requestFile);


        // finally, execute the request
        try {
            network.getImageProxy().upload(body, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call,
                                       Response<ResponseBody> response) {
                    Log.v("Upload", "success");
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("Upload error:", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
