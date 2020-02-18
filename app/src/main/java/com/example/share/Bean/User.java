package com.example.share.Bean;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {

    /**
     *昵称
     */
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
