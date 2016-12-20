package com.example.hf876.qqsimple.Activities;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;

import android.view.Menu;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hf876.qqsimple.Constants.Constants;
import com.example.hf876.qqsimple.R;
import com.example.hf876.qqsimple.UserPreferences;
import com.example.hf876.qqsimple.fragment.BaseFragment;
import com.example.hf876.qqsimple.ui.BottomControlPanel;
import com.example.hf876.qqsimple.ui.ChatModel;
import com.example.hf876.qqsimple.ui.HeadControlPanel;
import com.example.hf876.qqsimple.ui.ItemModel;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.friend.model.AddFriendNotify;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.SystemMessageObserver;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SystemMessageType;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.SystemMessage;

import java.util.ArrayList;
import java.util.List;

import static android.support.design.widget.Snackbar.make;

public class MainActivity extends AppCompatActivity implements BottomControlPanel.BottomPanelCallback{
    BottomControlPanel bottomPanel = null;
    HeadControlPanel headPanel = null;

    private FragmentManager fragmentManager = null;
    private FragmentTransaction fragmentTransaction = null;


    UserPreferences userPreferences;
  //  TextView tv_user_status;
    ImageView findimg;

    public static String currFragTag = "";


    public static final String TAG="MainActivity";

    //消息监测
    Observer<List<IMMessage>> incomingMessageObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        initUI();

        fragmentManager = getFragmentManager();
        userPreferences=UserPreferences.getInstance(getApplicationContext());
        setDefaultFirstFragment(Constants.FRAGMENT_FLAG_MESSAGE);
      //  tv_user_status=(TextView) findViewById(R.id.tv_user_status);
        StatusCode status= NIMClient.getStatus();
        if(status.wontAutoLogin()){
            Snackbar.make(getWindow().getDecorView(), "需要重新登录", Snackbar.LENGTH_SHORT).show();
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 1000);
        }

        /**
         *   用户登录认证的消息监听
         */
        AuthServiceObserver authServiceObserver = NIMClient.getService(AuthServiceObserver.class);
        authServiceObserver.observeOnlineStatus(new Observer<StatusCode>() {
            @Override
            public void onEvent(StatusCode statusCode) {
               // tv_user_status.setText(statusCode.name());
                if(statusCode == StatusCode.UNLOGIN) {
                    AuthServiceObserver authServiceObserver = NIMClient.getService(AuthServiceObserver.class);
                    authServiceObserver.observeOnlineStatus(null, false);
                    userPreferences.clearUserInfo();
                    Snackbar.make(getWindow().getDecorView(), "即将退出", Snackbar.LENGTH_SHORT).show();
                    getWindow().getDecorView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 1000);
                    return;
                }

                if(statusCode.wontAutoLogin()) {
                    Snackbar.make(getWindow().getDecorView(), "需要重新登录", Snackbar.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

            }
        }, true);



        findimg= (ImageView) findViewById(R.id.right_img);
         findimg.setImageResource(R.drawable.findimg);
         findimg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent =new Intent(getApplicationContext(),FindUserActivity.class);
                 startActivity(intent);
             }
         });


        SystemMessageObserver systemMessageObserver =NIMClient.getService(SystemMessageObserver.class);
        systemMessageObserver.observeReceiveSystemMsg(new Observer<SystemMessage>() {
            @Override
            public void onEvent(SystemMessage systemMessage) {
              if(systemMessage.getType()== SystemMessageType.AddFriend){
                  AddFriendNotify attachData=(AddFriendNotify)systemMessage.getAttachObject();
                  if(attachData!=null){
                      if (attachData.getEvent() == AddFriendNotify.Event.RECV_ADD_FRIEND_DIRECT) {
                          // 对方直接添加你为好友

                          NIMClient.getService(FriendService.class).ackAddFriendRequest(attachData.getAccount(), true); // 通过对方的好友请求

                      }

                  }

                }

            }
        },true);


        /**
         * 注册消息监听
         */

        initObserver();




    }

    private void initObserver() {

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

                        }



                     for(int i =0; i<addedListItems.size();i++) {

                         String newComingContent = addedListItems.get(i).getContent();
                         String realAccount = addedListItems.get(i).getFromAccount();


                         ChatModel model = new ChatModel();
                         model.setContent(newComingContent);
                         model.setIcon("http://img.my.csdn.net/uploads/201508/05/1438760758_3497.jpg");

                         ItemModel curModel = new ItemModel(ItemModel.CHAT_A, model);

                         Log.e(TAG, "onEvent: addedlistitems"+"  i= "+i+"  "+addedListItems.get(i).getContent());

                         ArrayList<ItemModel> lists=Constants.getChatDataByAccount(realAccount);



                         if(lists.size()!=0&&!lists.get(lists.size()-1).equals(newComingContent)){

                             Constants.addChatData(realAccount, curModel);

                             Log.e(TAG, "onEvent: *************true**********" );

                         }else if(lists.size()==0){

                             Constants.addChatData(realAccount, curModel);
                             Log.e(TAG, "onEvent: *************false size=0**********" );
                         }else {

                             Log.e(TAG, "onEvent: *************false **********" );
                         }



                       }
                    }
                };




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    private void initUI(){
        bottomPanel = (BottomControlPanel)findViewById(R.id.bottom_layout);
        if(bottomPanel != null){
            bottomPanel.initBottomPanel();
            bottomPanel.setBottomCallback(this);
        }
        headPanel = (HeadControlPanel)findViewById(R.id.head_layout);
        if(headPanel != null){
            headPanel.initHeadPanel();
        }
;
    }


    @Override
    public void onBottomPanelClick(int itemId) {
        // TODO Auto-generated method stub
        String tag = "";
        if((itemId & Constants.BTN_FLAG_MESSAGE) != 0){
            tag = Constants.FRAGMENT_FLAG_MESSAGE;
        }else if((itemId & Constants.BTN_FLAG_CONTACTS) != 0){
            tag = Constants.FRAGMENT_FLAG_CONTACTS;
        }else if((itemId & Constants.BTN_FLAG_NEWS) != 0){
            tag = Constants.FRAGMENT_FLAG_NEWS;
        }

        setTabSelection(tag); //切换Fragment
        headPanel.setMiddleTitle(tag);//切换标题
    }

    private void setDefaultFirstFragment(String tag){
        Log.e("hef", "setDefaultFirstFragment enter... currFragTag = " + currFragTag+ " "+tag);
        setTabSelection(tag);
        Log.e("hef", "setDefaultFirstFragment enter... currFragTag = " + tag);
        bottomPanel.defaultBtnChecked();
        Log.e("hef", "setDefaultFirstFragment exit...");
    }

    private void commitTransactions(String tag){
        if (fragmentTransaction != null && !fragmentTransaction.isEmpty()) {
            fragmentTransaction.commit();
            currFragTag = tag;
            fragmentTransaction = null;
        }
    }

    private FragmentTransaction ensureTransaction( ){
        if(fragmentTransaction == null){
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        }
        return fragmentTransaction;

    }

    private void attachFragment(int layout, Fragment f, String tag){
        if(f != null){
            if(f.isDetached()){
                ensureTransaction();
                fragmentTransaction.attach(f);

            }else if(!f.isAdded()){
                ensureTransaction();
                fragmentTransaction.add(layout, f, tag);
            }
        }
    }

    private Fragment getFragment(String tag){

        Fragment f = fragmentManager.findFragmentByTag(tag);

        if(f == null){
           // Toast.makeText(getApplicationContext(), "fragment = null tag = " + tag, Toast.LENGTH_SHORT).show();
            f = BaseFragment.newInstance(getApplicationContext(), tag);
        }
        return f;

    }
    private void detachFragment(Fragment f){

        if(f != null && !f.isDetached()){
            ensureTransaction();
            fragmentTransaction.detach(f);
        }
    }
    /**切换fragment
     * @param tag
     */
    private  void switchFragment(String tag){
        if(TextUtils.equals(tag, currFragTag)){
            return;
        }
        //把上一个fragment detach掉
        if(currFragTag != null && !currFragTag.equals("")){
            detachFragment(getFragment(currFragTag));
        }
        attachFragment(R.id.fragment_content, getFragment(tag), tag);
        commitTransactions(tag);
    }

    /**设置选中的Tag
     * @param tag
     */
    public  void setTabSelection(String tag) {
        // 开启一个Fragment事务
        fragmentTransaction = fragmentManager.beginTransaction();

        switchFragment(tag);

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub

        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, false);


        Log.e(TAG, "onStop: "+false);
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
    }


    @Override
    protected void onResume() {

        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, true);

        Log.e(TAG, "onResume: "+true );
        super.onResume();
    }

    @Override
    protected void onDestroy() {

        Log.e(TAG, "onDestroy: "+false );
        super.onDestroy();

    }


    @Override
    protected void onRestart() {

        Log.e(TAG, "onDestroy: "+true );

        super.onRestart();
    }


    @Override
    protected void onPause() {
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, false);
        Log.e(TAG, "onDestroy: "+false );

        super.onPause();
    }
}
