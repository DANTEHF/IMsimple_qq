package com.example.hf876.qqsimple;

import android.content.Context;

import com.example.hf876.qqsimple.Constants.SharedPreferencesHelper;

/**
 * Created by hf876 on 2016/12/4.
 */

public class UserPreferences extends SharedPreferencesHelper {

 private static UserPreferences helper;


    protected UserPreferences(Context context) {
        super(context);
    }
    public static UserPreferences getInstance(Context context) {
        if (helper == null) {
            helper = new UserPreferences(context);
        }
        return helper;
    }

    public void setUserAccount(String account) {
        putStringValue("account", account);
    }

    public String getUserAccount() {
        return getStringValue("account");
    }

    public void setUserToken(String token) {
        putStringValue("token", token);
    }

    public String getUserToken() {
        return getStringValue("token");
    }

    public void clearUserInfo() {
        setUserToken("");
        setUserAccount("");
    }
}
