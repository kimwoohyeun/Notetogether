package com.example.y.notetogether.Activity.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import javax.xml.parsers.DocumentBuilderFactory;
import com.example.y.notetogether.Activity.Multie.MemoGroupActivity;
import com.example.y.notetogether.Activity.Service.Network;
import com.example.y.notetogether.Activity.Service.User;
import com.example.y.notetogether.Activity.Service.UserProxy;
import com.example.y.notetogether.R;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginDefine;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kimwoohyeun on 2016-05-15.
 */
public class LoginActivity extends Activity {
    Handler handler = new Handler();
    private EditText edit_email;
    private EditText edit_pwd;
    private User user;
    private Button Btn_log;
    private Button Btn_log_register;
    private Button Btn_log_cancle;
    private Network network;
    private String email;
    private String pwd;
    private static String accessToken;          //token for access, this token last 4 hours
    private static String refreshToken;         //token for refreshing accessToken, refreshToken last more time than accessToken
    private OAuthLogin mOAuthLoginModule;
    private String OAUTH_CLIENT_ID = "7IxBTqFyOaO8bCWEEJHa";
    private String OAUTH_CLIENT_SECRET = "_Wu3Kc7yQZ";
    private String OAUTH_CLIENT_NAME = "NoteTogeter";

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        edit_email = (EditText) findViewById(R.id.edit_id);
        edit_pwd = (EditText) findViewById(R.id.edit_pwd);
        Btn_log = (Button) findViewById(R.id.btn_login);
        Btn_log_register = (Button) findViewById(R.id.btn_register);
        Btn_log_cancle = (Button) findViewById(R.id.btn_login_cancle);
        Toast.makeText(LoginActivity.this, "로그인화면", Toast.LENGTH_SHORT).show();
        network = Network.getNetworkInstance();

        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(
                LoginActivity.this
                , OAUTH_CLIENT_ID
                , OAUTH_CLIENT_SECRET
                , OAUTH_CLIENT_NAME
        );

        OAuthLoginButton mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.buttonOAuthLoginImg);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
        Btn_log_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Btn_log.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                email = edit_email.getText().toString();
                pwd = edit_pwd.getText().toString();
                user = new User(email, pwd);

                try {
                    network.getUserProxy().login(user, new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful()) {
                                LoginResponse loginResponse = response.body();
                                handleResponse(loginResponse,user);
                            } else {

                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "잘못 입력하셨습니다", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Exception 에러", Toast.LENGTH_SHORT).show();
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
    public void handleResponse(LoginResponse loginResponse, User user) {
        String responseBody = loginResponse.getStatus();
        Log.i("login", responseBody);
        if (responseBody.equals("Create User Success")) {
            Toast.makeText(this, responseBody, Toast.LENGTH_SHORT).show();
        }else if(responseBody.equals("ID Already EXIST")){
            Toast.makeText(this, responseBody, Toast.LENGTH_SHORT).show();
        }else if(responseBody.equals("NO Email EXIST")){
            Toast.makeText(this, responseBody, Toast.LENGTH_SHORT).show();
        }else if(responseBody.equals("Wrong Password")){
            Toast.makeText(this, responseBody, Toast.LENGTH_SHORT).show();
        }else {
            Log.i("login", "" + responseBody.equals("Login Success"));
            Toast.makeText(this, responseBody , Toast.LENGTH_SHORT).show();
            saveSessionKey(loginResponse.getSessionKey());
            Intent intent = new Intent(this, MemoGroupActivity.class);
            //intent.putExtra("User", (Parcelable) user);
            startActivity(intent);
            finish();
        }
    }
    public void saveSessionKey(String sessionKey) {
        SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("SessionKey", sessionKey);
        editor.commit();
        Log.i("LoginActivity","SessionKey : " + sessionKey);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public String getOpenAPI(){
        String userInfo = "";
        try {
            userInfo = new RequestApiTask().execute().get();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return userInfo;
    }
    private class RequestApiTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected String doInBackground(Void... params) {
            String url = "https://openapi.naver.com/v1/nid/getUserProfile.xml";
            String at = mOAuthLoginModule.getAccessToken(LoginActivity.this);
            return  mOAuthLoginModule.requestApi(LoginActivity.this, at, url);
        }
        protected void onPostExecute(String userInfo) {
            Log.d("userData", userInfo);
        }
    }


    private String xmlParsingByXpath(String InputXML, String target) {
        try {
            InputSource inputSource = new InputSource(new StringReader((InputXML)));
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource);
            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList nodeList = (NodeList)xpath.evaluate("//data/response/" + target, document, XPathConstants.NODESET);
            for(int idx=0; idx<nodeList.getLength(); idx++){
                Log.d("xml_log", nodeList.item(idx).getTextContent());
                return nodeList.item(idx).getTextContent();
            }
        }
        catch(Exception e){
            Log.e("XML parsing error", "XML error");
            e.printStackTrace();
        }
        return null;
    }
    /**
     *  To control the situation after login completed
     */
    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                accessToken = mOAuthLoginModule.getAccessToken(mContext);
                refreshToken = mOAuthLoginModule.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginModule.getExpiresAt(mContext);
                String tokenType = mOAuthLoginModule.getTokenType(mContext);
                //saveSessionKey(accessToken);
                //mOauthAT.setText(accessToken);    //mOauthRT.setText(refreshToken);   //mOauthExpires.setText(String.valueOf(expiresAt));
                //mOauthTokenType.setText(tokenType);  //mOAuthState.setText(mOAuthLoginInstance.getState(mContext).toString());
                String userInfo = getOpenAPI();
                String userEmail = xmlParsingByXpath(userInfo, "email");
                String userBirthday = xmlParsingByXpath(userInfo, "birthday");
                String userAge = xmlParsingByXpath(userInfo, "age");
                String userGender = xmlParsingByXpath(userInfo, "gender");
                String userName = xmlParsingByXpath(userInfo, "name");
                user = new User(userEmail, "");
                try{
                    UserProxy userProxy = network.getUserProxy();
                    userProxy.registerUser(user, new retrofit2.Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.isSuccessful()) {
                                Log.i("UserProxy", "Network Success And " + response.body());
                                //Intent intent = new Intent(LoginActivity.this, MemoGroupActivity.class);        //after login completed
                                //startActivity(intent);
                                //finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.e("TEST", "FAIL!!!" + t);
                        }
                    });
                }catch (Exception e){
                }

            } else {
                String errorCode = mOAuthLoginModule.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginModule.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        }
    };

}

