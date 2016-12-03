package com.example.hf876.qqsimple.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.hf876.qqsimple.R;


/**
 * Created by hf876 on 2016/11/28.
 */
public class SplashActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =new Intent();
                intent.setClass(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        },2000);
    }
}
