package com.example.hf876.qqsimple.Activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.hf876.qqsimple.R;

/**
 * Created by hf876 on 2016/11/28.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_login);
        Intent intent=new Intent();
        intent.setClass(LoginActivity.this ,MainActivity.class);
        startActivity(intent);

    }
}

