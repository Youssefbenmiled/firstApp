package com.example.firstapp.model;

public class Upload {
    public String imgUrl;

    public Upload(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public Upload(){

    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
