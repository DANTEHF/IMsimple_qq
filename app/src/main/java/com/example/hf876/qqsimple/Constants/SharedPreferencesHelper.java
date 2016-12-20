package com.example.hf876.qqsimple.Constants;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.hf876.qqsimple.R;

/**
 * Created by hf876 on 2016/12/5.
 */

public class SharedPreferencesHelper {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private static SharedPreferencesHelper helper;

    protected SharedPreferencesHelper(Context context)
    {
        String pref = context.getResources().getString(R.string.prefs_name);
        sp = context.getSharedPreferences(pref, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public static SharedPreferencesHelper getInstance(Context context)
    {
        if (helper == null)
        {
            helper = new SharedPreferencesHelper(context);
        }
        return helper;
    }


    public void putStringValue(String key, String value) {
        editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getStringValue(String key)
    {
        return sp.getString(key, null);
    }

    public void setBooleanValue(String key,boolean value) {
        editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBooleanValue(String key,boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public void putIntValue(String key, int value) {
        editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getIntValue(String key,int defaultV)
    {
        return sp.getInt(key, defaultV);
    }

    public void putLongValue(String key, long value) {
        editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public long getLongValue(String key,long defaultV)
    {
        return sp.getLong(key, defaultV);
    }
}
