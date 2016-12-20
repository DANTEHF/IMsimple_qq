package com.example.hf876.qqsimple.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hf876.qqsimple.ui.ContactBean;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider.UserInfo;

import java.util.List;
import com.example.hf876.qqsimple.R;
/**
 * Created by hf876 on 2016/12/5.
 */

public class ContactsAdapter extends BaseAdapter {

    private List<ContactBean> mListUserinfo=null;
    private Context mContext;
    private LayoutInflater mInflater;

    public ContactsAdapter(List<ContactBean> listUserinfo,Context context){
        mListUserinfo=listUserinfo;
        mContext=context;
        mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mListUserinfo.size();
    }

    @Override
    public Object getItem(int position) {
        return mListUserinfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v= mInflater.inflate(R.layout.contacts_item_layout,null);

        ImageView imageView = (ImageView) v.findViewById(R.id.img_msg_item);
        imageView.setImageResource(mListUserinfo.get(position).getPhotoDrawableId());

        TextView nameMsg = (TextView)v.findViewById(R.id.name_msg_item);
        nameMsg.setText(mListUserinfo.get(position).getUserID());


        return v;
    }
}
