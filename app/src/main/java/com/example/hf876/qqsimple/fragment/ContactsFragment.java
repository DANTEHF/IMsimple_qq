package com.example.hf876.qqsimple.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hf876.qqsimple.Activities.ChatActivity;
import com.example.hf876.qqsimple.Activities.MainActivity;
import com.example.hf876.qqsimple.Adapters.ContactsAdapter;
import com.example.hf876.qqsimple.Constants.Constants;
import com.example.hf876.qqsimple.R;
import com.example.hf876.qqsimple.ui.ContactBean;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.friend.model.Friend;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hf876 on 2016/11/30.
 */
public class ContactsFragment extends BaseFragment {
    private static final  String TAG="Contacts View";
    private MainActivity mMainActivity;
    private ListView mListView;
    private ContactsAdapter mContactsAdapter;
    private List<ContactBean> mContactBean =new ArrayList<ContactBean>();

    String theAccount;
     @Override
     public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate------");
        List<Friend> friendsAccount= NIMClient.getService(FriendService.class).getFriends();

        for(int i=0;i<friendsAccount.size();i++){
            theAccount=friendsAccount.get(i).getAccount();

            mContactBean.add(new ContactBean(R.drawable.ball    , theAccount));

        }


     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contactsLayout = inflater.inflate(R.layout.contacts_layout,
                container, false);
        Log.e(TAG, "onCreateView: --->");
        mMainActivity=(MainActivity) getActivity();
        mFragmentManager=getActivity().getFragmentManager();
        mListView=(ListView) contactsLayout.findViewById(R.id.listview_contacts);
        mContactsAdapter =new ContactsAdapter(mContactBean,mMainActivity);
        mListView.setAdapter(mContactsAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ContactBean theFriend= (ContactBean) parent.getAdapter().getItem(position);
                String theAccount=  theFriend.getUserID();

                Intent intent =new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("account",theAccount);

                Log.e(TAG,"start to chat with"+theAccount);
                startActivity(intent);
            }
        });


   return contactsLayout;


    }




    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        MainActivity.currFragTag = Constants.FRAGMENT_FLAG_CONTACTS;
    }

}
