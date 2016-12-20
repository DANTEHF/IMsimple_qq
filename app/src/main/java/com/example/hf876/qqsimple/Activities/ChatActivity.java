package com.example.hf876.qqsimple.Activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hf876.qqsimple.Adapters.ChatAdapter;
import com.example.hf876.qqsimple.Constants.Constants;
import com.example.hf876.qqsimple.R;
import com.example.hf876.qqsimple.ui.ChatModel;
import com.example.hf876.qqsimple.ui.ItemModel;
import com.netease.nimlib.sdk.InvocationFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;

import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hf876 on 2016/12/1.
 */

public class ChatActivity extends AppCompatActivity{
    private static final String TAG = "ChatActivity";
    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private EditText editText;
    private TextView tvSend ;
    private String content;
    TextView middle_title;
    String theAccount;
    LinearLayoutManager linerManager;
    Observer<List<IMMessage>> incomingMessageObserver;

    private ArrayList<ItemModel> chatdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //用于测试avtivity
        Constants.addActivity(this);


        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        //获取联系人名字以及账号
        middle_title= (TextView) findViewById(R.id.midle_title);
        Intent intent=getIntent();

        theAccount =intent.getStringExtra("account");
        middle_title.setText(theAccount);


        Log.e(TAG,"oncreate begin to chat with"+theAccount);


        //获得聊天数据


        chatdata=new ArrayList<ItemModel>();

        ArrayList list=Constants.getChatDataByAccount(theAccount);
        Log.e(TAG, "onCreate: list from constants "+list);

        chatdata.addAll(Constants.getChatDataByAccount(theAccount));

        adapter=new ChatAdapter(chatdata);
        Log.e("chatdata", "onCreate: "+chatdata);
        recyclerView = (RecyclerView) findViewById(R.id.recylerView);
        editText = (EditText) findViewById(R.id.et);
        tvSend = (TextView) findViewById(R.id.tvSend);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        linerManager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linerManager);


        recyclerView.setAdapter(adapter);

        incomingMessageObserver =
                new Observer<List<IMMessage>>() {
                    @Override
                    public void onEvent(List<IMMessage> messages) {
                        // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊
                        //
                        // 天对象。
                        List<IMMessage> addedListItems = new ArrayList<>(messages.size());
                        for (IMMessage message : messages) {

                                addedListItems.add(message);

                           //  Log.e(TAG, "onEvent: addlistitem  "+message.getContent() );

                        }


                        Log.e(TAG, "added listItem onEvent: *************************************************" );

                        for(int i=0;i<addedListItems.size();i++){
                            Log.e(TAG, "onEvent: lastitem  print "+i+" "+addedListItems.get(i).getContent());
                        }

                        Log.e(TAG, "last listitem onEvent: *************************************************" );



                        Log.e(TAG, "onEvent: addedListItem size  "+addedListItems.size() );
                        List<ItemModel> lastChatdatalist=Constants.getChatDataByAccount(theAccount);

                        String lastMsg="";
                        if(lastChatdatalist.size()!=0){

                            Log.e(TAG, "onEvent: lastdalist.size  "+ lastChatdatalist.size());
                           lastMsg= ((ChatModel)lastChatdatalist.get(lastChatdatalist.size()-1).getChatModel()).getContent();

                            Log.e(TAG, "onEvent: lastmsg"+lastMsg );
                        }

                        Log.e(TAG, "last chatlist onEvent: *************************************************" );

                        for(int i=0;i<lastChatdatalist.size();i++){
                            Log.e(TAG, "onEvent: lastitem  print "+i+" "+((ChatModel)lastChatdatalist.get(i).getChatModel()).getContent());
                        }

                        Log.e(TAG, "last chatlist onEvent: *************************************************" );



                        for(int i =0; i<addedListItems.size();i++){

                                String newComingContent = addedListItems.get(i).getContent();
                                String realAccount = addedListItems.get(i).getFromAccount();


                                ChatModel model = new ChatModel();
                                model.setContent(newComingContent);
                                model.setIcon("http://images.ali213.net/picfile/pic/2012-04-01/927_zlm02.jpg");

                                ItemModel curModel = new ItemModel(ItemModel.CHAT_A, model);
                                Log.e(TAG, "onEvent: realaccount" + realAccount + "  " + theAccount+" last "+lastMsg+"   new   "+newComingContent);

                            Log.e(TAG, "onEvent: "+theAccount.equals(realAccount)+"   "+ !lastMsg.equals(newComingContent));
                                if (theAccount.equals(realAccount) && !lastMsg.equals(newComingContent)) {
                                    Log.e(TAG, "onEvent: realaccount adapter");
                                    adapter.addChatDataToAdapter(curModel);
                                    Log.e("ChatActivity adapter", "add chatData"+((ChatModel)curModel.getChatModel()).getContent());

                                    Constants.addChatData(realAccount, curModel);
                                    NIMClient.getService(MsgService.class)
                                            .clearUnreadCount(theAccount, SessionTypeEnum.P2P);
                                }


                    }
                    }
                };
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, true);
        initData();
    }


    private void initData() {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                content = s.toString().trim();
            }
        });

        tvSend.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                ChatModel model = new ChatModel();
                model.setIcon("http://r002.joyme.com/r002/image/2012/07/42/AD24F5F0FCFADC8342048C4B36A6A0A3.jpg");
                model.setContent(content);

                ItemModel curModel=new ItemModel(ItemModel.CHAT_B, model);

                final IMMessage message = MessageBuilder.createTextMessage(
                        theAccount, // 聊天对象的 ID，如果是单聊，为用户帐号，如果是群聊，为群组 ID
                        SessionTypeEnum.P2P, content// 聊天类型，单聊或群组
                        // 文本内容
                );
              // 发送消息。如果需要关心发送结果，可设置回调函数。发送完成时，会收到回调。如果失败，会有具体的错误码。

                adapter.addChatDataToAdapter(curModel);

                Constants.addChatData(theAccount,curModel);
                editText.setText("");

                InvocationFuture<Void> invocationFuture=NIMClient.getService(MsgService.class).sendMessage(message,true);
                invocationFuture.setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ChatActivity.this,"已发送", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailed(int i) {

                    }

                    @Override
                    public void onException(Throwable throwable) {

                    }
                });

            }
        });

    }

    private void hideKeyBorad(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }


    @Override
    protected void onDestroy() {
        Log.e(TAG,"finish to chat with"+theAccount);
        Constants.deleteActivity(this);
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, false);
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.e(TAG, "onStop: "+"******************" );
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, false);
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.e(TAG, "onRestart: "+"*******************");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.e(TAG, "onResume: "+"********************" );
        super.onResume();
    }

}
