package com.example.hf876.qqsimple.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hf876.qqsimple.Constants.Constants;
import com.example.hf876.qqsimple.R;

/**
 * Created by hf876 on 2016/11/30.
 */
public class HeadControlPanel extends RelativeLayout {

    private Context mContext;
    private TextView mMidleTitle;
    private TextView mRightTitle;
    private static final float middle_title_size = 20f;
    private static final float right_title_size = 17f;
    private static final int default_background_color = Color.rgb(23, 124, 202);

    public HeadControlPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
     @Override
    protected void onFinishInflate(){
         mMidleTitle = (TextView)findViewById(R.id.midle_title);
         mRightTitle = (TextView)findViewById(R.id.right_title);
         setBackgroundColor(default_background_color);
     }
    public void initHeadPanel(){

        if(mMidleTitle != null){
            setMiddleTitle(Constants.FRAGMENT_FLAG_MESSAGE);
        }
    }
    public void setMiddleTitle(String s){
        mMidleTitle.setText(s);
        mMidleTitle.setTextSize(middle_title_size);
    }
}
