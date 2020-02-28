package com.example.share.Bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

public class Post extends BmobObject {
    //填写用到的一些东西

    //上传的对应用户
    private User author;

    //帖子对应的信息
    private String title, content, nickname,username;

    //收藏对应的
    private BmobRelation mRelation ;

    public BmobRelation getRelation() {
        return mRelation;
    }


    private String isrelated;

    public String getIsrelated() {
        return isrelated;
    }

    public void setIsrelated(String isrelated) {
        this.isrelated = isrelated;
    }

    public void setRelation(BmobRelation relation) {
        mRelation = relation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
