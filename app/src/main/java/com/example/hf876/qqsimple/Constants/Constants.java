package com.example.hf876.qqsimple.Constants;

import android.app.Activity;
import android.util.Log;

import com.example.hf876.qqsimple.ui.ItemModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by hf876 on 2016/11/28.
 */
public class Constants {

    public static final String TAG="Constants";

    public static final int BTN_FLAG_MESSAGE = 0x01;
    public static final int BTN_FLAG_CONTACTS = 0x01 << 1;
    public static final int BTN_FLAG_NEWS = 0x01 << 2;
    public static final int BTN_FLAG_SETTING = 0x01 << 3;

    //Fragment的标识
    public static final String FRAGMENT_FLAG_MESSAGE = "消息";
    public static final String FRAGMENT_FLAG_CONTACTS = "联系人";
    public static final String FRAGMENT_FLAG_NEWS = "动态";
    public static final String FRAGMENT_FLAG_MENU="侧边栏";
    public static final String FRAGMENT_FLAG_SIMPLE = "simple";


    public static HashMap<String,ArrayList<ItemModel>> chatList=new HashMap<>();

    //通过account来获得对应的聊天数据
    public static ArrayList<ItemModel> getChatDataByAccount(String theAccount) {

        if(chatList.get(theAccount)==null){
            chatList.put(theAccount,new ArrayList<ItemModel>());
        }

        Log.e(TAG+"getChatData","account"+theAccount+" size "+chatList.get(theAccount).size());

      //  printChatList(chatlist);

        return  chatList.get(theAccount);
    }


    //保存数据
    public static void  addChatData(String theAccount,ItemModel curItem) {


       // printChatList(chatlist);

        if(chatList.get(theAccount)==null){
            chatList.put(theAccount,new ArrayList<ItemModel>());
        }

        chatList.get(theAccount).add(curItem);

        Log.e(TAG+"addData","account"+theAccount+"size():"+chatList.get(theAccount).size());

    }

    private static void printChatList() {

        Iterator iterator=chatList.entrySet().iterator();

        while(iterator.hasNext()){
            Map.Entry<String,ArrayList<ItemModel>> entry = (Map.Entry<String, ArrayList<ItemModel>>) iterator.next();
            Log.e("print chatList"+entry.getKey(),entry.getValue().toString());
        }
    }



    public static ArrayList activities=new ArrayList();

    public static void addActivity(Activity activity){
        activities.add(activity);

        printActivityList();

    }

    private static void printActivityList() {

        for (int i = 0; i < activities.size(); i++) {
            Log.e(TAG, "printActivityList: " + i + "  " + activities.get(i));

        }
    }
    public static void deleteActivity(Activity activity){

        if(activities.lastIndexOf(activity)!=0){
            activities.remove(activities.lastIndexOf(activity));
        }

        printActivityList();

    }

}
