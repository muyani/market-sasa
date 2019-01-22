package com.letina.marketsasa;

public class Upload {
    private String mName;
    private String mImageUrl;
    private String userId;

    public Upload() {
        //empty constructor needed.

    }

    public Upload(String mName, String mImageUrl,String id) {

        if (mName.trim().equals("")){
            this.mName = "No name";
        }
        else{
            this.mName = mName;
            this.mImageUrl = mImageUrl;
            this.userId =id;

        }


    }

    public String getmName() {
        return mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
