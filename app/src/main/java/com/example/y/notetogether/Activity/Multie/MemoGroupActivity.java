package com.example.y.notetogether.Activity.Multie;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.y.notetogether.Activity.DB.ContentBoardAdapter;
import com.example.y.notetogether.Activity.DB.Contents;
import com.example.y.notetogether.Activity.DB.Dao;
import com.example.y.notetogether.Activity.Login.LoginActivity;
import com.example.y.notetogether.Activity.Service.Network;
import com.example.y.notetogether.Activity.Service.User;
import com.example.y.notetogether.Activity.Service.UserProxy;
import com.example.y.notetogether.Activity.Single.MemoActivity;
import com.example.y.notetogether.Activity.Single.MemoActivity2;
import com.example.y.notetogether.R;
import com.facebook.FacebookSdk;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kimwoohyeun on 2016-05-21.
 */
public class MemoGroupActivity extends AppCompatActivity implements View.OnClickListener{
        private ImageView img_login;
        private Button Btn_new;
        private Button Btn_edit;
        private Button Btn_setting;
        private Button Btn_mode;
        private Button Btn_home;
        private Button Btn_logout;
        private TextView Text_email;
        private EditText textView_serch;
        private RecyclerView recyclerView;
        private Dao dao;
        private RelativeLayout serch;
        private LinearLayout inflate;
        private LinearLayout cardview;
        private LinearLayout settingLayout;
        private int editbtn_condition = 0;
        private int homebtn_condition = 0;
        //new Activity
        private String time;
        private EditText content;
        private Button send;
        private View exit;
        private Network network;
        private int color=4;
        private Button Btn_color1;
        private Button Btn_color2;
        private Button Btn_color3;
        private Button Btn_color4;
        private Button Btn_color5;
        private Button Btn_room;
        private Button Btn_roomsearch;
        private User user;
        private UserProxy userProxy;
        private String sessionKey;
        private RoomDialog roomDialog;
        private RoomSearchDialog roomSearchDialog;
        private LinearLayout GroupRoomLayout;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //페이스북 연동
            FacebookSdk.sdkInitialize(getApplicationContext());
            setContentView(R.layout.activity_groupmemo);
            //레이아웃 아이디 설정
            cardview = (LinearLayout)findViewById(R.id.cardview_main);
            inflate = (LinearLayout)findViewById(R.id.settinginflate_main);
            //로그인되어있는 유저 불러오기
            network = Network.getNetworkInstance();
            userProxy = network.getUserProxy();
            sessionKey = getSessionKey();
            if (!sessionKey.equals("ERROR")) {
                sessionLogin(sessionKey);
            }
            //리스트 생성
            recyclerView = (RecyclerView)findViewById(R.id.recycleView_memo);
            dao = new Dao(this);
            //카드뷰 설정
            LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            //네트워크 설정
            network = Network.getNetworkInstance();
            //리스트에 어뎁터 삽입
            final ContentBoardAdapter contentBoardAdapter = new ContentBoardAdapter(this,dao.getContents(),R.layout.activity_memo);
            recyclerView.setAdapter(contentBoardAdapter);
            //방 생성 레이아웃
            GroupRoomLayout = (LinearLayout)findViewById(R.id.GroupRoom_main);
            //방 생성 버튼 설정
            Btn_room = (Button)findViewById(R.id.btn_gruop_room);
            Btn_room.setOnClickListener(this);
            //방 검색 버튼 설정
            Btn_roomsearch = (Button)findViewById(R.id.btn_group_roomsearch);
            Btn_roomsearch.setOnClickListener(this);
            //새 메모 버튼 설정
            Btn_new = (Button)findViewById(R.id.btn_main_new);
            Btn_new.setOnClickListener(this);
            //수정 버튼 설정
            Btn_edit = (Button)findViewById(R.id.btn_main_edit);
            Btn_edit.setOnClickListener(this);
            //세팅 버튼 설정
            Btn_setting = (Button)findViewById(R.id.btn_main_setting);
            Btn_setting.setOnClickListener(this);
            //모드 버튼 설정
            Btn_mode = (Button)findViewById(R.id.btn_main_mode);
            Btn_mode.setOnClickListener(this);
            //홈 버튼설정
            Btn_home = (Button)findViewById(R.id.btn_main_home);
            Btn_home.setOnClickListener(this);
            //검색창, 체크박스 숨기기
            serch = (RelativeLayout)findViewById(R.id.serchview_main);
            if(editbtn_condition==0) {
                serch.setVisibility(View.GONE);
            }
            //검색 필터링
            textView_serch = (EditText)findViewById(R.id.textView_main_serch);
            textView_serch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String text = textView_serch.getText().toString().toLowerCase(Locale.getDefault());
                    contentBoardAdapter.filter(text);
                    recyclerView.setAdapter(contentBoardAdapter);
                }
            });
        }

    private void sessionLogin(String sessionKey) {
        try {
            userProxy.loginBySession(sessionKey, new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        handleResponse_session(response.body());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(MemoGroupActivity.this, "Network error1", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            Toast.makeText(MemoGroupActivity.this, "Network error2", Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", e.toString());
        }
    }
    private void handleResponse_session(User user) {
        this.user = new User(user.email,user.password);
    }


    @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.btn_gruop_room:{

                    roomDialog = new RoomDialog(this,sessionKey);
                    //커스텀 다이얼로그 객체에 Dismiss 리스너 설정
                    //커스텀 다이얼로그가 사라졌을때 취할 행동들
                    roomDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }
                    });
                    //커스텀 다이얼로그 객에체 Cancle 리스너 설정
                    roomDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                        }
                    });


                    roomDialog.show();

                    break;
                }
                case R.id.btn_group_roomsearch:{

                    roomSearchDialog = new RoomSearchDialog(this,sessionKey);
                    //커스텀 다이얼로그 객체에 Dismiss 리스너 설정
                    //커스텀 다이얼로그가 사라졌을때 취할 행동들
                    roomSearchDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }
                    });
                    //커스텀 다이얼로그 객에체 Cancle 리스너 설정
                    roomSearchDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                        }
                    });
                    //Dialog 밖을 터치 했을 경우 Dialog 사라지게 하기
                    roomSearchDialog.setCanceledOnTouchOutside(true);
                    //Dialog 자체 배경을 투명하게 하기
                    roomSearchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    roomSearchDialog.show();

                    break;
                }

                case R.id.btn_main_edit: {
                    open_serch_inflateLayout();
                    break;

                }
                case R.id.btn_main_new:{
                    open_new_inflateLayout();
                    break;
                }
                case R.id.btn_main_setting:{
                    editbtn_condition = 0;
                    open_setting_inflateLayout();
                    break;
                }
                case R.id.btn_Write_send: {
                    Intent intent = new Intent(this, MemoActivity2.class);
                    Log.i("SEND", content.toString());
                    dao.insertContents(exportContents());
                    startActivity(intent);
                    finish();
                    break;
                }
                case R.id.btn_main_home:{
                    open_home_inflateLayout();
                    break;
                }
                case R.id.btn_Write_exit:{
                    Intent intent = new Intent(this, MemoActivity2.class);
                    Log.i("EXIT", content.toString());
                    startActivity(intent);
                    finish();
                    break;
                }
                case R.id.color_1:{
                    color = 1;
                    Btn_color1.setBackground(getResources().getDrawable(R.drawable.level_1_check));
                    Btn_color2.setBackground(getResources().getDrawable(R.drawable.level_2));
                    Btn_color3.setBackground(getResources().getDrawable(R.drawable.level_3));
                    Btn_color4.setBackground(getResources().getDrawable(R.drawable.level_4));
                    Btn_color5.setBackground(getResources().getDrawable(R.drawable.level_5));
                    break;
                }
                case R.id.color_2:{
                    color = 2;
                    Btn_color1.setBackground(getResources().getDrawable(R.drawable.level_1));
                    Btn_color2.setBackground(getResources().getDrawable(R.drawable.level_2_check));
                    Btn_color3.setBackground(getResources().getDrawable(R.drawable.level_3));
                    Btn_color4.setBackground(getResources().getDrawable(R.drawable.level_4));
                    Btn_color5.setBackground(getResources().getDrawable(R.drawable.level_5));
                    break;
                }
                case R.id.color_3:{
                    color = 3;
                    Btn_color1.setBackground(getResources().getDrawable(R.drawable.level_1));
                    Btn_color2.setBackground(getResources().getDrawable(R.drawable.level_2));
                    Btn_color3.setBackground(getResources().getDrawable(R.drawable.level_3_check));
                    Btn_color4.setBackground(getResources().getDrawable(R.drawable.level_4));
                    Btn_color5.setBackground(getResources().getDrawable(R.drawable.level_5));
                    break;
                }
                case R.id.color_4: {
                    color = 4;
                    Btn_color1.setBackground(getResources().getDrawable(R.drawable.level_1));
                    Btn_color2.setBackground(getResources().getDrawable(R.drawable.level_2));
                    Btn_color3.setBackground(getResources().getDrawable(R.drawable.level_3));
                    Btn_color4.setBackground(getResources().getDrawable(R.drawable.level_4_check));
                    Btn_color5.setBackground(getResources().getDrawable(R.drawable.level_5));
                    break;
                }
                case R.id.color_5: {
                    color = 5;
                    Btn_color1.setBackground(getResources().getDrawable(R.drawable.level_1));
                    Btn_color2.setBackground(getResources().getDrawable(R.drawable.level_2));
                    Btn_color3.setBackground(getResources().getDrawable(R.drawable.level_3));
                    Btn_color4.setBackground(getResources().getDrawable(R.drawable.level_4));
                    Btn_color5.setBackground(getResources().getDrawable(R.drawable.level_5_check));
                    break;
                }
                case R.id.img_login : {
                    break;
                }
                case R.id.btn_logout :

                    try {
                    userProxy.logoutBySession(sessionKey, new Callback<String>() {

                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()) {
                                handleResponse_logout(response.body());
                            }
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(MemoGroupActivity.this, "Logout Failuer", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    Toast.makeText(MemoGroupActivity.this, "Logout Exception", Toast.LENGTH_SHORT).show();
                    Log.e("MainActivity", e.toString());
                }
                    SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove("SessionKey");
                    editor.commit();
                    Intent intent = new Intent(this, MemoActivity2.class);
                    startActivity(intent);
                    finish();
                    break;
                }
            }

    private void handleResponse_logout(String response) {
        Toast.makeText(MemoGroupActivity.this, response, Toast.LENGTH_SHORT).show();
    }
        private void open_home_inflateLayout(){
            if(homebtn_condition==0){
                Intent intent = new Intent(this, MemoGroupActivity.class);
                startActivity(intent);
                finish();
                homebtn_condition = 1;
            }
        }
        private void open_new_inflateLayout(){
            homebtn_condition=0;
            inflate.removeAllViews();
            recyclerView.setVisibility(View.GONE);
            GroupRoomLayout.setVisibility(View.GONE);
            inflate.setVisibility(View.VISIBLE);
            serch.setVisibility(View.GONE);
            cardview.setVisibility(View.GONE);
            settingLayout = (LinearLayout)findViewById(R.id.settinginflate_main);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.activity_memo__write,settingLayout,true);
            Btn_edit.setBackground(getResources().getDrawable(R.drawable.search_gray));
            Btn_setting.setBackground(getResources().getDrawable(R.drawable.setting_gray));
            Btn_new.setBackground(getResources().getDrawable(R.drawable.new_orange));
            Btn_home.setBackground(getResources().getDrawable(R.drawable.memohome_gray));

            //time = (EditText)findViewById(R.id.memo_write_EditText_time);
            //시스템으로부터 현재시간(ms)가져오기
            long now = System.currentTimeMillis();
            //Data 객체에 시간 저장
            Date date = new Date(now);
            //각자 사용할 포맷을 정하고 문자열로 만든다.  ex) yyyy/MM/dd HH:mm:ss"
            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");
            time = sdfNow.format(date);
            content = (EditText)findViewById(R.id.memo_write_EditText_content);
            exit = (View)findViewById(R.id.btn_Write_exit);
            exit.setOnClickListener(this);
            send = (Button)findViewById(R.id.btn_Write_send);
            send.setOnClickListener(this);
            Btn_color1 = (Button)findViewById(R.id.color_1);
            Btn_color1.setOnClickListener(this);
            Btn_color2 = (Button)findViewById(R.id.color_2);
            Btn_color2.setOnClickListener(this);
            Btn_color3 = (Button)findViewById(R.id.color_3);
            Btn_color3.setOnClickListener(this);
            Btn_color4 = (Button)findViewById(R.id.color_4);
            Btn_color4.setOnClickListener(this);
            Btn_color5 = (Button)findViewById(R.id.color_5);
            Btn_color5.setOnClickListener(this);
            dao = new Dao(this);
        }
        private Contents exportContents(){
            return new Contents(content.getText().toString().substring(0,content.getText().toString().length()), time, content.getText().toString(), color);
        }

        private void open_setting_inflateLayout(){
            homebtn_condition = 0;
            inflate.removeAllViews();
            recyclerView.setVisibility(View.GONE);
            GroupRoomLayout.setVisibility(View.GONE);
            inflate.setVisibility(View.VISIBLE);
            serch.setVisibility(View.GONE);
            cardview.setVisibility(View.GONE);
            settingLayout = (LinearLayout)findViewById(R.id.settinginflate_main);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.settinginflate_gruop, settingLayout, true);
            Btn_edit.setBackground(getResources().getDrawable(R.drawable.search_gray));
            Btn_setting.setBackground(getResources().getDrawable(R.drawable.setting_orange));
            Btn_new.setBackground(getResources().getDrawable(R.drawable.new_gray));
            Btn_home.setBackground(getResources().getDrawable(R.drawable.memohome_gray));
            //로그인 버튼 설정
            img_login = (ImageView)findViewById(R.id.img_login);
            img_login.setOnClickListener(this);
            //유저 이메일 설정
            Text_email = (TextView)inflate.findViewById(R.id.text_setting_useremail);
            Text_email.setText(user.email);
            //로그아웃 설정
            Btn_logout = (Button)findViewById(R.id.btn_logout);
            Btn_logout.setOnClickListener(this);
        }
        private void open_serch_inflateLayout(){
            recyclerView.setVisibility(View.VISIBLE);
            GroupRoomLayout.setVisibility(View.GONE);
            inflate.setVisibility(View.GONE);
            serch.setVisibility(View.VISIBLE);
            cardview.setVisibility(View.VISIBLE);
            Btn_setting.setBackground(getResources().getDrawable(R.drawable.setting_gray));
            Btn_edit.setBackground(getResources().getDrawable(R.drawable.search_orange));
            Btn_new.setBackground(getResources().getDrawable(R.drawable.new_gray));
            Btn_home.setBackground(getResources().getDrawable(R.drawable.memohome_gray));
        }
    public String getSessionKey() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getString("SessionKey", "ERROR");
    }

}

