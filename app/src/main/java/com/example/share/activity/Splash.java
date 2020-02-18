package com.example.share.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.share.Bean.User;
import com.example.share.MainActivity;
import com.example.share.R;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //延时操作
        Timer timer = new Timer();
        timer.schedule(timetast, 2000);


        Bmob.initialize(this,"88d4bc881a96eac8419cb22aeff54874");





    }
    TimerTask timetast = new TimerTask() {
        @Override
        public void run() {
            // startActivity(new Intent(Splash.this,MainActivity.class));
            //如果已登录就跳转到主界面，没有登陆就跳转到登陆界面
            //88d4bc881a96eac8419cb22aeff54874
            //文档有提示，但是使用自己的方法
            BmobUser bmobUser = BmobUser.getCurrentUser(User.class);
            if (bmobUser != null) {
                //页面跳转
                startActivity(new Intent(Splash.this,MainActivity.class));
                finish();
            }else {
                //如果没有登录就跳转到登录界面
                startActivity(new Intent(Splash.this,Login.class));
                finish();
            }

        }
    };
}
