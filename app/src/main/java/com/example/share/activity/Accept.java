package com.example.share.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.share.Bean.Post;
import com.example.share.R;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class Accept extends AppCompatActivity {

    private TextView mUsername;
    private TextView mContent;
    private TextView mTime;
    private ImageView mBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);


        //初始化视图
        initView();
        initData();

        //监听返回
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {

        //第二种写法
        Intent username = getIntent();
        Intent content = getIntent();
        Intent time = getIntent();
        //从HomeAdapter把数据传进来
        //预加载数据进来
        String usernameStringExtra = username.getStringExtra("username");
        String contentStringExtra= content.getStringExtra("content");
        String timeStringExtra = time.getStringExtra("time");
        mUsername.setText(usernameStringExtra);
        mContent.setText(contentStringExtra);
        mTime.setText(timeStringExtra);





/*
       第一种方法
       //接收的数据
        // 根据id来进行数据的确认
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        //Post post = new Post();

        BmobQuery<Post> query = new BmobQuery<>();
        query.getObject(id, new QueryListener<Post>() {
            @Override
            public void done(Post post, BmobException e) {
                if (e==null) {
                    mUsername.setText(post.getUsername());
                    mContent.setText(post.getContent());
                    mTime.setText(post.getCreatedAt());
                }else {
                    Toast.makeText(Accept.this, "获取失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
*/


    }

    private void initView() {
        mUsername = findViewById(R.id.username);
        mContent = findViewById(R.id.content);
        mTime = findViewById(R.id.time);
        mBack = findViewById(R.id.back);
    }
}
