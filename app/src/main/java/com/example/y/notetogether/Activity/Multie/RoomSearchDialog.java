package com.example.y.notetogether.Activity.Multie;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.y.notetogether.R;

/**
 * Created by kimwoohyeun on 2016-05-27.
 */
public class RoomSearchDialog extends Dialog implements View.OnClickListener{
    private String sessionKey;
    private ImageView cancle;
    private Button ok;
    private EditText editText;
    public RoomSearchDialog(Context context, String sessionkey) {
        super(context);
        this.sessionKey = sessionkey;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        //타이틀바 없애기
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        //커스텀 다이얼로그 레이아웃
        setContentView(R.layout.activity_groupmemo_roomsearch);
        cancle = (ImageView) findViewById(R.id.btn_roomsearch_exit);
        ok = (Button)findViewById(R.id.btn_roomsearch_ok);
        editText =  (EditText)findViewById(R.id.edittext_roomsearch_name);
        ok.setOnClickListener(this);
        cancle.setOnClickListener(this);
        //위치 조정
        this.getWindow().setGravity(Gravity.TOP);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_room_exit: {
                cancel();
                break;
            }

        }

    }
}
