package com.example.hf876.qqsimple.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hf876.qqsimple.R;

import java.util.Date;
import java.util.Random;
import java.security.MessageDigest;
/**
 * Created by hf876 on 2016/12/5.
 */

public class RegistActivity extends AppCompatActivity {

    EditText username;
    EditText account;
    EditText  password;
    Button  regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_regist);
        username=(EditText) findViewById(R.id.et_inputName_register);
        account=(EditText)findViewById(R.id.et_inputAccount_register);
        password=(EditText)findViewById(R.id.et_inputPassword_register);
        regist=(Button)findViewById(R.id.btn_register_register);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=username.getText().toString();
                String Account=account.getText().toString();
                String pass=password.getText().toString();
                if(userName!=""&&Account!=""&&pass!=""){


                }
            }
        });

    }

}
