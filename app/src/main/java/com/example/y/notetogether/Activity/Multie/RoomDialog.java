package com.example.y.notetogether.Activity.Multie;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.y.notetogether.Activity.Service.Group;
import com.example.y.notetogether.Activity.Service.GroupProxy;
import com.example.y.notetogether.Activity.Service.Network;
import com.example.y.notetogether.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kimwoohyeun on 2016-05-26.
 */
public class RoomDialog extends Dialog implements View.OnClickListener{
    private EditText editText;
    private ImageView cancle;
    private Button ok;
    private String room_name;
    private Network network;
    private Group group;
    private GroupProxy groupProxy;
    private String sessionKey;

    public RoomDialog(Context context,String sessionkey) {
        super(context);
        this.sessionKey = sessionkey;
    }
    public String getRoomName(){
        return room_name;
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
        //다이얼로그 흐리게
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        //커스텀 다이얼로그 레이아웃
        setContentView(R.layout.activity_groupmemo_room);
        cancle = (ImageView) findViewById(R.id.btn_room_exit);
        ok = (Button)findViewById(R.id.btn_room_ok);
        editText =  (EditText)findViewById(R.id.edittext_room_name);
        ok.setOnClickListener(this);
        cancle.setOnClickListener(this);
        //위치 조정
        this.getWindow().setGravity(Gravity.TOP);
        //Dialog 밖을 터치 했을 경우 Dialog 사라지게 하기
        //this.setCanceledOnTouchOutside(true);
        //테두리 투명하게
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_room_exit : {
                cancel();
                break;
            }
            case R.id.btn_room_ok:{
                room_name = editText.getText().toString();
                network = Network.getNetworkInstance();
                group = new Group(room_name,sessionKey );
                try{
                    network.getGroupProxy().makeRoom(group, new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), response.body(), Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getContext(), "방생성 Faileure", Toast.LENGTH_SHORT).show();
                        }
                    });

                }catch (Exception e){
                    Toast.makeText(getContext(), "방생성 Exception", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }
}
