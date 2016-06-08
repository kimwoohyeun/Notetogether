package com.example.y.notetogether.Activity.Multie;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.y.notetogether.Activity.DB.ContentBoardAdapter;
import com.example.y.notetogether.Activity.DB.ContentCountDB;
import com.example.y.notetogether.Activity.DB.ContentDB;
import com.example.y.notetogether.Activity.DB.Dao;
import com.example.y.notetogether.Activity.Service.Group;
import com.example.y.notetogether.Activity.Service.Network;
import com.example.y.notetogether.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kimwoohyeun on 2016-05-27.
 */
public class RoomSearchDialog extends Dialog implements View.OnClickListener{
    private String sessionKey;
    private ImageView cancle;
    private Button ok;
    private EditText textView_serch;
    private ListView listView;
    private Dao dao;
    private ContentBoardRoomAdapter contentBoardAdapter_ok;
    private Network network;
    private RecyclerView recyclerView;
    private Context context;
    private Group group;
    public RoomSearchDialog(Context context, String sessionkey) {
        super(context);
        this.context = context;
        this.sessionKey = sessionkey;
    }
    private ArrayList<RoomContents> contentList;



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
        //리스트 생성
        recyclerView = (RecyclerView)findViewById(R.id.listView_roomsearch);
        //카드뷰 설정
       LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        //네트워크 설정
        getContents getContents = new getContents();
        getContents.execute();
        cancle = (ImageView) findViewById(R.id.btn_roomsearch_exit);
        ok = (Button)findViewById(R.id.btn_roomsearch_ok);
       // listView = (ListView)findViewById(R.id.listView_roomsearch);
        ok.setOnClickListener(this);
        cancle.setOnClickListener(this);
        //위치 조정
        this.getWindow().setGravity(Gravity.TOP);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_roomsearch_exit: {
                cancel();
                break;
            }
            case R.id.btn_roomsearch_ok : {
                network = Network.getNetworkInstance();
                group = new Group(contentBoardAdapter_ok.gettitle(),sessionKey );
                try{
                    network.getGroupProxy().participateRoom(group, new Callback<String>() {
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
    private class getContents extends AsyncTask<Void,Void,Void>{
        public ArrayList<ContentDB> content;
        public ArrayList<ContentCountDB> count;

        @Override
        protected Void doInBackground(Void... params) {
            network = Network.getNetworkInstance();
            content = network.getGroupProxy().getRoomContents();
            count = network.getGroupProxy().getRoomCount();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            contentList = new ArrayList<RoomContents>();
            for(int i=0;i<content.size();i++) {
                contentList.add(new RoomContents(content.get(i).getName(),0));
            }
            for(int i=0;i<count.size();i++){
                for(int j=0;j<content.size();j++){
                    if(count.get(i).getGroupid()==j+1)
                        contentList.set(j,new RoomContents(content.get(j).getName(),count.get(i).getCount()));
                }
            }

            //리스트에 어뎁터 삽입
            final ContentBoardRoomAdapter contentBoardRoomAdapter = new ContentBoardRoomAdapter(context,contentList,R.layout.activity_groupmemo_roomsearch);
            recyclerView.setAdapter(contentBoardRoomAdapter);
            contentBoardAdapter_ok=contentBoardRoomAdapter;
            //검색 필터링
            textView_serch = (EditText)findViewById(R.id.edittext_roomsearch_name);
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
                    contentBoardRoomAdapter.filter(text);
                    recyclerView.setAdapter(contentBoardRoomAdapter);
                }
            });
        }

    }

}

