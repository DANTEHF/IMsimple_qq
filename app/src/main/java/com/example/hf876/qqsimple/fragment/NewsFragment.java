package com.example.hf876.qqsimple.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hf876.qqsimple.Activities.MainActivity;
import com.example.hf876.qqsimple.Constants.Constants;
import com.example.hf876.qqsimple.R;

/**
 * Created by hf876 on 2016/11/30.
 */
public class NewsFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View newsLayout = inflater.inflate(R.layout.news_layout, container,
                false);
        return newsLayout;
    }


    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        MainActivity.currFragTag = Constants.FRAGMENT_FLAG_NEWS;
    }
}
