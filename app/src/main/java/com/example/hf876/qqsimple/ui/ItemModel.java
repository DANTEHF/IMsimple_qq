package com.example.hf876.qqsimple.ui;

import android.content.Context;
import android.view.View;

import java.io.ObjectInput;
import java.io.Serializable;


public class ItemModel implements Serializable {

    public static final int CHAT_A = 1001;
    public static final int CHAT_B = 1002;
    public int type;
    public Object object;
    //public String account;



    public ItemModel(int type, Object object) {
        this.type = type;
        this.object = object;
    }


    @Override
    public String toString() {
        return object+"";
    }
    public Object getChatModel(){
        return object;
    }
}

