package com.example.share.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.share.Bean.Post;
import com.example.share.Bean.User;
import com.example.share.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class PushContent extends AppCompatActivity {
    private EditText mPushContent;
    private ImageView mBack;
    private Button mPush;

    //发帖子对应的activity

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_content);


        initView();

        initEvent();
    }

    private void initEvent() {

        //发帖操作
        mPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先监听内容是否为空
                if (mPushContent.getText().toString().isEmpty()) {
                    Toast.makeText(PushContent.this, "请输入内容", Toast.LENGTH_SHORT).show();
                }else {
                    //获取登录的用户的人
                    User user = BmobUser.getCurrentUser(User.class);
                    Post post = new Post();
                    post.setUsername(user.getUsername());
                    //获取编辑框的内容
                    post.setContent(mPushContent.getText().toString());
                    post.setAuthor(user);
                    post.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e==null) {
                                //发布内容为空，就先把编辑框内容设置为空
                                mPushContent.setText("");
                                Toast.makeText(PushContent.this, "发布成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(PushContent.this, "发布失败" + e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void initView() {
        mPushContent = findViewById(R.id.push_content);
        mBack = findViewById(R.id.back);
        mPush = findViewById(R.id.push);
    }
}
