package com.example.hf876.qqsimple.Activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hf876.qqsimple.R;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.friend.constant.VerifyType;
import com.netease.nimlib.sdk.friend.model.AddFriendData;

/**
 * Created by hf876 on 2016/12/6.
 */

public class FindUserActivity extends AppCompatActivity {

    Button addFriend_btn;
    EditText usernumber;
    EditText addFriend_msg;
    private String usernum;

    private String msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.acticity_find_panel);
         addFriend_btn= (Button) findViewById(R.id.addFriend_btn);
         usernumber= (EditText) findViewById(R.id.et_find_text);
         addFriend_msg= (EditText) findViewById(R.id.et_addfrd_msg);


        addFriend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernum=usernumber.getText().toString();
                msg =addFriend_msg.getText().toString();
                if(usernum!=null&&msg!=null){
                    final VerifyType verifyType =VerifyType.DIRECT_ADD;
                    NIMClient.getService(FriendService.class).addFriend(new AddFriendData(usernum,verifyType,msg))
                            .setCallback(new RequestCallback<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(FindUserActivity.this,"发送好友申请成功 :等待对方同意",Toast.LENGTH_LONG).show();
                            usernumber.setText("");
                            addFriend_msg.setText("");
                        }

                        @Override
                        public void onFailed(int i) {
                            Toast.makeText(FindUserActivity.this,"发送好友申请失败，原因可能是账号不存在",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onException(Throwable throwable) {
                            Log.e("Wrong:", "onException: "+throwable);
                        }
                    });


                }
            }
        });




    }



}
