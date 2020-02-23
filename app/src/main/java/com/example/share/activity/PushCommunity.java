package com.example.share.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.share.Bean.Community;
import com.example.share.Bean.User;
import com.example.share.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class PushCommunity extends AppCompatActivity {

    private ImageView mBack;
    private EditText mPushcommunityname;
    private EditText mPushcommunityinfo;
    private Button mPushbutton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_community);


        initView();


        initEvent();
    }

    private void initEvent() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mPushbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取到当前的用户
                User user = BmobUser.getCurrentUser(User.class);
                Community community = new Community();
                community.setName(mPushcommunityname.getText().toString());
                community.setInfo(mPushcommunityinfo.getText().toString());
                community.setUser(user);  //为论坛制定一个拥有者
                community.setOnwer(user.getUsername());

                community.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            Toast.makeText(PushCommunity.this, "发布成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(PushCommunity.this, "发布失败" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void initView() {
        mBack = findViewById(R.id.back);
        mPushcommunityname = findViewById(R.id.push_community_name);
        mPushcommunityinfo = findViewById(R.id.push_community_info);
        mPushbutton = findViewById(R.id.push_button);
    }
}
