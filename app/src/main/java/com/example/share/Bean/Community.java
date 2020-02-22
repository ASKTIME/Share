package com.example.share.Bean;

import cn.bmob.v3.BmobObject;

public class Community extends BmobObject {

    private User user;


    private String name;

    private String info;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }




}
