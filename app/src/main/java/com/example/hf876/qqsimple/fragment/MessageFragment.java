package com.example.hf876.qqsimple.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hf876.qqsimple.Activities.MainActivity;
import com.example.hf876.qqsimple.Constants.Constants;
import com.example.hf876.qqsimple.R;

/**
 * Created by hf876 on 2016/11/30.
 */
public class MessageFragment extends BaseFragment {
    private static final String TAG = "MessageFragment";
    private MainActivity mMainActivity ;
    //  private ListView mListView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.message_layout,
                container, false);
        Log.d(TAG, "onCreateView---->");
        mMainActivity = (MainActivity) getActivity();
        mFragmentManager = getActivity().getFragmentManager();
//        mListView = (ListView)messageLayout.findViewById(R.id.listview_message);
//
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                // TODO Auto-generated method stub
//
//            }
//
//        });
        return messageLayout;
    }


    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        Log.e(TAG, "onAttach-----");

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate------");

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated-------");
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        Log.e(TAG, "onStart----->");
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.e(TAG, "onresume---->");
        MainActivity.currFragTag = Constants.FRAGMENT_FLAG_MESSAGE;
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Log.e(TAG, "onpause");
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();
        Log.e(TAG, "ondestoryView");
    }
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.e(TAG, "ondestory");
    }

    @Override
    public void onDetach() {
        // TODO Auto-generated method stub
        super.onDetach();
        Log.d(TAG, "onDetach------");

    }
}
