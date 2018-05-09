package com.example.administrator.picdemo01.bean;

/**
 * Created by Administrator on 2018/5/8.
 */

public class MainBean {
    private String title;
    private String pic_url;

    public MainBean(String title) {
        this.title = title;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }
}
