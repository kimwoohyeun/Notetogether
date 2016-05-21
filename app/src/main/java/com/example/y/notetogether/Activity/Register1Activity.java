package com.example.y.notetogether.Activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.y.notetogether.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kimwoohyeun on 2016-05-20.
 */
public class Register1Activity extends AppCompatActivity implements View.OnClickListener{
    private Button Btn_ok;
    private Button Btn_cancle;
    private String id;
    private String pwd;
    private EditText edit_id;
    private EditText edit_pwd;
    User user;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Btn_ok = (Button)findViewById(R.id.btn_register_ok);
        Btn_ok.setOnClickListener(this);
        Btn_cancle = (Button)findViewById(R.id.btn_register_cancle);
        Btn_cancle.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_register_ok: {
                Network network = Network.getNetworkInstance();
                edit_id = (EditText) findViewById(R.id.editText_register_id);
                edit_pwd = (EditText) findViewById(R.id.editText_register_password);
                id = edit_id.getText().toString();
                pwd = edit_pwd.getText().toString();
                user = new User(id, pwd);
                try{
                    UserProxy userProxy = network.getUserProxy();
                    userProxy.registerUser(user, new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.isSuccessful()) {
                                Log.i("UserProxy", "Network Success And " + response.body());

                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.e("TEST", "FAIL!!!" + t);
                        }
                    });
                }catch (Exception e){

                }

                break;
            }
            case R.id.btn_register_cancle: {
                finish();
                break;
            }

        }

    }
}
