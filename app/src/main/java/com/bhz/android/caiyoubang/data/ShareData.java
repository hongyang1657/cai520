package com.bhz.android.caiyoubang.data;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/5/17.
 */
public class ShareData {
    String image_url;
    String title;
    int favour;
    int collec;
    Drawable image;

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFavour() {
        return favour;
    }

    public void setFavour(int favour) {
        this.favour = favour;
    }

    public int getCollec() {
        return collec;
    }

    public void setCollec(int collec) {
        this.collec = collec;
    }
}
