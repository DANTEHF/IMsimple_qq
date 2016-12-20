package com.example.hf876.qqsimple.Activities;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hf876.qqsimple.R;
import com.example.hf876.qqsimple.UserPreferences;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;



/**
 * Created by hf876 on 2016/11/28.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static  final String TAG =LoginActivity.class.getSimpleName();

    private EditText ed_username;
    private EditText ed_password;
    private CheckBox checkBox;
    private Button   loginBtn;
    private TextView cannot_login;
    private TextView tv_register;

    String username;
    String password;

    UserPreferences userPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_login);

        userPreferences = UserPreferences.getInstance(getApplicationContext());
        String account = userPreferences.getUserAccount();
        String token = userPreferences.getUserToken();

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            Log.d(TAG, "start HomeActivity");
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }


      ed_username = (EditText) findViewById(R.id.et_username);
      ed_password = (EditText) findViewById(R.id.et_password);
      checkBox = (CheckBox) findViewById(R.id.ck_agree);
      loginBtn = (Button) findViewById(R.id.btn_login);
      loginBtn.setOnClickListener(this);
      cannot_login = (TextView) findViewById(R.id.cannot_login);
      tv_register = (TextView) findViewById(R.id.tv_register);
      cannot_login.setOnClickListener(this);
      tv_register.setOnClickListener(this);
        userPreferences=UserPreferences.getInstance(getApplicationContext());
  }


    public void dologin() {


        ed_username.setError(null);
        ed_password.setError(null);

        username = ed_username.getText().toString();
        password = ed_password.getText().toString();
        Log.e("hi", "fdadfa");
        LoginInfo info = new LoginInfo(username,password); // config...
        AuthService authService=NIMClient.getService(AuthService.class);
        AbortableFuture future = authService.login(info);
        future.setCallback(new RequestCallback<LoginInfo>() {


            @Override
            public void onSuccess(LoginInfo loginInfo) {
                Log.e("S:", "onSuccess: ");
                userPreferences.setUserAccount(loginInfo.getAccount());
                userPreferences.setUserToken(loginInfo.getToken());

                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailed(int i) {
                Log.e("F:", "onFailed: "+i);
                Toast.makeText(LoginActivity.this,"登录出错了：检查网络或确保无他人登录",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onException(Throwable throwable) {
                Log.e("E:", "onException: ");
            }
        });
    }

    public void cnlogin() {
        Toast.makeText(LoginActivity.this, " useless", Toast.LENGTH_SHORT).show();
    }

    public void goRegist() {
        Toast.makeText(LoginActivity.this, "cannot regist for now", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                dologin();
                break;
            case R.id.cannot_login:
                cnlogin();
                break;
            case R.id.tv_register:
                goRegist();
                break;
            default:
                break;
        }

    }





}
