package com.example.y.notetogether.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.y.notetogether.R;
import android.app.Activity;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kimwoohyeun on 2016-05-15.
 */
public class LoginActivity extends Activity {
    Handler handler = new Handler();
    private EditText edit_id;
    private EditText edit_pwd;
    private User user;
    private Button Btn_log;
    private Button Btn_log_register;
    private Button Btn_log_cancle;
    private Network network;
    private String id;
    private String pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edit_id = (EditText) findViewById(R.id.edit_id);
        edit_pwd = (EditText) findViewById(R.id.edit_pwd);
        Btn_log = (Button) findViewById(R.id.btn_login);
        Btn_log_register = (Button) findViewById(R.id.btn_register);
        Btn_log_cancle = (Button) findViewById(R.id.btn_login_cancle);
        Toast.makeText(LoginActivity.this, "로그인화면", Toast.LENGTH_SHORT).show();
        network = Network.getNetworkInstance();
        Btn_log_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Btn_log.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                id = edit_id.getText().toString();
                pwd = edit_pwd.getText().toString();
                user = new User(id, pwd);

                try {
                    network.getUserProxy().login(user, new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()) {
                                handleResponse(response.body());
                                Toast.makeText(LoginActivity.this, "우", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "현", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "이", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "김", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Btn_log_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,Register1Activity.class);
                startActivity(intent);
            }
        });
    }
    public void handleResponse(String responseBody) {
        Log.i("login", responseBody);
        if (responseBody.equals("Create User Success")) {
            Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(this, FindBuddyActivity.class);
            //startActivity(intent);

        }else if(responseBody.equals("ID Already EXIST")){
            Toast.makeText(this, "동일한 아이디가 있습니다", Toast.LENGTH_SHORT).show();
        }else {
            Log.i("login", "" + responseBody.equals("Login_Success"));
            Toast.makeText(this, "에러", Toast.LENGTH_SHORT).show();
        }
    }

}

