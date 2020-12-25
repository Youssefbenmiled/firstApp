package com.example.firstapp.model;

public class Upload {
    public String imgUrl;
    public String key;

    public Upload(String imgUrl,String key) {
        this.imgUrl = imgUrl;
        this.key=key;
    }
    public Upload(){

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
