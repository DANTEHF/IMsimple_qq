package com.example.hf876.qqsimple.ui;

/**
 * Created by hf876 on 2016/12/5.
 */

public class ContactBean {
    private int PhotoDrawableId;

    private String UserID;


    public ContactBean(int photoDrawableId,String userID) {
        super();
        PhotoDrawableId = photoDrawableId;

        UserID=userID;

    }

    public int getPhotoDrawableId() {
        return PhotoDrawableId;
    }
    public void setPhotoDrawableId(int mPhotoDrawableId) {
        this.PhotoDrawableId = mPhotoDrawableId;
    }

    public String getUserID(){
        return UserID;
    }
    @Override
    public String toString() {
        return "MessageBean [mPhotoDrawableId=" + PhotoDrawableId
                + ", UserID=" + UserID + "]";
    }



}
