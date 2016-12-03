package com.example.hf876.qqsimple.ui;

import android.content.Context;
import android.graphics.Color;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import android.widget.RelativeLayout;

import com.example.hf876.qqsimple.Constants.Constants;
import com.example.hf876.qqsimple.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hf876 on 2016/11/30.
 */
public class BottomControlPanel extends RelativeLayout implements View.OnClickListener {
    private Context mContext;
    private ImageText mMsgBtn = null;
    private ImageText mContactsBtn = null;
    private ImageText mNewsBtn = null;

    private int DEFALUT_BACKGROUND_COLOR = Color.rgb(243, 243, 243); //Color.rgb(192, 192, 192)
    private BottomPanelCallback mBottomCallback = null;
    private List<ImageText> viewList = new ArrayList<ImageText>();

    public interface BottomPanelCallback{
        public void onBottomPanelClick(int itemId);
    }

    public BottomControlPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onClick(View v) {
        initBottomPanel();
        int index = -1;
        switch(v.getId()){
            case R.id.btn_message:
                index = Constants.BTN_FLAG_MESSAGE;
                mMsgBtn.setChecked(Constants.BTN_FLAG_MESSAGE);
                break;
            case R.id.btn_contacts:
                index = Constants.BTN_FLAG_CONTACTS;
                mContactsBtn.setChecked(Constants.BTN_FLAG_CONTACTS);
                break;
            case R.id.btn_news:
                index = Constants.BTN_FLAG_NEWS;
                mNewsBtn.setChecked(Constants.BTN_FLAG_NEWS);
                break;

            default:break;
        }
        if(mBottomCallback != null){
            mBottomCallback.onBottomPanelClick(index);
        }

    }
    @Override
    protected void onFinishInflate(){

        mMsgBtn = (ImageText)findViewById(R.id.btn_message);
        mContactsBtn = (ImageText)findViewById(R.id.btn_contacts);
        mNewsBtn = (ImageText)findViewById(R.id.btn_news);

        setBackgroundColor(DEFALUT_BACKGROUND_COLOR);
        viewList.add(mMsgBtn);
        viewList.add(mContactsBtn);
        viewList.add(mNewsBtn);


    }
    public void initBottomPanel(){
        if(mMsgBtn != null){
            mMsgBtn.setImage(R.drawable.ic_message_normal);
            mMsgBtn.setText("消息");
        }
        if(mContactsBtn != null){
            mContactsBtn.setImage(R.drawable.ic_lxr_normal);
            mContactsBtn.setText("联系人");
        }
        if(mNewsBtn != null){
            mNewsBtn.setImage(R.drawable.ic_space_normal);
            mNewsBtn.setText("动态");
        }

        setBtnListener();

    }

    private void setBtnListener() {
        int num = this.getChildCount();
        for(int i = 0; i < num; i++){
            View v = getChildAt(i);
            if(v != null){
                v.setOnClickListener(this);
            }
        }
    }
    public void setBottomCallback(BottomPanelCallback bottomCallback){
        mBottomCallback = bottomCallback;
    }
    public void defaultBtnChecked(){
        if(mMsgBtn != null){
            mMsgBtn.setChecked(Constants.BTN_FLAG_MESSAGE);
        }
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // TODO Auto-generated method stub
        super.onLayout(changed, left, top, right, bottom);
        layoutItems(left, top, right, bottom);
    }

    private void layoutItems(int left, int top, int right, int bottom) {
        int n = getChildCount();
        if(n == 0){
            return;
        }
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        Log.i("yanguoqi", "paddingLeft = " + paddingLeft + " paddingRight = " + paddingRight);
        int width = right - left;
        int height = bottom - top;
        Log.i("yanguoqi", "width = " + width + " height = " + height);
        int allViewWidth = 0;
        for(int i = 0; i< n; i++){
            View v = getChildAt(i);
           Log.i("yanguoqi", "v.getWidth() = " + v.getWidth());
            allViewWidth += v.getWidth();
        }
        int blankWidth = (width - allViewWidth - paddingLeft - paddingRight) / (n - 1);
        Log.i("yanguoqi", "blankV = " + blankWidth );

        LayoutParams params1 = (LayoutParams) viewList.get(1).getLayoutParams();
        params1.leftMargin = blankWidth;
        viewList.get(1).setLayoutParams(params1);

        LayoutParams params2 = (LayoutParams) viewList.get(2).getLayoutParams();
        params2.leftMargin = blankWidth;
        viewList.get(2).setLayoutParams(params2);
    }
}
