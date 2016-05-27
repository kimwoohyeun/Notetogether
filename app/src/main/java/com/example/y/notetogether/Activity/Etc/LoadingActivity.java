package com.example.y.notetogether.Activity.Etc;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;

import com.example.y.notetogether.R;

import java.util.List;
import java.util.logging.Handler;
//import android.os.Handler;
import java.util.logging.LogRecord;

import retrofit2.Call;

/**
 * Created by kimwoohyeun on 2016-04-18.
 */

public class LoadingActivity extends Activity  {
    protected void onCreate(Bundle saveInstanceState) {
        {

                super.onCreate(saveInstanceState);
                setContentView(R.layout.loading_splash);
                android.os.Handler handler = new android.os.Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        finish();
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    }
                };
                handler.sendEmptyMessageDelayed(0, 2000);


        }
    }
    public void onBackPressed(){}

}
